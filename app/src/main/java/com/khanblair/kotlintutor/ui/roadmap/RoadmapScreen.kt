package com.khanblair.kotlintutor.ui.roadmap

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.khanblair.kotlintutor.model.RoadmapItem

@Composable
fun RoadmapScreen(
    viewModel: RoadmapViewModel,
    onLessonClick: (topicId: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    RoadmapContent(
        uiState = uiState,
        onTopicClick = { item ->
            if (item.node.hasContent) onLessonClick(item.node.id) else viewModel.markCompleted(item.node.id)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoadmapContent(
    uiState: RoadmapUiState,
    onTopicClick: (RoadmapItem) -> Unit,
) {
    val categories = uiState.items.filter { it.node.parentId == null }
    val childrenByCategory = uiState.items.filter { it.node.parentId != null }.groupBy { it.node.parentId }
    var expandedIds by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Kotlin Roadmap") }) },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("${uiState.completionPercent}% complete", style = MaterialTheme.typography.titleMedium)
                LinearProgressIndicator(
                    progress = { uiState.completionPercent / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                )
            }
            LazyColumn {
                items(categories, key = { it.node.id }) { category ->
                    val expanded = category.node.id in expandedIds
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedIds = if (expanded) {
                                    expandedIds - category.node.id
                                } else {
                                    expandedIds + category.node.id
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(category.node.title, style = MaterialTheme.typography.titleMedium)
                        Text(if (expanded) "▾" else "▸", style = MaterialTheme.typography.titleMedium)
                    }
                    if (expanded) {
                        childrenByCategory[category.node.id].orEmpty().forEach { child ->
                            TopicRow(item = child, onClick = { onTopicClick(child) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopicRow(item: RoadmapItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 32.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(item.node.title, style = MaterialTheme.typography.bodyLarge)
            if (!item.node.hasContent) {
                Text("Content coming soon — tap to mark done", style = MaterialTheme.typography.bodySmall)
            }
        }
        Text(if (item.isCompleted) "✓" else "○", style = MaterialTheme.typography.titleMedium)
    }
}
