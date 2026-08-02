package com.khanblair.kotlintutor.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.khanblair.kotlintutor.di.AppContainer
import com.khanblair.kotlintutor.ui.components.KotlinTutorBottomBar

private val BOTTOM_BAR_ROUTES = setOf(ROUTE_ROADMAP, ROUTE_SETTINGS)

@Composable
fun KotlinTutorApp(container: AppContainer) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in BOTTOM_BAR_ROUTES

    Scaffold(
        // Each screen has its own inner Scaffold/TopAppBar that already handles
        // status-bar/nav-bar insets. Without this, the outer Scaffold's default
        // contentWindowInsets (safeDrawing) reserves that space *again*, on top
        // of the inner Scaffold's own reservation — doubling the padding at
        // both the top (status bar) and bottom (nav bar) edges.
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                KotlinTutorBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(ROUTE_ROADMAP) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            KotlinTutorNavHost(container = container, navController = navController)
        }
    }
}
