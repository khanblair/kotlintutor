package com.khanblair.kotlintutor.data.tutor

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class DeepSeekDtoTest {

    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    @Test
    fun `ChatCompletionRequest serializes with expected field names and defaults`() {
        val request = ChatCompletionRequest(messages = listOf(ChatMessage("user", "hello")))
        val encoded = json.encodeToString(request)
        assertEquals(
            """{"model":"deepseek-v4-pro","messages":[{"role":"user","content":"hello"}],"stream":false}""",
            encoded,
        )
    }

    @Test
    fun `ChatCompletionResponse decodes the assistant reply from choices`() {
        val raw = """{"choices":[{"message":{"role":"assistant","content":"hi there"}}]}"""
        val decoded = json.decodeFromString<ChatCompletionResponse>(raw)
        assertEquals("hi there", decoded.choices.first().message.content)
    }

    @Test
    fun `ChatCompletionResponse ignores unknown fields`() {
        val raw = """{"id":"abc","choices":[{"message":{"role":"assistant","content":"ok"}}],"usage":{"total_tokens":10}}"""
        val decoded = json.decodeFromString<ChatCompletionResponse>(raw)
        assertEquals("ok", decoded.choices.first().message.content)
    }
}
