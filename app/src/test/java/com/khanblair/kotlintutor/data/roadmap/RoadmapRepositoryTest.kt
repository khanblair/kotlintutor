package com.khanblair.kotlintutor.data.roadmap

import com.khanblair.kotlintutor.data.progress.ProgressRepository
import com.khanblair.kotlintutor.model.TopicProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

private class FakeProgressRepository(
    initial: List<TopicProgress> = emptyList(),
) : ProgressRepository {
    val progressFlow = MutableStateFlow(initial)

    override fun observeProgress(): Flow<List<TopicProgress>> = progressFlow

    override suspend fun markCompleted(topicId: String) {
        progressFlow.value = progressFlow.value.filterNot { it.topicId == topicId } +
            TopicProgress(topicId, isCompleted = true, lastQuizScore = null, lastAttemptedAt = null)
    }

    override suspend fun recordQuizScore(topicId: String, score: Int, attemptedAt: Long) {
        progressFlow.value = progressFlow.value.filterNot { it.topicId == topicId } +
            TopicProgress(topicId, isCompleted = true, lastQuizScore = score, lastAttemptedAt = attemptedAt)
    }
}

class RoadmapRepositoryTest {

    @Test
    fun `observeRoadmap returns every node from RoadmapContent`() = runTest {
        val repository = DefaultRoadmapRepository(FakeProgressRepository())

        val items = repository.observeRoadmap().first()

        assertEquals(RoadmapContent.nodes.size, items.size)
        assertEquals(RoadmapContent.nodes.map { it.id }.toSet(), items.map { it.node.id }.toSet())
    }

    @Test
    fun `nodes with no recorded progress default to not completed`() = runTest {
        val repository = DefaultRoadmapRepository(FakeProgressRepository())

        val items = repository.observeRoadmap().first()

        assertTrue(items.none { it.isCompleted })
        assertTrue(items.all { it.lastQuizScore == null })
    }

    @Test
    fun `completed topics are reflected in the roadmap items`() = runTest {
        val fake = FakeProgressRepository(
            initial = listOf(TopicProgress("val-vs-var", isCompleted = true, lastQuizScore = 3, lastAttemptedAt = 100L)),
        )
        val repository = DefaultRoadmapRepository(fake)

        val items = repository.observeRoadmap().first()
        val valVsVar = items.first { it.node.id == "val-vs-var" }
        val untouched = items.first { it.node.id == "data-types" }

        assertTrue(valVsVar.isCompleted)
        assertEquals(3, valVsVar.lastQuizScore)
        assertTrue(untouched.isCompleted.not())
        assertNull(untouched.lastQuizScore)
    }
}
