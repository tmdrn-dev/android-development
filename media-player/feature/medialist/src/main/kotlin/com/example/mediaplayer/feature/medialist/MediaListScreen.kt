package com.example.mediaplayer.feature.medialist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MediaListScreen(
    viewModel: MediaListViewModel = hiltViewModel(),
) {
    val viewModelData by viewModel.data.collectAsStateWithLifecycle()
    Text(
        text = "MediaItemList",
        modifier = Modifier
    )
}
