package com.khanblair.kotlintutor.data.progress

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ProgressDao {

    @Query("SELECT * FROM topic_progress")
    abstract fun observeAll(): Flow<List<ProgressEntity>>

    @Query("SELECT * FROM topic_progress WHERE topicId = :topicId")
    abstract suspend fun getByTopicId(topicId: String): ProgressEntity?

    @Upsert
    abstract suspend fun upsert(progress: ProgressEntity)

    /**
     * Marks a topic completed without touching any existing quiz score.
     *
     * Wrapping the read-modify-write in a single Room transaction makes it atomic:
     * concurrent callers (e.g. a quiz submission racing a manual "mark complete")
     * serialize on the transaction executor instead of interleaving their reads
     * and writes, which could otherwise silently lose one of the updates.
     */
    @Transaction
    open suspend fun markCompleted(topicId: String) {
        val existing = getByTopicId(topicId)
        upsert((existing ?: ProgressEntity(topicId, false, null, null)).copy(isCompleted = true))
    }

    /**
     * Records a quiz result. Submitting a quiz is itself a completion signal
     * (deliberate: an attempt, not a pass, marks the topic done), so the row is
     * upserted with [isCompleted] set regardless of score.
     */
    @Transaction
    open suspend fun recordQuizScore(topicId: String, score: Int, attemptedAt: Long) {
        val existing = getByTopicId(topicId)
        upsert(
            (existing ?: ProgressEntity(topicId, false, null, null)).copy(
                isCompleted = true,
                lastQuizScore = score,
                lastAttemptedAt = attemptedAt,
            ),
        )
    }
}
