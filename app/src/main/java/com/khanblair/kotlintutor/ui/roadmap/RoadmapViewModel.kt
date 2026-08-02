package com.khanblair.kotlintutor.ui.roadmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanblair.kotlintutor.data.progress.ProgressRepository
import com.khanblair.kotlintutor.data.roadmap.RoadmapRepository
import com.khanblair.kotlintutor.domain.completionPercent
import com.khanblair.kotlintutor.model.RoadmapItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class RoadmapUiState(
    val items: List<RoadmapItem> = emptyList(),
    val completionPercent: Int = 0,
)

class RoadmapViewModel(
    roadmapRepository: RoadmapRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {
    val uiState: StateFlow<RoadmapUiState> = roadmapRepository.observeRoadmap()
        .map { items -> RoadmapUiState(items = items, completionPercent = items.completionPercent()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), RoadmapUiState())

    /** For topics without authored content yet — lets the user track the roadmap manually. */
    fun markCompleted(topicId: String) {
        viewModelScope.launch { progressRepository.markCompleted(topicId) }
    }
}
