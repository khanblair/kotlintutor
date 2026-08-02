package com.khanblair.kotlintutor.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Filled, app-primary-colored action button. Use for the single most
 * important action on a screen (Save, Done, Mark Complete).
 */
@Composable
fun KotlinTutorButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = ButtonDefaults.shape,
        content = content,
    )
}

/**
 * Outlined secondary action button. Use alongside a [KotlinTutorButton]
 * for a less prominent alternative action (Retry, Clear, Ask the Tutor).
 */
@Composable
fun KotlinTutorSecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        content = content,
    )
}

/**
 * Low-emphasis text-only button. Use for tertiary/dismissive actions
 * that shouldn't compete visually with the primary or secondary action.
 */
@Composable
fun KotlinTutorTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        content = content,
    )
}

/** Standard horizontal gap between two side-by-side action buttons. */
val ButtonRowSpacing = 12.dp
