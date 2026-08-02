package com.khanblair.kotlintutor.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanblair.kotlintutor.data.curriculum.CurriculumRepository
import com.khanblair.kotlintutor.data.progress.ProgressRepository
import com.khanblair.kotlintutor.domain.QuizScorer
import com.khanblair.kotlintutor.model.QuizQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val answers: Map<String, Int> = emptyMap(),
    val submitted: Boolean = false,
    val score: Int? = null,
)

class QuizViewModel(
    private val topicId: String,
    curriculumRepository: CurriculumRepository,
    private val progressRepository: ProgressRepository,
    private val currentTimeMillis: () -> Long = System::currentTimeMillis,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        QuizUiState(questions = curriculumRepository.getTopic(topicId)?.quiz ?: emptyList()),
    )
    val uiState: StateFlow<QuizUiState> = _uiState

    fun selectAnswer(questionId: String, optionIndex: Int) {
        if (_uiState.value.submitted) return
        _uiState.update { it.copy(answers = it.answers + (questionId to optionIndex)) }
    }

    fun submit() {
        val state = _uiState.value
        if (state.submitted) return
        val score = QuizScorer.score(state.questions, state.answers)
        _uiState.update { it.copy(submitted = true, score = score) }
        viewModelScope.launch { progressRepository.recordQuizScore(topicId, score, currentTimeMillis()) }
    }

    fun retry() {
        _uiState.update { it.copy(answers = emptyMap(), submitted = false, score = null) }
    }
}
