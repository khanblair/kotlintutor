package com.khanblair.kotlintutor.ui.roadmap

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.khanblair.kotlintutor.model.RoadmapItem
import com.khanblair.kotlintutor.ui.components.AppLogoMark
import com.khanblair.kotlintutor.ui.components.KotlinTutorTopBar
import com.khanblair.kotlintutor.ui.theme.successColor

@Composable
fun RoadmapScreen(
    viewModel: RoadmapViewModel,
    onLessonClick: (topicId: String) -> Unit,
    onSettingsClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    RoadmapContent(
        uiState = uiState,
        onTopicClick = { item ->
            if (item.node.hasContent) onLessonClick(item.node.id) else viewModel.markCompleted(item.node.id)
        },
        onSettingsClick = onSettingsClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoadmapContent(
    uiState: RoadmapUiState,
    onTopicClick: (RoadmapItem) -> Unit,
    onSettingsClick: () -> Unit,
) {
    val categories = uiState.items.filter { it.node.parentId == null }
    val childrenByCategory = uiState.items.filter { it.node.parentId != null }.groupBy { it.node.parentId }
    val topicCount = uiState.items.count { it.node.parentId != null }
    val completedCount = uiState.items.count { it.node.parentId != null && it.isCompleted }
    var expandedIds by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = {
            KotlinTutorTopBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AppLogoMark(size = 28.dp)
                        Text("Kotlin Roadmap", modifier = Modifier.padding(start = 10.dp))
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { ProgressHeader(completedCount = completedCount, totalCount = topicCount, percent = uiState.completionPercent) }

            items(categories, key = { it.node.id }) { category ->
                val expanded = category.node.id in expandedIds
                CategoryCard(
                    title = category.node.title,
                    expanded = expanded,
                    onToggle = {
                        expandedIds = if (expanded) expandedIds - category.node.id else expandedIds + category.node.id
                    },
                    children = childrenByCategory[category.node.id].orEmpty(),
                    onTopicClick = onTopicClick,
                )
            }
        }
    }
}

@Composable
private fun ProgressHeader(completedCount: Int, totalCount: Int, percent: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Your progress",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(top = 4.dp),
            ) {
                Text(
                    text = "$percent%",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                Text(
                    text = "  $completedCount of $totalCount topics",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            }
            LinearProgressIndicator(
                progress = { percent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(50)),
                trackColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
            )
        }
    }
}

@Composable
private fun CategoryCard(
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    children: List<RoadmapItem>,
    onTopicClick: (RoadmapItem) -> Unit,
) {
    val rotation by animateFloatAsState(targetValue = if (expanded) 180f else 0f, label = "chevron")

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onToggle)
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    modifier = Modifier.rotate(rotation),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            if (expanded) {
                children.forEach { child -> TopicRow(item = child, onClick = { onTopicClick(child) }) }
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
            .padding(start = 20.dp, end = 16.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.node.title, style = MaterialTheme.typography.bodyLarge)
            if (!item.node.hasContent) {
                Text(
                    text = "Content coming soon — tap to mark done",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        StatusDot(completed = item.isCompleted)
    }
}

@Composable
private fun StatusDot(completed: Boolean) {
    if (completed) {
        Icon(Icons.Filled.CheckCircle, contentDescription = "Completed", tint = successColor)
    } else {
        Box(
            modifier = Modifier
                .size(20.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.outline, CircleShape),
        )
    }
}

