package com.khanblair.kotlintutor.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.khanblair.kotlintutor.ui.navigation.ROUTE_ROADMAP
import com.khanblair.kotlintutor.ui.navigation.ROUTE_SETTINGS

private data class BottomBarDestination(val route: String, val label: String, val icon: ImageVector)

private val bottomBarDestinations = listOf(
    BottomBarDestination(ROUTE_ROADMAP, "Roadmap", Icons.Filled.Home),
    BottomBarDestination(ROUTE_SETTINGS, "Settings", Icons.Filled.Settings),
)

@Composable
fun KotlinTutorBottomBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar {
        bottomBarDestinations.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = { onNavigate(destination.route) },
                icon = { Icon(destination.icon, contentDescription = destination.label) },
                label = { Text(destination.label) },
            )
        }
    }
}
