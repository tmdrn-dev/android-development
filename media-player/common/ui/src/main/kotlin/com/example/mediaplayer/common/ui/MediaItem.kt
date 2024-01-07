package com.example.mediaplayer.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import coil.compose.AsyncImage

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
            fontWeight = when(isPlaying) {
                true -> FontWeight.Bold
                else -> FontWeight.Normal
            }
        )
        Text(
            text = mediaItem?.mediaMetadata?.artist.toString(),
        )
    }
}