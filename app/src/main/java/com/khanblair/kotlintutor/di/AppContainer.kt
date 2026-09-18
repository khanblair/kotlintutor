package com.khanblair.kotlintutor.di

import android.content.Context
import androidx.room.Room
import com.khanblair.kotlintutor.data.curriculum.CurriculumRepository
import com.khanblair.kotlintutor.data.curriculum.DefaultCurriculumRepository
import com.khanblair.kotlintutor.data.progress.AppDatabase
import com.khanblair.kotlintutor.data.progress.ProgressRepository
import com.khanblair.kotlintutor.data.progress.RoomProgressRepository
import com.khanblair.kotlintutor.data.roadmap.DefaultRoadmapRepository
import com.khanblair.kotlintutor.data.roadmap.RoadmapRepository
import com.khanblair.kotlintutor.data.settings.SharedPrefsThemePreferences
import com.khanblair.kotlintutor.data.settings.ThemePreferences
import com.khanblair.kotlintutor.data.tutor.ApiKeyStore
import com.khanblair.kotlintutor.data.tutor.DefaultTutorRepository
import com.khanblair.kotlintutor.data.tutor.EncryptedApiKeyStore
import com.khanblair.kotlintutor.data.tutor.KtorDeepSeekApi
import com.khanblair.kotlintutor.data.tutor.TutorRepository
import com.khanblair.kotlintutor.data.tutor.createDeepSeekHttpClient

/** Hand-rolled dependency container. No DI framework needed at this app's size. */
class AppContainer(context: Context) {
    private val database = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "kotlintutor.db",
    )
        // No schema migrations exist yet (schema v1, exportSchema enabled in
        // AppDatabase so future migrations can be authored against history).
        // Until real migrations are written, an incompatible schema change
        // clears local progress instead of crashing on startup.
        .fallbackToDestructiveMigration(dropAllTables = true)
        .build()

    val progressRepository: ProgressRepository = RoomProgressRepository(database.progressDao())
    val roadmapRepository: RoadmapRepository = DefaultRoadmapRepository(progressRepository)
    val curriculumRepository: CurriculumRepository = DefaultCurriculumRepository()

    val apiKeyStore: ApiKeyStore = EncryptedApiKeyStore(context.applicationContext)
    val tutorRepository: TutorRepository = DefaultTutorRepository(KtorDeepSeekApi(createDeepSeekHttpClient(), apiKeyStore))

    val themePreferences: ThemePreferences = SharedPrefsThemePreferences(context.applicationContext)
}
