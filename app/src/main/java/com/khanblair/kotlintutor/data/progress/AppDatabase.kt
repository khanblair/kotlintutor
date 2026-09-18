package com.khanblair.kotlintutor.data.progress

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProgressEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun progressDao(): ProgressDao
}
