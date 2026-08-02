@file:OptIn(ExperimentalTextApi::class)

package com.khanblair.kotlintutor.ui.theme

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import com.khanblair.kotlintutor.R

/**
 * Inter (UI text) and JetBrains Mono (code) — both variable fonts from Google
 * Fonts (OFL-licensed, see docs/licenses/). Each weight below loads the same
 * .ttf with a different `wght` axis setting rather than needing separate files.
 */
val bodyFontFamily = FontFamily(
    Font(R.font.inter_variable, FontWeight.Normal, variationSettings = FontVariation.Settings(FontVariation.weight(400))),
    Font(R.font.inter_variable, FontWeight.Medium, variationSettings = FontVariation.Settings(FontVariation.weight(500))),
    Font(R.font.inter_variable, FontWeight.SemiBold, variationSettings = FontVariation.Settings(FontVariation.weight(600))),
    Font(R.font.inter_variable, FontWeight.Bold, variationSettings = FontVariation.Settings(FontVariation.weight(700))),
)

val codeFontFamily = FontFamily(
    Font(R.font.jetbrains_mono_variable, FontWeight.Normal, variationSettings = FontVariation.Settings(FontVariation.weight(400))),
    Font(R.font.jetbrains_mono_variable, FontWeight.Medium, variationSettings = FontVariation.Settings(FontVariation.weight(500))),
    Font(R.font.jetbrains_mono_variable, FontWeight.Bold, variationSettings = FontVariation.Settings(FontVariation.weight(700))),
)
