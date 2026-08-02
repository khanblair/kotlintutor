package com.khanblair.kotlintutor.data.progress

import com.khanblair.kotlintutor.model.TopicProgress
import kotlinx.coroutines.flow.Flow

interface ProgressRepository {
    fun observeProgress(): Flow<List<TopicProgress>>
    suspend fun markCompleted(topicId: String)
    suspend fun recordQuizScore(topicId: String, score: Int, attemptedAt: Long)
}
