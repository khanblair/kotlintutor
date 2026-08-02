package com.khanblair.kotlintutor.domain

import com.khanblair.kotlintutor.model.CurriculumTopic
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.model.Recap
import org.junit.Assert.assertTrue
import org.junit.Test

class TutorPromptBuilderTest {

    private val topic = CurriculumTopic(
        id = "sample-topic",
        title = "Sample Topic",
        category = "Testing",
        recap = Recap("Previous Topic", "recap text", "quick check?", "quick answer"),
        explain = "This is the explanation text.",
        example = "val x = 1",
        keyPoints = listOf("Point one", "Point two"),
        quiz = listOf(
            QuizQuestion("sample-topic-q1", "sample-topic", "What is X?", listOf("A", "B"), 0, "because A"),
        ),
        tutorFocus = "Focus on point one; suggest exercise Y.",
    )

    @Test
    fun `every prompt mentions the topic title`() {
        TutorMode.entries.forEach { mode ->
            val prompt = TutorPromptBuilder.buildSystemPrompt(topic, mode)
            assertTrue("mode $mode should mention the topic title", prompt.contains(topic.title))
        }
    }

    @Test
    fun `explain mode includes explain text, example, key points, and tutor focus`() {
        val prompt = TutorPromptBuilder.buildSystemPrompt(topic, TutorMode.EXPLAIN)
        assertTrue(prompt.contains(topic.explain))
        assertTrue(prompt.contains(topic.example))
        assertTrue(prompt.contains("Point one"))
        assertTrue(prompt.contains("Point two"))
        assertTrue(prompt.contains(topic.tutorFocus))
    }

    @Test
    fun `quiz me mode includes every question and its correct answer`() {
        val prompt = TutorPromptBuilder.buildSystemPrompt(topic, TutorMode.QUIZ_ME)
        val question = topic.quiz.first()
        assertTrue(prompt.contains(question.question))
        assertTrue(prompt.contains(question.options[question.correctIndex]))
        assertTrue(prompt.contains(question.explanation))
    }

    @Test
    fun `review my code mode includes key points as a rubric`() {
        val prompt = TutorPromptBuilder.buildSystemPrompt(topic, TutorMode.REVIEW_MY_CODE)
        topic.keyPoints.forEach { point -> assertTrue(prompt.contains(point)) }
    }

    @Test
    fun `give an exercise mode includes tutor focus guidance`() {
        val prompt = TutorPromptBuilder.buildSystemPrompt(topic, TutorMode.GIVE_EXERCISE)
        assertTrue(prompt.contains(topic.tutorFocus))
    }
}
