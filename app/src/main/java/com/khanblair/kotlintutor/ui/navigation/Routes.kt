package com.khanblair.kotlintutor.ui.navigation

internal const val ROUTE_ROADMAP = "roadmap"
internal const val ROUTE_LESSON = "lesson/{topicId}"
internal const val ROUTE_QUIZ = "quiz/{topicId}"
internal const val ROUTE_TUTOR = "tutor/{topicId}"
internal const val ROUTE_SETTINGS = "settings"

internal fun lessonRoute(topicId: String) = "lesson/$topicId"
internal fun quizRoute(topicId: String) = "quiz/$topicId"
internal fun tutorRoute(topicId: String) = "tutor/$topicId"
