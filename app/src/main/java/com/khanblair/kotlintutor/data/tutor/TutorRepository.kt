package com.khanblair.kotlintutor.data.tutor

import com.khanblair.kotlintutor.domain.TutorMode
import com.khanblair.kotlintutor.domain.TutorPromptBuilder
import com.khanblair.kotlintutor.model.CurriculumTopic

interface TutorRepository {
    /**
     * Streams a tutor reply for [topic] in [mode]. [history] carries prior
     * user/assistant turns; [onDelta] receives the accumulated reply text so far.
     */
    suspend fun streamMessage(
        topic: CurriculumTopic,
        mode: TutorMode,
        history: List<ChatMessage>,
        userMessage: String,
        onDelta: suspend (String) -> Unit,
    ): DeepSeekResult
}

class DefaultTutorRepository(private val api: DeepSeekApi) : TutorRepository {
    override suspend fun streamMessage(
        topic: CurriculumTopic,
        mode: TutorMode,
        history: List<ChatMessage>,
        userMessage: String,
        onDelta: suspend (String) -> Unit,
    ): DeepSeekResult {
        val systemPrompt = TutorPromptBuilder.buildSystemPrompt(topic, mode)
        val messages = listOf(ChatMessage(role = "system", content = systemPrompt)) +
            history +
            ChatMessage(role = "user", content = userMessage)
        return api.streamMessage(messages, onDelta)
    }
}
