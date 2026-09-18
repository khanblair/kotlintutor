package com.khanblair.kotlintutor.data.tutor

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
            """{"model":"deepseek-v4-pro","messages":[{"role":"user","content":"hello"}],"stream":true}""",
            encoded,
        )
    }

    @Test
    fun `stream chunks decode the incremental delta content`() {
        val raw = """{"choices":[{"delta":{"content":"Hello "}}]}"""
        val decoded = json.decodeFromString<StreamChunk>(raw)
        assertEquals("Hello ", decoded.choices.first().delta?.content)
    }

    @Test
    fun `stream chunks with a null delta content decode without error`() {
        val raw = """{"choices":[{"delta":{"content":null},"finish_reason":"stop"}]}"""
        val decoded = json.decodeFromString<StreamChunk>(raw)
        assertEquals(null, decoded.choices.first().delta?.content)
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
