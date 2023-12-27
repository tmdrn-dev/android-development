package com.example.mediaplayer.app

import android.content.Intent
import com.example.mediaplayer.app.ui.MediaPlayerApp
import com.example.mediaplayer.common.ui.theme.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mediaplayer.service.MusicService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MediaPlayerApp(startService = ::startService)
                }
            }
        }
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, MusicService::class.java)
            startForegroundService(intent)
            isServiceRunning = true
        }
    }
}
