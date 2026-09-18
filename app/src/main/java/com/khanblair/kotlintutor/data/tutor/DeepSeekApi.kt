package com.khanblair.kotlintutor.data.tutor

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.utils.io.readUTF8Line
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

sealed interface DeepSeekResult {
    data class Success(val reply: String) : DeepSeekResult
    data object MissingApiKey : DeepSeekResult
    data class Error(val message: String) : DeepSeekResult
}

interface DeepSeekApi {
    /**
     * Streams a chat completion. [onDelta] is invoked with the accumulated reply
     * text so far, so callers can render token-by-token; the terminal result
     * carries the full reply. Cancellation of the calling coroutine aborts the
     * in-flight HTTP call.
     */
    suspend fun streamMessage(messages: List<ChatMessage>, onDelta: suspend (String) -> Unit): DeepSeekResult
}

private const val CHAT_COMPLETIONS_URL = "https://api.deepseek.com/chat/completions"
private const val SSE_DONE = "data: [DONE]"

class KtorDeepSeekApi(
    private val httpClient: HttpClient,
    private val apiKeyStore: ApiKeyStore,
    private val json: Json = Json { ignoreUnknownKeys = true },
) : DeepSeekApi {

    override suspend fun streamMessage(messages: List<ChatMessage>, onDelta: suspend (String) -> Unit): DeepSeekResult {
        val apiKey = apiKeyStore.getApiKey() ?: return DeepSeekResult.MissingApiKey
        return try {
            val response = httpClient.post(CHAT_COMPLETIONS_URL) {
                header(HttpHeaders.Authorization, "Bearer $apiKey")
                contentType(ContentType.Application.Json)
                setBody(ChatCompletionRequest(messages = messages, stream = true))
            }
            if (!response.status.isSuccess()) {
                return DeepSeekResult.Error(describeHttpError(response.status.value, response.bodyAsText(), json))
            }
            var accumulated = ""
            val channel = response.bodyAsChannel()
            while (true) {
                val line = channel.readUTF8Line() ?: break
                if (line == SSE_DONE) break
                val delta = parseSseDelta(line, json)
                if (delta != null) {
                    accumulated += delta
                    onDelta(accumulated)
                }
            }
            if (accumulated.isBlank()) DeepSeekResult.Error("Empty response from DeepSeek")
            else DeepSeekResult.Success(accumulated)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            DeepSeekResult.Error(e.message ?: "Network error contacting DeepSeek")
        }
    }
}

/**
 * Parses one SSE line from a streaming chat completion into the incremental
 * text delta, or null when the line carries no content (event metadata,
 * `[DONE]`, empty payloads, malformed JSON).
 */
internal fun parseSseDelta(line: String, json: Json = Json { ignoreUnknownKeys = true }): String? {
    if (!line.startsWith("data:")) return null
    val payload = line.removePrefix("data:").trim()
    if (payload.isEmpty() || payload == "[DONE]") return null
    return runCatching {
        json.decodeFromString<StreamChunk>(payload).choices.firstOrNull()?.delta?.content
    }.getOrNull()
}

/**
 * Maps a non-2xx HTTP status (plus the response body, when readable) to a
 * message the user can act on — a 401 is a settings problem, a 429 is a
 * quota problem, and DeepSeek's own error body is surfaced verbatim.
 */
internal fun describeHttpError(status: Int, body: String?, json: Json = Json { ignoreUnknownKeys = true }): String {
    val reason = runCatching {
        body?.let {
            json.parseToJsonElement(it).jsonObject["error"]?.jsonObject?.get("message")?.jsonPrimitive?.content
        }
    }.getOrNull()?.take(200)
    val suffix = if (reason.isNullOrBlank()) "" else " ($reason)"
    return when (status) {
        401 -> "Invalid DeepSeek API key — check your key in Settings.$suffix"
        402, 429 -> "DeepSeek rate limit or quota exceeded — try again later.$suffix"
        in 400..499 -> "DeepSeek rejected the request (HTTP $status).$suffix"
        else -> "DeepSeek server error (HTTP $status) — try again shortly.$suffix"
    }
}
