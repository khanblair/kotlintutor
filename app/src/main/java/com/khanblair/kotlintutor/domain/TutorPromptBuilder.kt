package com.khanblair.kotlintutor.domain

import com.khanblair.kotlintutor.model.CurriculumTopic

/**
 * Builds the AI Tutor's system prompt for a given topic + mode, per the mapping
 * in docs/kotlin-tutor-content.md's Appendix: Explain -> explain/keyPoints,
 * Quiz me -> quiz, Review my code -> keyPoints as a rubric, Give an exercise -> tutorFocus.
 */
object TutorPromptBuilder {

    fun buildSystemPrompt(topic: CurriculumTopic, mode: TutorMode): String {
        val header = "You are a friendly, focused Kotlin tutor helping a learner with the topic \"${topic.title}\"."
        return when (mode) {
            TutorMode.EXPLAIN -> buildString {
                appendLine(header)
                appendLine("Explain this topic clearly, using the following source material:")
                appendLine(topic.explain)
                if (topic.example.isNotBlank()) {
                    appendLine("Example code:")
                    appendLine(topic.example)
                }
                appendLine("Key points and pitfalls to emphasize:")
                topic.keyPoints.forEach { appendLine("- $it") }
                appendLine("Tutor guidance: ${topic.tutorFocus}")
            }.trim()

            TutorMode.QUIZ_ME -> buildString {
                appendLine(header)
                appendLine(
                    "Quiz the learner on this topic. Ask the following questions ONE AT A TIME, " +
                        "waiting for their answer before moving to the next, and give feedback after each:",
                )
                topic.quiz.forEachIndexed { index, question ->
                    val correctAnswer = question.options[question.correctIndex]
                    appendLine("${index + 1}. ${question.question} (correct answer: $correctAnswer — ${question.explanation})")
                }
            }.trim()

            TutorMode.REVIEW_MY_CODE -> buildString {
                appendLine(header)
                appendLine(
                    "The learner will paste Kotlin code related to this topic. Review it against " +
                        "these key points and pitfalls, calling out anything that violates them:",
                )
                topic.keyPoints.forEach { appendLine("- $it") }
                appendLine("Be constructive and specific; suggest concrete fixes.")
            }.trim()

            TutorMode.GIVE_EXERCISE -> buildString {
                appendLine(header)
                appendLine("Give the learner a hands-on exercise for this topic, based on this guidance:")
                appendLine(topic.tutorFocus)
                appendLine("Present ONE exercise, wait for their attempt, then give feedback.")
            }.trim()
        }
    }
}
