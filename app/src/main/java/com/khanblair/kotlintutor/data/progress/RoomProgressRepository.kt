package com.khanblair.kotlintutor.data.progress

import com.khanblair.kotlintutor.model.TopicProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomProgressRepository(private val dao: ProgressDao) : ProgressRepository {

    override fun observeProgress(): Flow<List<TopicProgress>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun markCompleted(topicId: String) {
        val existing = dao.getByTopicId(topicId)
        dao.upsert((existing ?: ProgressEntity(topicId, false, null, null)).copy(isCompleted = true))
    }

    override suspend fun recordQuizScore(topicId: String, score: Int, attemptedAt: Long) {
        val existing = dao.getByTopicId(topicId)
        dao.upsert(
            (existing ?: ProgressEntity(topicId, false, null, null)).copy(
                lastQuizScore = score,
                lastAttemptedAt = attemptedAt,
            ),
        )
    }
}

private fun ProgressEntity.toDomain() = TopicProgress(
    topicId = topicId,
    isCompleted = isCompleted,
    lastQuizScore = lastQuizScore,
    lastAttemptedAt = lastAttemptedAt,
)
