package com.khanblair.kotlintutor.ui.lesson

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    viewModel: LessonViewModel,
    onTakeQuiz: () -> Unit,
    onBack: () -> Unit,
) {
    val lesson = viewModel.lesson

    Scaffold(
        topBar = { TopAppBar(title = { Text(lesson?.title ?: "Lesson") }) },
    ) { padding ->
        if (lesson == null) {
            Column(modifier = Modifier.padding(padding).padding(16.dp)) {
                Text("This topic doesn't have a lesson yet.")
            }
            return@Scaffold
        }
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(lesson.sections) { section ->
                    Text(
                        section,
                        modifier = Modifier.padding(bottom = 16.dp),
                        style = MaterialTheme.typography.bodyLarge,
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
                Button(onClick = onTakeQuiz) {
                    Text("Take Quiz")
                }
            }
        }
    }
}
