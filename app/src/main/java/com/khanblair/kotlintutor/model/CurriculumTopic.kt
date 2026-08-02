package com.khanblair.kotlintutor.model

/**
 * One topic from docs/kotlin-tutor-content.md. [id] matches a [RoadmapNode.id]
 * this topic is the primary content for. [tutorFocus] is never shown verbatim
 * in the UI — it seeds the AI Tutor's system prompt for this topic.
 */
data class CurriculumTopic(
    val id: String,
    val title: String,
    val category: String,
    val recap: Recap?,
    val explain: String,
    val example: String,
    val keyPoints: List<String>,
    val quiz: List<QuizQuestion>,
    val tutorFocus: String,
)
