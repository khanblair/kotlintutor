package com.khanblair.kotlintutor.ui.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanblair.kotlintutor.data.curriculum.CurriculumRepository
import com.khanblair.kotlintutor.data.progress.ProgressRepository
import com.khanblair.kotlintutor.model.CurriculumTopic
import kotlinx.coroutines.launch

class LessonViewModel(
    private val topicId: String,
    curriculumRepository: CurriculumRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {
    val topic: CurriculumTopic? = curriculumRepository.getTopic(topicId)

    fun markComplete() {
        viewModelScope.launch { progressRepository.markCompleted(topicId) }
    }
}
