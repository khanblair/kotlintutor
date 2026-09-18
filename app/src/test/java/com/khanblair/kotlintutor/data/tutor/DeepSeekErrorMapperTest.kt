package com.khanblair.kotlintutor.data.tutor

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test

class DeepSeekErrorMapperTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `bad key is actionable`() {
        val message = describeHttpError(401, """{"error":{"message":"Authentication Fails"}}""", json)
        assertEquals("Invalid DeepSeek API key — check your key in Settings. (Authentication Fails)", message)
    }

    @Test
    fun `rate limit is actionable`() {
        assertEquals(
            "DeepSeek rate limit or quota exceeded — try again later.",
            describeHttpError(429, null, json),
        )
    }

    @Test
    fun `client errors include the status and server reason`() {
        val message = describeHttpError(400, """{"error":{"message":"invalid model"}}""", json)
        assertEquals("DeepSeek rejected the request (HTTP 400). (invalid model)", message)
    }

    @Test
    fun `server errors suggest retrying`() {
        assertEquals(
            "DeepSeek server error (HTTP 503) — try again shortly.",
            describeHttpError(503, null, json),
        )
    }

    @Test
    fun `non-json error body degrades to a bare status message`() {
        assertEquals(
            "DeepSeek server error (HTTP 500) — try again shortly.",
            describeHttpError(500, "gateway timeout", json),
        )
    }
}
