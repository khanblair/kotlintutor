package com.khanblair.kotlintutor.domain

import com.khanblair.kotlintutor.model.RoadmapItem

/**
 * Percentage of leaf topics (nodes with a parent, i.e. excluding category
 * headers) marked completed. Returns 0 if there are no leaf topics.
 */
fun List<RoadmapItem>.completionPercent(): Int {
    val topics = filter { it.node.parentId != null }
    if (topics.isEmpty()) return 0
    val completed = topics.count { it.isCompleted }
    return (completed * 100) / topics.size
}
