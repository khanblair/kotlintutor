package com.khanblair.kotlintutor.domain

import com.khanblair.kotlintutor.model.QuizQuestion
import org.junit.Assert.assertEquals
import org.junit.Test

class QuizScorerTest {

    private val questions = listOf(
        QuizQuestion("q1", "topic", "Q1?", listOf("a", "b"), correctIndex = 0, explanation = ""),
        QuizQuestion("q2", "topic", "Q2?", listOf("a", "b"), correctIndex = 1, explanation = ""),
        QuizQuestion("q3", "topic", "Q3?", listOf("a", "b", "c"), correctIndex = 2, explanation = ""),
    )

    @Test
    fun `all correct answers scores full marks`() {
        val answers = mapOf("q1" to 0, "q2" to 1, "q3" to 2)
        assertEquals(3, QuizScorer.score(questions, answers))
    }

    @Test
    fun `some wrong answers scores partial marks`() {
        val answers = mapOf("q1" to 0, "q2" to 0, "q3" to 1)
        assertEquals(1, QuizScorer.score(questions, answers))
    }

    @Test
    fun `unanswered questions do not count as correct`() {
        val answers = mapOf("q1" to 0)
        assertEquals(1, QuizScorer.score(questions, answers))
    }

    @Test
    fun `empty answers scores zero`() {
        assertEquals(0, QuizScorer.score(questions, emptyMap()))
    }

    @Test
    fun `extraneous answers for unknown question ids are ignored`() {
        val answers = mapOf("q1" to 0, "unknown-id" to 5)
        assertEquals(1, QuizScorer.score(questions, answers))
    }
}
