package com.khanblair.kotlintutor.ui.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.khanblair.kotlintutor.model.QuizQuestion
import com.khanblair.kotlintutor.ui.components.KotlinTutorTopBar
import com.khanblair.kotlintutor.ui.theme.successColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onBack: () -> Unit,
    onDone: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { KotlinTutorTopBar(title = { Text("Quiz") }, onBack = onBack) },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
        ) {
            if (uiState.submitted) {
                ScoreBanner(score = uiState.score ?: 0, total = uiState.questions.size)
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    OutlinedButton(onClick = { viewModel.retry() }, modifier = Modifier.weight(1f)) { Text("Retry") }
                    Button(onClick = onDone, modifier = Modifier.weight(1f)) { Text("Done") }
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
private fun ScoreBanner(score: Int, total: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Text(
            text = "Score: $score / $total",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
private fun QuestionCard(
    question: QuizQuestion,
    selectedIndex: Int?,
    submitted: Boolean,
    onSelect: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                question.question,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            question.options.forEachIndexed { index, option ->
                OptionRow(
                    option = option,
                    selected = selectedIndex == index,
                    isCorrectAnswer = submitted && index == question.correctIndex,
                    isWrongSelection = submitted && index == selectedIndex && index != question.correctIndex,
                    enabled = !submitted,
                    onSelect = { onSelect(index) },
                )
            }
            if (submitted) {
                Text(
                    question.explanation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun OptionRow(
    option: String,
    selected: Boolean,
    isCorrectAnswer: Boolean,
    isWrongSelection: Boolean,
    enabled: Boolean,
    onSelect: () -> Unit,
) {
    val containerColor = when {
        isCorrectAnswer -> successColor.copy(alpha = 0.15f)
        isWrongSelection -> MaterialTheme.colorScheme.errorContainer
        else -> Color.Transparent
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .background(containerColor, RoundedCornerShape(10.dp))
            .selectable(selected = selected, enabled = enabled, onClick = onSelect)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            enabled = enabled,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary),
        )
        Text(option, modifier = Modifier.weight(1f))
        if (isCorrectAnswer) Icon(Icons.Filled.Check, contentDescription = "Correct answer", tint = successColor)
        if (isWrongSelection) Icon(Icons.Filled.Close, contentDescription = "Your answer", tint = MaterialTheme.colorScheme.error)
    }
}
