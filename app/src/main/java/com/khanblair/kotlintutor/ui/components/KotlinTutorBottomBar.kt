package com.khanblair.kotlintutor.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.khanblair.kotlintutor.ui.navigation.ROUTE_ROADMAP
import com.khanblair.kotlintutor.ui.navigation.ROUTE_SETTINGS

private data class BottomBarDestination(val route: String, val label: String, val icon: ImageVector)

private val bottomBarDestinations = listOf(
    BottomBarDestination(ROUTE_ROADMAP, "Roadmap", Icons.Filled.Home),
    BottomBarDestination(ROUTE_SETTINGS, "Settings", Icons.Filled.Settings),
)

/**
 * Space the floating pill occupies at the bottom of the screen (its own
 * height plus surrounding margin). Screens shown with this bar visible
 * must reserve at least this much bottom content padding so their last
 * item can scroll fully clear of it — the pill floats over content
 * rather than reserving layout space itself.
 */
val FloatingNavBarReservedHeight = 96.dp

/**
 * A floating pill-shaped nav bar (not a full-width Material NavigationBar).
 * Must be placed in a [androidx.compose.foundation.layout.Box] overlay
 * aligned to the bottom, not a Scaffold `bottomBar` slot — that slot
 * reserves height and would prevent it from floating over content.
 */
@Composable
fun KotlinTutorBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 3.dp,
        shadowElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(36.dp),
        ) {
            bottomBarDestinations.forEach { destination ->
                val selected = currentRoute == destination.route
                val tint = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable(onClick = { onNavigate(destination.route) })
                        .padding(horizontal = 14.dp, vertical = 4.dp),
                ) {
                    Icon(destination.icon, contentDescription = destination.label, tint = tint)
                    Text(destination.label, color = tint, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
