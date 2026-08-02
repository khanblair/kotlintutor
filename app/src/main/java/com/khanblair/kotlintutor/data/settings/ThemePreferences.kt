package com.khanblair.kotlintutor.data.settings

import android.content.Context
import com.khanblair.kotlintutor.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ThemePreferences {
    val themeMode: StateFlow<ThemeMode>
    fun setThemeMode(mode: ThemeMode)
}

class SharedPrefsThemePreferences(context: Context) : ThemePreferences {
    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(readThemeMode())
    override val themeMode: StateFlow<ThemeMode> = _themeMode

    override fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
        _themeMode.value = mode
    }

    private fun readThemeMode(): ThemeMode {
        val stored = prefs.getString(KEY_THEME_MODE, null) ?: return ThemeMode.SYSTEM
        return runCatching { ThemeMode.valueOf(stored) }.getOrDefault(ThemeMode.SYSTEM)
    }

    private companion object {
        const val PREFS_NAME = "kotlintutor_prefs"
        const val KEY_THEME_MODE = "theme_mode"
    }
}
