package com.example.mediaplayer.feature.medialist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mediaplayer.data.model.MediaData
import coil.compose.rememberAsyncImagePainter

@Composable
fun MediaListScreen(
    viewModel: MediaItemViewModel = hiltViewModel(),
) {
    val mediaItems by viewModel.mediaItems.collectAsStateWithLifecycle()

    LazyColumn {
        items(mediaItems) { item ->
            println(item.image)
            MediaItemRow(item)
        }
    }
}

@Composable
fun MediaItemRow(mediaItem: MediaData) {
    val painter = rememberAsyncImagePainter(mediaItem.image)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start=8.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = "Thumbnail",
            modifier = Modifier.size(48.dp)
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = mediaItem.title,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = mediaItem.artist,
            )
        }
    }
}
