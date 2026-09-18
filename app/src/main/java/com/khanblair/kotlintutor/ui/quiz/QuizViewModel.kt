package com.khanblair.kotlintutor.ui.quiz

import androidx.lifecycle.SavedStateHandle
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
    private val savedStateHandle: SavedStateHandle,
    private val currentTimeMillis: () -> Long = System::currentTimeMillis,
) : ViewModel() {

    // Answers/submitted/score are mirrored into the SavedStateHandle so a quiz
    // in progress survives process death, not just configuration changes.
    private val _uiState = MutableStateFlow(
        QuizUiState(
            questions = curriculumRepository.getTopic(topicId)?.quiz ?: emptyList(),
            answers = savedStateHandle.get<Map<String, Int>>(KEY_ANSWERS) ?: emptyMap(),
            submitted = savedStateHandle.get<Boolean>(KEY_SUBMITTED) ?: false,
            score = savedStateHandle.get<Int>(KEY_SCORE),
        ),
    )
    val uiState: StateFlow<QuizUiState> = _uiState

    fun selectAnswer(questionId: String, optionIndex: Int) {
        if (_uiState.value.submitted) return
        val newAnswers = _uiState.value.answers + (questionId to optionIndex)
        _uiState.update { it.copy(answers = newAnswers) }
        savedStateHandle[KEY_ANSWERS] = newAnswers
    }

    fun submit() {
        val state = _uiState.value
        if (state.submitted) return
        val score = QuizScorer.score(state.questions, state.answers)
        _uiState.update { it.copy(submitted = true, score = score) }
        savedStateHandle[KEY_SUBMITTED] = true
        savedStateHandle[KEY_SCORE] = score
        viewModelScope.launch { progressRepository.recordQuizScore(topicId, score, currentTimeMillis()) }
    }

    fun retry() {
        _uiState.update { it.copy(answers = emptyMap(), submitted = false, score = null) }
        savedStateHandle[KEY_ANSWERS] = emptyMap<String, Int>()
        savedStateHandle[KEY_SUBMITTED] = false
        savedStateHandle[KEY_SCORE] = null
    }

    private companion object {
        const val KEY_ANSWERS = "answers"
        const val KEY_SUBMITTED = "submitted"
        const val KEY_SCORE = "score"
    }
}
