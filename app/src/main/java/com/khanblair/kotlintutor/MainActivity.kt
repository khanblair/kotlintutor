package com.khanblair.kotlintutor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.khanblair.kotlintutor.ui.navigation.KotlinTutorApp
import com.khanblair.kotlintutor.ui.theme.KotlinTutorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = (application as KotlinTutorApplication).container
        setContent {
            val themeMode by container.themePreferences.themeMode.collectAsState()
            KotlinTutorTheme(themeMode = themeMode) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    KotlinTutorApp(container = container)
                }
            }
        }
    }
}
