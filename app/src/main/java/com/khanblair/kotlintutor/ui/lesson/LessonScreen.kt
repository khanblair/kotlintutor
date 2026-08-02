package com.khanblair.kotlintutor.ui.lesson

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.khanblair.kotlintutor.model.Recap

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
        topBar = { TopAppBar(title = { Text(topic?.title ?: "Lesson") }) },
    ) { padding ->
        if (topic == null) {
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                Text("This topic doesn't have a lesson yet.")
            }
            return@Scaffold
        }
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                topic.recap?.let { recap ->
                    item { RecapCard(recap) }
                }
                item {
                    Text(
                        text = topic.explain,
                        modifier = Modifier.padding(bottom = 16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                if (topic.example.isNotBlank()) {
                    item {
                        Text(
                            text = topic.example,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                                .padding(bottom = 16.dp),
                            fontFamily = FontFamily.Monospace,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
                item {
                    Text(
                        text = "Key points & pitfalls",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                }
                items(topic.keyPoints) { point ->
                    Text(
                        text = "• $point",
                        modifier = Modifier.padding(bottom = 8.dp),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                OutlinedButton(onClick = {
                    viewModel.markComplete()
                    onBack()
                }) {
                    Text("Mark Complete")
                }
                OutlinedButton(onClick = onAskTutor) {
                    Text("Ask the Tutor")
                }
                Button(onClick = onTakeQuiz) {
                    Text("Take Quiz")
                }
            }
        }
    }
}

@Composable
private fun RecapCard(recap: Recap) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
            .padding(12.dp)
            .padding(bottom = 16.dp),
    ) {
        Text(
            text = "Recap — ${recap.previousTopicTitle}",
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = recap.recapText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 4.dp),
        )
        Text(
            text = "Quick check: ${recap.quickCheckQuestion}",
            style = MaterialTheme.typography.bodySmall,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(top = 8.dp),
        )
        Text(
            text = "→ ${recap.quickCheckAnswer}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 2.dp),
        )
    }
}

