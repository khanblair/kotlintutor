package com.khanblair.kotlintutor.data.tutor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException

sealed interface DeepSeekResult {
    data class Success(val reply: String) : DeepSeekResult
    data object MissingApiKey : DeepSeekResult
    data class Error(val message: String) : DeepSeekResult
}

interface DeepSeekApi {
    suspend fun sendMessage(messages: List<ChatMessage>): DeepSeekResult
}

private const val CHAT_COMPLETIONS_URL = "https://api.deepseek.com/chat/completions"

class KtorDeepSeekApi(
    private val httpClient: HttpClient,
    private val apiKeyStore: ApiKeyStore,
) : DeepSeekApi {

    override suspend fun sendMessage(messages: List<ChatMessage>): DeepSeekResult {
        val apiKey = apiKeyStore.getApiKey() ?: return DeepSeekResult.MissingApiKey
        return try {
            val response = httpClient.post(CHAT_COMPLETIONS_URL) {
                header(HttpHeaders.Authorization, "Bearer $apiKey")
                contentType(ContentType.Application.Json)
                setBody(ChatCompletionRequest(messages = messages))
            }
            if (!response.status.isSuccess()) {
                return DeepSeekResult.Error("DeepSeek returned HTTP ${response.status.value}")
            }
            val reply = response.body<ChatCompletionResponse>().choices.firstOrNull()?.message?.content
                ?: return DeepSeekResult.Error("Empty response from DeepSeek")
            DeepSeekResult.Success(reply)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            DeepSeekResult.Error(e.message ?: "Network error contacting DeepSeek")
        }
    }
}
