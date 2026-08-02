package com.khanblair.kotlintutor.data.roadmap

import org.junit.Assert.assertTrue
import org.junit.Test

class RoadmapContentTest {

    @Test
    fun `all node ids are unique`() {
        val ids = RoadmapContent.nodes.map { it.id }
        assertTrue("duplicate ids: ${ids.groupingBy { it }.eachCount().filter { it.value > 1 }}", ids.size == ids.toSet().size)
    }

    @Test
    fun `every parentId points to an existing node`() {
        val ids = RoadmapContent.nodes.map { it.id }.toSet()
        val danglingParents = RoadmapContent.nodes.mapNotNull { it.parentId }.filterNot { it in ids }
        assertTrue("dangling parentIds: $danglingParents", danglingParents.isEmpty())
    }

    @Test
    fun `every content topic id exists as a node marked hasContent`() {
        val nodesById = RoadmapContent.nodes.associateBy { it.id }
        CONTENT_TOPIC_IDS.forEach { topicId ->
            val n = nodesById[topicId]
            assertTrue("missing node for content topic id: $topicId", n != null)
            assertTrue("node $topicId should have hasContent = true", n!!.hasContent)
        }
    }
}
