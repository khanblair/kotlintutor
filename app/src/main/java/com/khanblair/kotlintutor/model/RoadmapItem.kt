package com.khanblair.kotlintutor.model

data class RoadmapItem(
    val node: RoadmapNode,
    val isCompleted: Boolean,
    val lastQuizScore: Int?,
)
