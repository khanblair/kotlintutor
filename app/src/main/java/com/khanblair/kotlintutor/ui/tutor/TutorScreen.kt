package com.khanblair.kotlintutor.ui.tutor

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khanblair.kotlintutor.R
import com.khanblair.kotlintutor.domain.TutorMode
import com.khanblair.kotlintutor.ui.components.KotlinTutorTextField
import com.khanblair.kotlintutor.ui.components.KotlinTutorTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorScreen(
    viewModel: TutorViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    val itemCount = uiState.messages.size + if (uiState.isSending) 1 else 0
    val lastMessageContent = uiState.messages.lastOrNull()?.content
    // Autoscroll when a new message appears or the in-progress reply grows —
    // but only when the user is already near the bottom, so reading history
    // mid-stream isn't interrupted.
    LaunchedEffect(lastMessageContent, itemCount) {
        val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        val atBottom = lastVisible == null || lastVisible >= listState.layoutInfo.totalItemsCount - 2
        if (atBottom && itemCount > 0) listState.animateScrollToItem(itemCount - 1)
    }

    Scaffold(
        topBar = {
            KotlinTutorTopBar(
                title = {
                    Text(
                        uiState.topicTitle?.let { stringResource(R.string.tutor_title_with_topic, it) }
                            ?: stringResource(R.string.tutor_title),
                    )
                },
                onBack = onBack,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .imePadding()
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                TutorMode.entries.forEach { mode ->
                    FilterChip(
                        selected = uiState.mode == mode,
                        enabled = !uiState.isSending,
                        onClick = { viewModel.selectMode(mode) },
                        label = { Text(stringResource(mode.labelRes()), maxLines = 1) },
                    )
                }
            }

            if (uiState.missingApiKey) {
                InlineNotice(text = stringResource(R.string.tutor_missing_key))
            }
            uiState.error?.let { message ->
                // Escape '%' — stringResource formats args via String.format, and
                // error text (e.g. a network exception message) may contain one.
                InlineNotice(text = stringResource(R.string.tutor_error_prefix, message.replace("%", "%%")))
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (uiState.messages.isEmpty()) {
                    item { EmptyState(mode = uiState.mode) }
                }
                items(uiState.messages) { message -> MessageBubble(message) }
                if (uiState.isSending) {
                    item { TypingBubble() }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
            ) {
                KotlinTutorTextField(
                    value = uiState.input,
                    onValueChange = viewModel::updateInput,
                    modifier = Modifier.weight(1f),
                    placeholder = stringResource(R.string.tutor_input_placeholder),
                )
                Spacer(modifier = Modifier.width(8.dp))
                if (uiState.isSending) {
                    // Streaming in progress: the send button becomes Stop so the
                    // in-flight request can be aborted instead of waiting it out.
                    FilledIconButton(onClick = viewModel::cancelSend) {
                        Icon(Icons.Filled.Close, contentDescription = stringResource(R.string.tutor_stop))
                    }
                } else {
                    FilledIconButton(
                        onClick = viewModel::send,
                        enabled = uiState.input.isNotBlank(),
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = stringResource(R.string.tutor_send))
                    }
                }
            }
        }
    }
}

@Composable
private fun TutorMode.labelRes(): Int = when (this) {
    TutorMode.EXPLAIN -> R.string.tutor_mode_explain
    TutorMode.QUIZ_ME -> R.string.tutor_mode_quiz
    TutorMode.REVIEW_MY_CODE -> R.string.tutor_mode_review
    TutorMode.GIVE_EXERCISE -> R.string.tutor_mode_exercise
}

@Composable
private fun InlineNotice(text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onErrorContainer,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(12.dp),
        )
    }
}

@Composable
private fun EmptyState(mode: TutorMode) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.tutor_empty_state, stringResource(mode.labelRes())),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun TypingBubble() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainerHigh, bubbleShape(isUser = false))
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
        }
    }
}

@Composable
private fun MessageBubble(message: TutorMessage) {
    val isUser = message.role == TutorMessage.Role.USER
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
    ) {
        // SelectionContainer makes replies (and the code inside them) selectable
        // and copyable — a bare Text isn't.
        SelectionContainer {
            Text(
                text = message.content,
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .background(
                        if (isUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerHigh,
                        bubbleShape(isUser),
                    )
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                color = if (isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

private fun bubbleShape(isUser: Boolean) = RoundedCornerShape(
    topStart = 16.dp,
    topEnd = 16.dp,
    bottomStart = if (isUser) 16.dp else 4.dp,
    bottomEnd = if (isUser) 4.dp else 16.dp,
)
