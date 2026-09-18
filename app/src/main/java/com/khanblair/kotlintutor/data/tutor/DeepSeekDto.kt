package com.khanblair.kotlintutor.data.tutor

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val role: String,
    val content: String,
)

@Serializable
data class ChatCompletionRequest(
    val model: String = "deepseek-v4-pro",
    val messages: List<ChatMessage>,
    val stream: Boolean = true,
)

@Serializable
data class ChatCompletionResponse(
    val choices: List<Choice>,
) {
    @Serializable
    data class Choice(val message: ChatMessage)
}

/** One `data:` chunk of a streaming (SSE) chat completion. */
@Serializable
internal data class StreamChunk(
    val choices: List<StreamChoice> = emptyList(),
) {
    @Serializable
    data class StreamChoice(val delta: StreamDelta? = null)

    @Serializable
    data class StreamDelta(val content: String? = null)
}
