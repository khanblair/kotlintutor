package com.khanblair.kotlintutor

import android.app.Application
import com.khanblair.kotlintutor.di.AppContainer

class KotlinTutorApplication : Application() {
    val container: AppContainer by lazy { AppContainer(this) }
}
