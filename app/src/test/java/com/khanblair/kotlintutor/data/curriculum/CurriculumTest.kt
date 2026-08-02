package com.khanblair.kotlintutor.data.curriculum

import com.khanblair.kotlintutor.data.roadmap.CONTENT_TOPIC_IDS
import com.khanblair.kotlintutor.data.roadmap.RoadmapContent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CurriculumTest {

    @Test
    fun `there are exactly 42 curriculum topics`() {
        assertEquals(42, Curriculum.topics.size)
    }

    @Test
    fun `every topic id is unique`() {
        val ids = Curriculum.topics.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun `curriculum topic ids exactly match CONTENT_TOPIC_IDS`() {
        assertEquals(CONTENT_TOPIC_IDS, Curriculum.topics.map { it.id }.toSet())
    }

    @Test
    fun `every curriculum topic id exists as a roadmap node`() {
        val roadmapIds = RoadmapContent.nodes.map { it.id }.toSet()
        Curriculum.topics.forEach { topic ->
            assertTrue("no RoadmapNode for curriculum topic ${topic.id}", topic.id in roadmapIds)
        }
    }

    @Test
    fun `only the first topic has no recap`() {
        assertNull(Curriculum.topics.first().recap)
        Curriculum.topics.drop(1).forEach { topic ->
            assertNotNull("topic ${topic.id} should have a recap", topic.recap)
        }
    }

    @Test
    fun `each recap references the immediately preceding topic's title`() {
        Curriculum.topics.zipWithNext().forEach { (previous, current) ->
            assertEquals(
                "recap on ${current.id} should reference ${previous.id}",
                previous.title,
                current.recap?.previousTopicTitle,
            )
        }
    }

    @Test
    fun `every topic has exactly three quiz questions with valid correctIndex`() {
        Curriculum.topics.forEach { topic ->
            assertEquals("topic ${topic.id} should have 3 quiz questions", 3, topic.quiz.size)
            topic.quiz.forEach { q ->
                assertTrue(
                    "question ${q.id} has correctIndex out of bounds",
                    q.correctIndex in q.options.indices,
                )
            }
        }
    }

    @Test
    fun `every quiz question id across the curriculum is unique`() {
        val ids = Curriculum.topics.flatMap { it.quiz }.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun `every topic has non-blank explain, tutorFocus, and at least one key point`() {
        Curriculum.topics.forEach { topic ->
            assertTrue("topic ${topic.id} has blank explain", topic.explain.isNotBlank())
            assertTrue("topic ${topic.id} has blank tutorFocus", topic.tutorFocus.isNotBlank())
            assertTrue("topic ${topic.id} has no key points", topic.keyPoints.isNotEmpty())
        }
    }
}
