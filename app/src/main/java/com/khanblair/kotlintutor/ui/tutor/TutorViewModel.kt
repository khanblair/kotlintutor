package com.khanblair.kotlintutor.ui.tutor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanblair.kotlintutor.data.curriculum.CurriculumRepository
import com.khanblair.kotlintutor.data.tutor.ChatMessage
import com.khanblair.kotlintutor.data.tutor.DeepSeekResult
import com.khanblair.kotlintutor.data.tutor.TutorRepository
import com.khanblair.kotlintutor.domain.TutorMode
import com.khanblair.kotlintutor.model.CurriculumTopic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TutorMessage(val role: Role, val content: String) {
    enum class Role { USER, ASSISTANT }
}

data class TutorUiState(
    val topicTitle: String? = null,
    val mode: TutorMode = TutorMode.EXPLAIN,
    val messages: List<TutorMessage> = emptyList(),
    val input: String = "",
    val isSending: Boolean = false,
    val error: String? = null,
    val missingApiKey: Boolean = false,
)

class TutorViewModel(
    topicId: String,
    curriculumRepository: CurriculumRepository,
    private val tutorRepository: TutorRepository,
) : ViewModel() {

    private val topic: CurriculumTopic? = curriculumRepository.getTopic(topicId)

    private val _uiState = MutableStateFlow(TutorUiState(topicTitle = topic?.title))
    val uiState: StateFlow<TutorUiState> = _uiState

    fun selectMode(mode: TutorMode) {
        _uiState.update { it.copy(mode = mode) }
    }

    fun updateInput(text: String) {
        _uiState.update { it.copy(input = text) }
    }

    fun send() {
        val currentTopic = topic ?: return
        val state = _uiState.value
        val userText = state.input.trim()
        if (userText.isEmpty() || state.isSending) return

        val history = state.messages.map {
            ChatMessage(role = if (it.role == TutorMessage.Role.USER) "user" else "assistant", content = it.content)
        }

        _uiState.update {
            it.copy(
                messages = it.messages + TutorMessage(TutorMessage.Role.USER, userText),
                input = "",
                isSending = true,
                error = null,
                missingApiKey = false,
            )
        }

        viewModelScope.launch {
            when (val result = tutorRepository.sendMessage(currentTopic, state.mode, history, userText)) {
                is DeepSeekResult.Success -> _uiState.update {
                    it.copy(
                        messages = it.messages + TutorMessage(TutorMessage.Role.ASSISTANT, result.reply),
                        isSending = false,
                    )
                }
                is DeepSeekResult.MissingApiKey -> _uiState.update {
                    it.copy(isSending = false, missingApiKey = true)
                }
                is DeepSeekResult.Error -> _uiState.update {
                    it.copy(isSending = false, error = result.message)
                }
            }
        }
    }
}
