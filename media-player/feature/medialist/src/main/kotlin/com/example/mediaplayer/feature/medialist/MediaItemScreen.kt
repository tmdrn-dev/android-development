package com.example.mediaplayer.feature.medialist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mediaplayer.data.model.MediaData

@Composable
fun MediaListScreen(
    viewModel: MediaItemViewModel = hiltViewModel(),
) {
    val mediaItems by viewModel.mediaItems.collectAsStateWithLifecycle()

    LazyColumn {
        items(mediaItems) { item ->
            MediaItemRow(item)
        }
    }
}

@Composable
fun MediaItemRow(mediaItem: MediaData) {
    Text(text = mediaItem.title)
}