package com.khanblair.kotlintutor.data.tutor

import com.khanblair.kotlintutor.domain.TutorMode
import com.khanblair.kotlintutor.domain.TutorPromptBuilder
import com.khanblair.kotlintutor.model.CurriculumTopic

interface TutorRepository {
    suspend fun sendMessage(
        topic: CurriculumTopic,
        mode: TutorMode,
        history: List<ChatMessage>,
        userMessage: String,
    ): DeepSeekResult
}

class DefaultTutorRepository(private val api: DeepSeekApi) : TutorRepository {
    override suspend fun sendMessage(
        topic: CurriculumTopic,
        mode: TutorMode,
        history: List<ChatMessage>,
        userMessage: String,
    ): DeepSeekResult {
        val systemPrompt = TutorPromptBuilder.buildSystemPrompt(topic, mode)
        val messages = listOf(ChatMessage(role = "system", content = systemPrompt)) +
            history +
            ChatMessage(role = "user", content = userMessage)
        return api.sendMessage(messages)
    }
}
