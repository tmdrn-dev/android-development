package com.example.datastorewithhilt

import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TimerScreen(
    viewModel: TimerViewModel = hiltViewModel()
) {
    val time = viewModel.timerTime.collectAsStateWithLifecycle()

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            Text(text = "Time remaining: ${time.value}")

            Row(
                modifier = Modifier
            ) {
                Button(
                    onClick = {
                        viewModel.startTimer(60)
                    }
                ) {
                    Text("Start(60s)")
                }

                Button(onClick = { viewModel.pauseTimer() }) {
                    Text("Pause")
                }

                Button(onClick = { viewModel.resumeTimer() }) {
                    Text("Resume")
                }

                Button(onClick = { viewModel.stopTimer() }) {
                    Text("Stop")
                }
            }
        }
    }
}
