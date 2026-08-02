package com.khanblair.kotlintutor.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val base = Typography()

// Default Material 3 type scale, set to Inter (see Font.kt) with a couple of
// tasteful tweaks: a firmer screen-title weight and slightly roomier body
// line-height for the long lesson explanations.
val Typography = Typography(
    displayLarge = base.displayLarge.copy(fontFamily = bodyFontFamily),
    displayMedium = base.displayMedium.copy(fontFamily = bodyFontFamily),
    displaySmall = base.displaySmall.copy(fontFamily = bodyFontFamily),
    headlineLarge = base.headlineLarge.copy(fontFamily = bodyFontFamily),
    headlineMedium = base.headlineMedium.copy(fontFamily = bodyFontFamily),
    headlineSmall = base.headlineSmall.copy(fontFamily = bodyFontFamily),
    titleLarge = base.titleLarge.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.SemiBold),
    titleMedium = base.titleMedium.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.SemiBold),
    titleSmall = base.titleSmall.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.SemiBold),
    bodyLarge = base.bodyLarge.copy(fontFamily = bodyFontFamily, lineHeight = 26.sp),
    bodyMedium = base.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = base.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = base.labelLarge.copy(fontFamily = bodyFontFamily, fontWeight = FontWeight.SemiBold),
    labelMedium = base.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = base.labelSmall.copy(fontFamily = bodyFontFamily),
)
