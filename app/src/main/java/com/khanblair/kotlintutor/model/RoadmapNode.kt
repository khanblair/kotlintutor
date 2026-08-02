package com.khanblair.kotlintutor.model

data class RoadmapNode(
    val id: String,
    val title: String,
    val parentId: String?,
    val category: String,
    val hasContent: Boolean,
)
