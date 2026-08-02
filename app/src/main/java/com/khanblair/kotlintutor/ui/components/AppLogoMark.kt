package com.khanblair.kotlintutor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khanblair.kotlintutor.ui.theme.codeFontFamily

/** The app's abstract mark — a code chevron badge — used in the Roadmap header and mirrored by the launcher icon. */
@Composable
fun AppLogoMark(modifier: Modifier = Modifier, size: Dp = 32.dp) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.3f))
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = ">",
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            fontFamily = codeFontFamily,
            fontSize = (size.value * 0.5f).sp,
        )
    }
}
