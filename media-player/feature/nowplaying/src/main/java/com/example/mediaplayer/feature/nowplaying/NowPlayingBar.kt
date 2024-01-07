package com.example.mediaplayer.feature.nowplaying

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mediaplayer.common.controller.MediaState
import com.example.mediaplayer.common.ui.MediaItem

@Composable
fun NowPlayingBar(
    viewModel: NowPlayingBarViewModel = hiltViewModel(),
) {
    val currentItem by viewModel.currentItem.collectAsStateWithLifecycle()
    val mediaState by viewModel.mediaState.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val isPlaying = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (uiState.value) {
            is UiState.Ready -> {
                IconButton(
                    onClick = { viewModel.onUiEvent(UiEvent.Backward) }
                ) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "previous")
                }

                IconButton(
                    onClick = { viewModel.onUiEvent(UiEvent.PlayPause) }
                ) {
                    Icon(
                        imageVector = when (mediaState) {
                            is MediaState.Playing -> {
                                when ((mediaState as MediaState.Playing).isPlaying) {
                                    true -> {
                                        isPlaying.value = true
                                        Icons.Default.Close
                                    }

                                    false -> {
                                        isPlaying.value = false
                                        Icons.Default.PlayArrow
                                    }
                                }

                            }

                            else -> {
                                if (isPlaying.value) Icons.Default.Close
                                else Icons.Default.PlayArrow
                            }
                        },
                        contentDescription = "play/pause"
                    )
                }

                IconButton(
                    onClick = { viewModel.onUiEvent(UiEvent.Forward) }
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "next")
                }

                MediaItem(currentItem)
            }

            else -> {}
        }
    }
}


