package com.khanblair.kotlintutor.ui.lesson

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.khanblair.kotlintutor.model.Recap
import com.khanblair.kotlintutor.ui.components.KotlinTutorButton
import com.khanblair.kotlintutor.ui.components.KotlinTutorSecondaryButton
import com.khanblair.kotlintutor.ui.components.KotlinTutorTopBar
import com.khanblair.kotlintutor.ui.theme.codeFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    viewModel: LessonViewModel,
    onTakeQuiz: () -> Unit,
    onAskTutor: () -> Unit,
    onBack: () -> Unit,
) {
    val topic = viewModel.topic

    Scaffold(
        topBar = { KotlinTutorTopBar(title = { Text(topic?.title ?: "Lesson") }, onBack = onBack) },
    ) { padding ->
        if (topic == null) {
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                Text("This topic doesn't have a lesson yet.")
            }
            return@Scaffold
        }
        Column(modifier = Modifier.padding(padding).padding(horizontal = 16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                topic.recap?.let { recap ->
                    item { RecapCard(recap, modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)) }
                }
                item {
                    Text(
                        text = topic.explain,
                        modifier = Modifier.padding(bottom = 16.dp, top = if (topic.recap == null) 12.dp else 0.dp),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                if (topic.example.isNotBlank()) {
                    item { CodeBlock(topic.example, modifier = Modifier.padding(bottom = 16.dp)) }
                }
                item {
                    Text(
                        text = "Key points & pitfalls",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                }
                items(topic.keyPoints) { point -> KeyPointRow(point) }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    KotlinTutorSecondaryButton(
                        onClick = {
                            viewModel.markComplete()
                            onBack()
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.padding(end = 6.dp))
                        Text("Complete")
                    }
                    KotlinTutorSecondaryButton(onClick = onAskTutor, modifier = Modifier.weight(1f)) {
                        Text("Ask Tutor")
                    }
                }
                KotlinTutorButton(
                    onClick = onTakeQuiz,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                ) {
                    Text("Take Quiz")
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun CodeBlock(code: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHighest),
    ) {
        Text(
            text = code,
            modifier = Modifier.padding(14.dp),
            fontFamily = codeFontFamily,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun KeyPointRow(point: String) {
    Row(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = "•",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 10.dp),
        )
        Text(text = point, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun RecapCard(recap: Recap, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(end = 6.dp),
                )
                Text(
                    text = "Recap — ${recap.previousTopicTitle}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
            Text(
                text = recap.recapText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(top = 6.dp),
            )
            Text(
                text = "Quick check: ${recap.quickCheckQuestion}",
                style = MaterialTheme.typography.bodySmall,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(top = 8.dp),
            )
            Text(
                text = "→ ${recap.quickCheckAnswer}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
    }
}
