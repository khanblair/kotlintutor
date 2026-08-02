package com.khanblair.kotlintutor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khanblair.kotlintutor.di.AppContainer
import com.khanblair.kotlintutor.ui.lesson.LessonScreen
import com.khanblair.kotlintutor.ui.lesson.LessonViewModel
import com.khanblair.kotlintutor.ui.quiz.QuizScreen
import com.khanblair.kotlintutor.ui.quiz.QuizViewModel
import com.khanblair.kotlintutor.ui.roadmap.RoadmapScreen
import com.khanblair.kotlintutor.ui.roadmap.RoadmapViewModel

private const val ROUTE_ROADMAP = "roadmap"
private const val ROUTE_LESSON = "lesson/{topicId}"
private const val ROUTE_QUIZ = "quiz/{topicId}"

@Composable
fun KotlinTutorNavHost(
    container: AppContainer,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = ROUTE_ROADMAP) {
        composable(ROUTE_ROADMAP) {
            val viewModel: RoadmapViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { RoadmapViewModel(container.roadmapRepository, container.progressRepository) }
                },
            )
            RoadmapScreen(
                viewModel = viewModel,
                onLessonClick = { topicId -> navController.navigate("lesson/$topicId") },
            )
        }
        composable(ROUTE_LESSON) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId").orEmpty()
            val viewModel: LessonViewModel = viewModel(
                key = topicId,
                factory = viewModelFactory {
                    initializer { LessonViewModel(topicId, container.contentRepository, container.progressRepository) }
                },
            )
            LessonScreen(
                viewModel = viewModel,
                onTakeQuiz = { navController.navigate("quiz/$topicId") },
                onBack = { navController.popBackStack() },
            )
        }
        composable(ROUTE_QUIZ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId").orEmpty()
            val viewModel: QuizViewModel = viewModel(
                key = topicId,
                factory = viewModelFactory {
                    initializer { QuizViewModel(topicId, container.contentRepository, container.progressRepository) }
                },
            )
            QuizScreen(
                viewModel = viewModel,
                onDone = { navController.popBackStack(ROUTE_ROADMAP, inclusive = false) },
            )
        }
    }
}
