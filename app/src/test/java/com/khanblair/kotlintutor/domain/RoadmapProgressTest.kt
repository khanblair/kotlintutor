package com.khanblair.kotlintutor.domain

import com.khanblair.kotlintutor.model.RoadmapItem
import com.khanblair.kotlintutor.model.RoadmapNode
import org.junit.Assert.assertEquals
import org.junit.Test

class RoadmapProgressTest {

    private fun item(id: String, parentId: String?, completed: Boolean) = RoadmapItem(
        node = RoadmapNode(id = id, title = id, parentId = parentId, category = "Cat", hasContent = false),
        isCompleted = completed,
        lastQuizScore = null,
    )

    @Test
    fun `category nodes are excluded from the percentage`() {
        val items = listOf(
            item("category", parentId = null, completed = true), // should not count
            item("topic-1", parentId = "category", completed = true),
            item("topic-2", parentId = "category", completed = false),
        )
        assertEquals(50, items.completionPercent())
    }

    @Test
    fun `no topics returns zero instead of dividing by zero`() {
        val items = listOf(item("category", parentId = null, completed = true))
        assertEquals(0, items.completionPercent())
    }

    @Test
    fun `all topics completed is one hundred percent`() {
        val items = listOf(
            item("topic-1", parentId = "category", completed = true),
            item("topic-2", parentId = "category", completed = true),
        )
        assertEquals(100, items.completionPercent())
    }

    @Test
    fun `no topics completed is zero percent`() {
        val items = listOf(
            item("topic-1", parentId = "category", completed = false),
            item("topic-2", parentId = "category", completed = false),
        )
        assertEquals(0, items.completionPercent())
    }

    @Test
    fun `a single completed topic on a large roadmap rounds up instead of truncating to zero`() {
        val topics = (1..127).map { item("topic-$it", parentId = "category", completed = it == 1) }
        assertEquals(1, topics.completionPercent())
    }

    @Test
    fun `two thirds rounds to the nearest percent`() {
        val items = listOf(
            item("topic-1", parentId = "category", completed = true),
            item("topic-2", parentId = "category", completed = true),
            item("topic-3", parentId = "category", completed = false),
        )
        // 2/3 = 66.6% -> 67
        assertEquals(67, items.completionPercent())
    }
}
