package com.khanblair.kotlintutor.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

/**
 * The single text input used everywhere in the app (search, API key,
 * chat message, etc.) — consistent outlined style and optional
 * leading/trailing icon slots. [shape] defaults to the standard outlined
 * field shape but can be overridden (e.g. a fully-rounded pill for a
 * chat composer bar).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KotlinTutorTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    leadingIconDescription: String? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    trailingIcon: ImageVector? = null,
    trailingIconDescription: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    shape: Shape = OutlinedTextFieldDefaults.shape,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon?.let {
            {
                Icon(it, contentDescription = leadingIconDescription)
            }
        },
        trailingIcon = trailingIcon?.let { icon ->
            {
                if (onTrailingIconClick != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(icon, contentDescription = trailingIconDescription)
                    }
                } else {
                    Icon(icon, contentDescription = trailingIconDescription)
                }
            }
        },
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = shape,
    )
}
