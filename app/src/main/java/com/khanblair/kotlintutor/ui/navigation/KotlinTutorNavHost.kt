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
import com.khanblair.kotlintutor.ui.settings.SettingsScreen
import com.khanblair.kotlintutor.ui.settings.SettingsViewModel
import com.khanblair.kotlintutor.ui.tutor.TutorScreen
import com.khanblair.kotlintutor.ui.tutor.TutorViewModel

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
                onLessonClick = { topicId -> navController.navigate(lessonRoute(topicId)) },
                onSettingsClick = { navController.navigate(ROUTE_SETTINGS) },
            )
        }
        composable(ROUTE_LESSON) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId").orEmpty()
            val viewModel: LessonViewModel = viewModel(
                key = topicId,
                factory = viewModelFactory {
                    initializer { LessonViewModel(topicId, container.curriculumRepository, container.progressRepository) }
                },
            )
            LessonScreen(
                viewModel = viewModel,
                onTakeQuiz = { navController.navigate(quizRoute(topicId)) },
                onAskTutor = { navController.navigate(tutorRoute(topicId)) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(ROUTE_QUIZ) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId").orEmpty()
            val viewModel: QuizViewModel = viewModel(
                key = topicId,
                factory = viewModelFactory {
                    initializer { QuizViewModel(topicId, container.curriculumRepository, container.progressRepository) }
                },
            )
            QuizScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onDone = { navController.popBackStack(ROUTE_ROADMAP, inclusive = false) },
            )
        }
        composable(ROUTE_TUTOR) { backStackEntry ->
            val topicId = backStackEntry.arguments?.getString("topicId").orEmpty()
            val viewModel: TutorViewModel = viewModel(
                key = topicId,
                factory = viewModelFactory {
                    initializer { TutorViewModel(topicId, container.curriculumRepository, container.tutorRepository) }
                },
            )
            TutorScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
            )
        }
        composable(ROUTE_SETTINGS) {
            val viewModel: SettingsViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { SettingsViewModel(container.apiKeyStore, container.themePreferences) }
                },
            )
            SettingsScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}
