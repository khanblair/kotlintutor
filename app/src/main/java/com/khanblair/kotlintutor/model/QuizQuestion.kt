package com.khanblair.kotlintutor.model

data class QuizQuestion(
    val id: String,
    val topicId: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
)
