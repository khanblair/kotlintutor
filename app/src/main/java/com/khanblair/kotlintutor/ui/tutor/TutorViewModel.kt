package com.khanblair.kotlintutor.ui.tutor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khanblair.kotlintutor.data.curriculum.CurriculumRepository
import com.khanblair.kotlintutor.data.tutor.ChatMessage
import com.khanblair.kotlintutor.data.tutor.DeepSeekResult
import com.khanblair.kotlintutor.data.tutor.TutorRepository
import com.khanblair.kotlintutor.domain.TutorMode
import com.khanblair.kotlintutor.model.CurriculumTopic
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/** How many recent conversation turns are sent per request — DeepSeek bills per token,
 *  so an unbounded history would make long chats slow and expensive. */
private const val HISTORY_LIMIT = 10

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

    private var sendJob: Job? = null

    /**
     * A mode change resets the conversation: the mode is baked into the system
     * prompt on every request, so reinterpreting old turns under a new mode (e.g.
     * an Explain chat suddenly graded as a quiz) would produce nonsense. An
     * in-flight response is left alone.
     */
    fun selectMode(mode: TutorMode) {
        _uiState.update { st ->
            when {
                st.isSending -> st
                st.mode == mode -> st
                st.messages.isEmpty() -> st.copy(mode = mode)
                else -> st.copy(mode = mode, messages = emptyList(), error = null, missingApiKey = false)
            }
        }
    }

    fun updateInput(text: String) {
        _uiState.update { it.copy(input = text) }
    }

    fun send() {
        val currentTopic = topic ?: return
        val state = _uiState.value
        val userText = state.input.trim()
        if (userText.isEmpty() || state.isSending) return

        val history = state.messages.takeLast(HISTORY_LIMIT).map {
            ChatMessage(role = if (it.role == TutorMessage.Role.USER) "user" else "assistant", content = it.content)
        }

        // Append the user message plus an empty assistant placeholder that each
        // streamed delta replaces, so the UI shows tokens arriving live.
        _uiState.update {
            it.copy(
                messages = it.messages + TutorMessage(TutorMessage.Role.USER, userText) + TutorMessage(TutorMessage.Role.ASSISTANT, ""),
                input = "",
                isSending = true,
                error = null,
                missingApiKey = false,
            )
        }

        sendJob?.cancel()
        sendJob = viewModelScope.launch {
            try {
                when (val result = tutorRepository.streamMessage(currentTopic, state.mode, history, userText) { accumulated ->
                    _uiState.update { st -> st.withLastAssistantMessage(accumulated) }
                }) {
                    is DeepSeekResult.Success -> _uiState.update { st ->
                        st.withLastAssistantMessage(result.reply).copy(isSending = false)
                    }
                    is DeepSeekResult.MissingApiKey -> _uiState.update { st ->
                        st.copy(isSending = false, missingApiKey = true).withoutEmptyAssistantPlaceholder()
                    }
                    is DeepSeekResult.Error -> _uiState.update { st ->
                        st.copy(isSending = false, error = result.message).withoutEmptyAssistantPlaceholder()
                    }
                }
            } catch (e: CancellationException) {
                // Stop was pressed: keep whatever reply streamed so far, but drop
                // the placeholder if no token arrived yet — otherwise an empty
                // bubble would linger in the conversation permanently.
                _uiState.update { st ->
                    val last = st.messages.lastOrNull()
                    if (last?.role == TutorMessage.Role.ASSISTANT && last.content.isEmpty()) {
                        st.copy(messages = st.messages.dropLast(1))
                    } else {
                        st
                    }
                }
            } finally {
                _uiState.update { it.copy(isSending = false) }
                sendJob = null
            }
        }
    }

    /** Aborts the in-flight request, keeping whatever reply streamed so far. */
    fun cancelSend() {
        sendJob?.cancel()
    }

    private fun TutorUiState.withLastAssistantMessage(content: String): TutorUiState =
        copy(messages = messages.dropLast(1) + TutorMessage(TutorMessage.Role.ASSISTANT, content))

    private fun TutorUiState.withoutEmptyAssistantPlaceholder(): TutorUiState =
        copy(messages = messages.dropLast(1))
}
