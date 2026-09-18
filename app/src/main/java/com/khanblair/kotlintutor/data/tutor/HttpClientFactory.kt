package com.khanblair.kotlintutor.data.tutor

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.HttpTimeoutConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * The single HTTP client for DeepSeek chat completions.
 *
 * Timeouts are deliberate: connect is short (15s), and the socket timeout
 * (120s) is an inactivity guard on the SSE stream — DeepSeek sends `data:`
 * chunks continuously while generating, so a live stream never trips it and a
 * hung connection can no longer leave the user staring at an infinite spinner.
 * There is deliberately NO absolute request deadline: a long but alive
 * streaming reply must not be killed by a wall-clock cap.
 */
fun createDeepSeekHttpClient(): HttpClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true; encodeDefaults = true })
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 15_000
        requestTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
        socketTimeoutMillis = 120_000
    }
}
