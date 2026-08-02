package com.khanblair.kotlintutor.model

data class TopicProgress(
    val topicId: String,
    val isCompleted: Boolean,
    val lastQuizScore: Int?,
    val lastAttemptedAt: Long?,
)
