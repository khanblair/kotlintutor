package com.khanblair.kotlintutor.data.progress

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topic_progress")
data class ProgressEntity(
    @PrimaryKey val topicId: String,
    val isCompleted: Boolean,
    val lastQuizScore: Int?,
    val lastAttemptedAt: Long?,
)
