package com.khanblair.kotlintutor.ui.settings

import androidx.lifecycle.ViewModel
import com.khanblair.kotlintutor.data.settings.ThemePreferences
import com.khanblair.kotlintutor.data.tutor.ApiKeyStore
import com.khanblair.kotlintutor.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val apiKey: String = "",
    val saved: Boolean = false,
)

class SettingsViewModel(
    private val apiKeyStore: ApiKeyStore,
    private val themePreferences: ThemePreferences,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState(apiKey = apiKeyStore.getApiKey().orEmpty()))
    val uiState: StateFlow<SettingsUiState> = _uiState

    val themeMode: StateFlow<ThemeMode> = themePreferences.themeMode

    fun updateApiKey(value: String) {
        _uiState.update { it.copy(apiKey = value, saved = false) }
    }

    fun save() {
        apiKeyStore.setApiKey(_uiState.value.apiKey.trim())
        _uiState.update { it.copy(saved = true) }
    }

    fun clear() {
        apiKeyStore.clearApiKey()
        _uiState.update { it.copy(apiKey = "", saved = false) }
    }

    fun setThemeMode(mode: ThemeMode) {
        themePreferences.setThemeMode(mode)
    }
}
