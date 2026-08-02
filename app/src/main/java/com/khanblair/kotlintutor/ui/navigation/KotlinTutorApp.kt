package com.khanblair.kotlintutor.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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

    // A plain Box overlay, not a Scaffold with a bottomBar slot: that slot
    // reserves layout height for its content, which would prevent the pill
    // nav bar from floating over the screen content beneath it. Each screen
    // has its own inner Scaffold handling its own status-bar inset.
    Box(modifier = Modifier.fillMaxSize()) {
        KotlinTutorNavHost(container = container, navController = navController)
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
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}
