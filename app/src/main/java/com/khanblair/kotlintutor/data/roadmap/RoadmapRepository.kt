package com.khanblair.kotlintutor.data.roadmap

import com.khanblair.kotlintutor.data.progress.ProgressRepository
import com.khanblair.kotlintutor.model.RoadmapItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface RoadmapRepository {
    fun observeRoadmap(): Flow<List<RoadmapItem>>
}

class DefaultRoadmapRepository(
    private val progressRepository: ProgressRepository,
) : RoadmapRepository {

    override fun observeRoadmap(): Flow<List<RoadmapItem>> =
        progressRepository.observeProgress().map { progressList ->
            val progressByTopicId = progressList.associateBy { it.topicId }
            RoadmapContent.nodes.map { node ->
                val progress = progressByTopicId[node.id]
                RoadmapItem(
                    node = node,
                    isCompleted = progress?.isCompleted ?: false,
                    lastQuizScore = progress?.lastQuizScore,
                )
            }
        }
}
