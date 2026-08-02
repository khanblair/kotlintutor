package com.khanblair.kotlintutor.di

import android.content.Context
import androidx.room.Room
import com.khanblair.kotlintutor.data.content.ContentRepository
import com.khanblair.kotlintutor.data.content.DefaultContentRepository
import com.khanblair.kotlintutor.data.progress.AppDatabase
import com.khanblair.kotlintutor.data.progress.ProgressRepository
import com.khanblair.kotlintutor.data.progress.RoomProgressRepository
import com.khanblair.kotlintutor.data.roadmap.DefaultRoadmapRepository
import com.khanblair.kotlintutor.data.roadmap.RoadmapRepository

/** Hand-rolled dependency container. No DI framework needed at this app's size. */
class AppContainer(context: Context) {
    private val database = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "kotlintutor.db",
    ).build()

    val progressRepository: ProgressRepository = RoomProgressRepository(database.progressDao())
    val roadmapRepository: RoadmapRepository = DefaultRoadmapRepository(progressRepository)
    val contentRepository: ContentRepository = DefaultContentRepository()
}
