package com.khanblair.kotlintutor.data.tutor

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DeepSeekSseTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `extracts content from a data payload`() {
        val line = """data: {"choices":[{"delta":{"content":"fun "}}]}"""
        assertEquals("fun ", parseSseDelta(line, json))
    }

    @Test
    fun `concatenates multiple deltas in order`() {
        val lines = listOf(
            """data: {"choices":[{"delta":{"content":"Hello"}}]}""",
            """data: {"choices":[{"delta":{"content":" world"}}]}""",
        )
        val combined = lines.mapNotNull { parseSseDelta(it, json) }.joinToString("")
        assertEquals("Hello world", combined)
    }

    @Test
    fun `ignores the done marker`() {
        assertNull(parseSseDelta("data: [DONE]", json))
    }

    @Test
    fun `ignores non-data lines such as event metadata and blank lines`() {
        assertNull(parseSseDelta(": keep-alive", json))
        assertNull(parseSseDelta("", json))
    }

    @Test
    fun `ignores chunks with no content delta (finish reason only)`() {
        val line = """data: {"choices":[{"delta":{},"finish_reason":"stop"}]}"""
        assertNull(parseSseDelta(line, json))
    }

    @Test
    fun `malformed json yields null instead of throwing`() {
        assertNull(parseSseDelta("data: {not json", json))
    }
}
