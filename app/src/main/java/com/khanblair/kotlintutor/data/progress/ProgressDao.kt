package com.khanblair.kotlintutor.data.progress

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {
    @Query("SELECT * FROM topic_progress")
    fun observeAll(): Flow<List<ProgressEntity>>

    @Query("SELECT * FROM topic_progress WHERE topicId = :topicId")
    suspend fun getByTopicId(topicId: String): ProgressEntity?

    @Upsert
    suspend fun upsert(progress: ProgressEntity)
}
