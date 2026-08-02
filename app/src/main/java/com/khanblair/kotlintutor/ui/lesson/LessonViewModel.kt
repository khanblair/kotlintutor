package com.khanblair.kotlintutor.ui.lesson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanblair.kotlintutor.data.content.ContentRepository
import com.khanblair.kotlintutor.data.progress.ProgressRepository
import com.khanblair.kotlintutor.model.Lesson
import kotlinx.coroutines.launch

class LessonViewModel(
    private val topicId: String,
    contentRepository: ContentRepository,
    private val progressRepository: ProgressRepository,
) : ViewModel() {
    val lesson: Lesson? = contentRepository.getLesson(topicId)

    fun markComplete() {
        viewModelScope.launch { progressRepository.markCompleted(topicId) }
    }
}
