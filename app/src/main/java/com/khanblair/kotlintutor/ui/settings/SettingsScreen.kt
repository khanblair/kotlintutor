package com.khanblair.kotlintutor.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.khanblair.kotlintutor.R
import com.khanblair.kotlintutor.ui.components.FloatingNavBarReservedHeight
import com.khanblair.kotlintutor.ui.components.KotlinTutorButton
import com.khanblair.kotlintutor.ui.components.KotlinTutorDropdown
import com.khanblair.kotlintutor.ui.components.KotlinTutorSecondaryButton
import com.khanblair.kotlintutor.ui.components.KotlinTutorTextField
import com.khanblair.kotlintutor.ui.components.KotlinTutorTopBar
import com.khanblair.kotlintutor.ui.theme.ThemeMode
import com.khanblair.kotlintutor.ui.theme.successColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()

    Scaffold(
        // Only the top inset is reserved here — the outer app-level Scaffold's
        // bottom nav bar (KotlinTutorBottomBar) already reserves the bottom
        // system inset itself. Reserving it again here (the Scaffold default)
        // would leave a redundant gap between this screen's content and the
        // bottom nav bar.
        contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Top),
        // No back arrow: Settings is a bottom-nav tab, reached only from the
        // pill nav bar, so a back action here would be redundant/confusing.
        topBar = { KotlinTutorTopBar(title = { Text(stringResource(R.string.settings_title)) }) },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ThemeCard(themeMode = themeMode, onThemeModeChange = viewModel::setThemeMode)

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 8.dp),
                        )
                        Text(stringResource(R.string.settings_api_key_title), style = MaterialTheme.typography.titleMedium)
                    }
                    Text(
                        text = stringResource(R.string.settings_api_key_usage),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                    Text(
                        text = stringResource(R.string.settings_api_key_optional),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                    )
                    KotlinTutorTextField(
                        value = uiState.apiKey,
                        onValueChange = viewModel::updateApiKey,
                        label = stringResource(R.string.settings_api_key_label),
                        visualTransformation = PasswordVisualTransformation(),
                    )
                    Row(
                        modifier = Modifier.padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        KotlinTutorButton(onClick = viewModel::save, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.padding(end = 6.dp))
                            Text(stringResource(R.string.settings_save))
                        }
                        KotlinTutorSecondaryButton(onClick = viewModel::clear, modifier = Modifier.weight(1f)) {
                            Icon(Icons.Filled.Clear, contentDescription = null, modifier = Modifier.padding(end = 6.dp))
                            Text(stringResource(R.string.settings_clear))
                        }
                    }
                    if (uiState.saved) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 12.dp),
                        ) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = null,
                                tint = successColor,
                                modifier = Modifier.padding(end = 6.dp),
                            )
                            Text(text = stringResource(R.string.settings_saved), color = successColor, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            // Reserves space for the floating pill nav bar so this card's
            // bottom edge (and the "Saved" confirmation below it) never
            // sits behind it — the pill floats over content, it doesn't
            // push it up the way a docked bottom bar would.
            Spacer(modifier = Modifier.height(FloatingNavBarReservedHeight))
        }
    }
}

@Composable
private fun ThemeCard(themeMode: ThemeMode, onThemeModeChange: (ThemeMode) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(R.string.settings_appearance), style = MaterialTheme.typography.titleMedium)
            KotlinTutorDropdown(
                modifier = Modifier.padding(top = 12.dp),
                label = stringResource(R.string.settings_theme),
                selected = themeMode,
                options = listOf(ThemeMode.SYSTEM, ThemeMode.LIGHT, ThemeMode.DARK),
                labelFor = {
                    when (it) {
                        ThemeMode.SYSTEM -> stringResource(R.string.theme_system)
                        ThemeMode.LIGHT -> stringResource(R.string.theme_light)
                        ThemeMode.DARK -> stringResource(R.string.theme_dark)
                    }
                },
                onSelected = onThemeModeChange,
            )
        }
    }
}
