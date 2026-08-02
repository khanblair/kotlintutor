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
    ).build()

    val progressRepository: ProgressRepository = RoomProgressRepository(database.progressDao())
    val roadmapRepository: RoadmapRepository = DefaultRoadmapRepository(progressRepository)
    val curriculumRepository: CurriculumRepository = DefaultCurriculumRepository()

    val apiKeyStore: ApiKeyStore = EncryptedApiKeyStore(context.applicationContext)
    val tutorRepository: TutorRepository = DefaultTutorRepository(KtorDeepSeekApi(createDeepSeekHttpClient(), apiKeyStore))
}
