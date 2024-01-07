package com.example.mediaplayer.app.medialist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import com.example.mediaplayer.common.controller.MediaState
import com.example.mediaplayer.common.ui.MediaItem

@Composable
fun MediaListScreen (
    startService: () -> Unit,
    viewModel: MediaItemViewModel = hiltViewModel(),
) {
    val mediaItems by viewModel.mediaItems.collectAsStateWithLifecycle()
    val currentItem by viewModel.currentItem.collectAsStateWithLifecycle()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (uiState.value) {
            is UiState.Ready -> {
                LaunchedEffect(true) {
                    startService()
                }

                Column {
                    LazyColumn {
                        items(mediaItems) { item ->
                            MediaItemRow(
                                mediaItem = item,
                                isPlaying = currentItem == item,
                                onClick = {
                                    viewModel.seekMediaItem(it)
                                }
                            )
                        }
                    }
                }
            }

            else -> {}
        }
    }
}

@Composable
fun MediaItemRow(
    mediaItem: MediaItem,
    isPlaying: Boolean,
    onClick: (MediaItem) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = {
                onClick(mediaItem)
            })
            .fillMaxWidth()
            .padding(start = 8.dp)
    ) {
        MediaItem(mediaItem, isPlaying)
    }
}
