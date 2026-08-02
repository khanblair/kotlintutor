package com.khanblair.kotlintutor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.khanblair.kotlintutor.ui.navigation.KotlinTutorNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = (application as KotlinTutorApplication).container
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    KotlinTutorNavHost(container = container)
                }
            }
        }
    }
}
