package com.khanblair.kotlintutor.data.content

import com.khanblair.kotlintutor.data.roadmap.CONTENT_TOPIC_IDS
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ContentTest {

    @Test
    fun `every content topic id has exactly one lesson`() {
        val lessonTopicIds = LessonContent.lessons.map { it.topicId }
        assertEquals(CONTENT_TOPIC_IDS.sorted(), lessonTopicIds.sorted())
        assertEquals(lessonTopicIds.size, lessonTopicIds.toSet().size)
    }

    @Test
    fun `every content topic id has at least one quiz question`() {
        val quizTopicIds = QuizContent.questions.map { it.topicId }.toSet()
        assertEquals(CONTENT_TOPIC_IDS, quizTopicIds)
    }

    @Test
    fun `every quiz question id is unique`() {
        val ids = QuizContent.questions.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun `every quiz question correctIndex is within its options range`() {
        QuizContent.questions.forEach { q ->
            assertTrue(
                "question ${q.id} has correctIndex ${q.correctIndex} out of bounds for ${q.options.size} options",
                q.correctIndex in q.options.indices,
            )
        }
    }

    @Test
    fun `no lesson has empty sections`() {
        LessonContent.lessons.forEach { lesson ->
            assertTrue("lesson ${lesson.topicId} has no sections", lesson.sections.isNotEmpty())
        }
    }
}
