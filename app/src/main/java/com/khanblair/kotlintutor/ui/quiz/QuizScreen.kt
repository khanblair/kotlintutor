package com.khanblair.kotlintutor.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khanblair.kotlintutor.model.QuizQuestion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onDone: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Quiz") }) },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
        ) {
            if (uiState.submitted) {
                Text(
                    "Score: ${uiState.score} / ${uiState.questions.size}",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(uiState.questions, key = { it.id }) { question ->
                    QuestionCard(
                        question = question,
                        selectedIndex = uiState.answers[question.id],
                        submitted = uiState.submitted,
                        onSelect = { index -> viewModel.selectAnswer(question.id, index) },
                    )
                }
            }
            if (uiState.submitted) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Button(onClick = { viewModel.retry() }) { Text("Retry") }
                    Button(onClick = onDone) { Text("Done") }
                }
            } else {
                Button(
                    onClick = { viewModel.submit() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = uiState.answers.size == uiState.questions.size,
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

@Composable
private fun QuestionCard(
    question: QuizQuestion,
    selectedIndex: Int?,
    submitted: Boolean,
    onSelect: (Int) -> Unit,
) {
    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Text(question.question, style = MaterialTheme.typography.titleMedium)
        question.options.forEachIndexed { index, option ->
            val isCorrect = submitted && index == question.correctIndex
            val isWrongSelection = submitted && index == selectedIndex && index != question.correctIndex
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedIndex == index,
                        enabled = !submitted,
                        onClick = { onSelect(index) },
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(selected = selectedIndex == index, onClick = { onSelect(index) }, enabled = !submitted)
                Text(option)
                if (isCorrect) Text("  ✓", color = MaterialTheme.colorScheme.primary)
                if (isWrongSelection) Text("  ✗", color = MaterialTheme.colorScheme.error)
            }
        }
        if (submitted) {
            Text(
                question.explanation,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}
