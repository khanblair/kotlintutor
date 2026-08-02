package com.khanblair.kotlintutor.data.content

import com.khanblair.kotlintutor.data.roadmap.CONTENT_TOPIC_IDS
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ContentRepositoryTest {

    private val repository: ContentRepository = DefaultContentRepository()

    @Test
    fun `getLesson returns the lesson for a known content topic`() {
        val lesson = repository.getLesson("val-vs-var")
        assertEquals("val vs var", lesson?.title)
    }

    @Test
    fun `getLesson returns null for a topic without authored content`() {
        assertNull(repository.getLesson("gradle"))
    }

    @Test
    fun `getQuizQuestions returns three questions for every content topic`() {
        CONTENT_TOPIC_IDS.forEach { topicId ->
            assertEquals(3, repository.getQuizQuestions(topicId).size)
        }
    }

    @Test
    fun `getQuizQuestions returns empty list for a topic without authored content`() {
        assertTrue(repository.getQuizQuestions("gradle").isEmpty())
    }
}
