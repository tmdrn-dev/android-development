package com.example.mediaplayer.app.medialist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import coil.compose.AsyncImage

@Composable
fun MediaListScreen (
    startService: () -> Unit,
    viewModel: MediaItemViewModel = hiltViewModel(),
) {
    val mediaItems by viewModel.mediaItems.collectAsStateWithLifecycle()
    val currentItem by viewModel.currentItem.collectAsStateWithLifecycle()
    val state = viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (state.value) {
            is MediaItemViewModel.UiState.Ready -> {
                LaunchedEffect(true) { // This is only call first time
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

                    MiniPlayerBar(
                        mediaItem = currentItem,
                        onClick = {
                            viewModel.onUiEvent(it)
                        }
                    )
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

@Composable
fun MiniPlayerBar(
    mediaItem: MediaItem?,
    onClick: (MediaItemViewModel.UiEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 이전곡 버튼
        IconButton(
            onClick = {
                onClick(MediaItemViewModel.UiEvent.Backward)
            }
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "이전곡")
        }

        // 재생/일시 정지 버튼
        IconButton(
            onClick = {
                onClick(MediaItemViewModel.UiEvent.PlayPause)
            }
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = "재생/일시 정지")
        }

        // 다음곡 버튼
        IconButton(
            onClick = {
                onClick(MediaItemViewModel.UiEvent.Forward)
            }
        ) {
            Icon(Icons.Default.ArrowForward, contentDescription = "다음곡")
        }

        MediaItem(mediaItem)
    }
}

@Composable
fun MediaItem(
    mediaItem: MediaItem?,
    isPlaying: Boolean = true,
) {
    AsyncImage(
        model = mediaItem?.mediaMetadata?.artworkUri,
        contentDescription = "Thumbnail",
        modifier = Modifier.size(48.dp))
    Column(
        modifier = Modifier.padding(start = 8.dp)
    ) {
        Text(
            text = mediaItem?.mediaMetadata?.title.toString(),
            fontWeight =
            if(isPlaying) FontWeight.Bold
            else FontWeight.Normal
        )
        Text(
            text = mediaItem?.mediaMetadata?.artist.toString(),
        )
    }
}
