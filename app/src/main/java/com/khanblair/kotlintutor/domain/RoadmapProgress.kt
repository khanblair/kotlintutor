package com.khanblair.kotlintutor.domain

import com.khanblair.kotlintutor.model.RoadmapItem
import kotlin.math.roundToInt

/**
 * Percentage of leaf topics (nodes with a parent, i.e. excluding category
 * headers) marked completed. Returns 0 if there are no leaf topics.
 *
 * Rounds to the nearest integer instead of truncating, so a single completed
 * topic on a 127-topic roadmap (0.79%) shows "1%" and moves the bar instead of
 * being stuck at 0% until the second topic.
 */
fun List<RoadmapItem>.completionPercent(): Int {
    val topics = filter { it.node.parentId != null }
    if (topics.isEmpty()) return 0
    val completed = topics.count { it.isCompleted }
    return ((completed * 100.0) / topics.size).roundToInt()
}
