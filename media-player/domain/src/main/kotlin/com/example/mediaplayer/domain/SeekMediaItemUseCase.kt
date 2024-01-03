package com.example.mediaplayer.domain

import androidx.media3.common.MediaItem
import com.example.mediaplayer.service.MusicController
import javax.inject.Inject

class SeekMediaItemUseCase @Inject constructor(
    private val mediaController: MusicController,
    private val loadMediaItemsUseCase: LoadMediaItemsUseCase
) {
    suspend operator fun invoke(mediaItem: MediaItem) {
        loadMediaItemsUseCase().collect { mediaLoadingState ->
            when (mediaLoadingState) {
                is MediaLoadingState.Ready -> {
                    val index = mediaLoadingState.mediaItems.indexOf(mediaItem)
                    if (index > -1) {
                        mediaController.seekMediaItem(index)
                    } else {
                        mediaController.addMediaItem(mediaItem)
                    }
                }
                else -> {
                }
            }
        }
    }
}


