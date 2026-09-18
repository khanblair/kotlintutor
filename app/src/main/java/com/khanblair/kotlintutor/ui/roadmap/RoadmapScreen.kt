package com.khanblair.kotlintutor.ui.roadmap

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khanblair.kotlintutor.R
import com.khanblair.kotlintutor.model.RoadmapItem
import com.khanblair.kotlintutor.ui.components.AppLogoMark
import com.khanblair.kotlintutor.ui.components.FloatingNavBarReservedHeight
import com.khanblair.kotlintutor.ui.components.KotlinTutorTextField
import com.khanblair.kotlintutor.ui.components.KotlinTutorTopBar
import com.khanblair.kotlintutor.ui.theme.successColor

@Composable
fun RoadmapScreen(
    viewModel: RoadmapViewModel,
    onLessonClick: (topicId: String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
    val topicCount = uiState.items.count { it.node.parentId != null }
    val completedCount = uiState.items.count { it.node.parentId != null && it.isCompleted }
    // Saveable so rotation/process death keeps the search query and which
    // categories are expanded (plain remember resets them).
    var expandedIds by rememberSaveable { mutableStateOf(emptyList<String>()) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val query = searchQuery.trim()
    val isSearching = query.isNotBlank()
    val visibleChildrenByCategory = if (isSearching) {
        childrenByCategory
            .mapValues { (_, children) -> children.filter { it.node.title.contains(query, ignoreCase = true) } }
            .filterValues { it.isNotEmpty() }
    } else {
        childrenByCategory
    }
    val visibleCategories = if (isSearching) {
        categories.filter { it.node.id in visibleChildrenByCategory }
    } else {
        categories
    }
    // Union with the user's manual expand/collapse state (not a replacement)
    // so clearing the search restores whatever they had open before.
    val effectiveExpandedIds = (expandedIds + visibleChildrenByCategory.keys.takeIf { isSearching }.orEmpty()).toSet()

    Scaffold(
        // Only the top inset is reserved here — the outer app-level Scaffold's
        // bottom nav bar (KotlinTutorBottomBar) already reserves the bottom
        // system inset itself. Reserving it again here (the Scaffold default)
        // would leave a redundant gap between this screen's content and the
        // bottom nav bar.
        contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Top),
        topBar = {
            KotlinTutorTopBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AppLogoMark(size = 28.dp)
                        Text(stringResource(R.string.roadmap_title), modifier = Modifier.padding(start = 10.dp))
                    }
                },
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp + FloatingNavBarReservedHeight),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { ProgressHeader(completedCount = completedCount, totalCount = topicCount, percent = uiState.completionPercent) }

            item {
                KotlinTutorTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = stringResource(R.string.roadmap_search_placeholder),
                    leadingIcon = Icons.Filled.Search,
                    leadingIconDescription = null,
                    trailingIcon = if (searchQuery.isNotEmpty()) Icons.Filled.Clear else null,
                    trailingIconDescription = stringResource(R.string.roadmap_clear_search),
                    onTrailingIconClick = if (searchQuery.isNotEmpty()) {
                        { searchQuery = "" }
                    } else {
                        null
                    },
                )
            }

            if (isSearching && visibleCategories.isEmpty()) {
                item {
                    Text(
                        // Escape '%' — stringResource formats args via String.format,
                        // and a raw '%' in the user's query would crash the screen.
                        text = stringResource(R.string.roadmap_no_results, query.replace("%", "%%")),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
            }

            items(visibleCategories, key = { it.node.id }) { category ->
                val expanded = category.node.id in effectiveExpandedIds
                CategoryCard(
                    title = category.node.title,
                    expanded = expanded,
                    onToggle = {
                        // While searching, every matching category is forced open;
                        // toggling here would silently corrupt the user's manual
                        // expansion state (a visible collapse that reverts, then
                        // losing the manual entry once the search is cleared).
                        if (!isSearching) {
                            expandedIds = if (expanded) expandedIds - category.node.id else expandedIds + category.node.id
                        }
                    },
                    children = visibleChildrenByCategory[category.node.id].orEmpty(),
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
                text = stringResource(R.string.roadmap_your_progress),
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
                    text = stringResource(R.string.roadmap_topics_of, completedCount, totalCount),
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
                    contentDescription = stringResource(if (expanded) R.string.roadmap_collapse else R.string.roadmap_expand),
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
                    text = stringResource(R.string.roadmap_content_coming),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            item.lastQuizScore?.let { score ->
                Text(
                    text = stringResource(R.string.roadmap_last_quiz, score),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        StatusDot(completed = item.isCompleted)
    }
}

@Composable
private fun StatusDot(completed: Boolean) {
    if (completed) {
        Icon(Icons.Filled.CheckCircle, contentDescription = stringResource(R.string.roadmap_completed), tint = successColor)
    } else {
        Box(
            modifier = Modifier
                .size(20.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.outline, CircleShape),
        )
    }
}

