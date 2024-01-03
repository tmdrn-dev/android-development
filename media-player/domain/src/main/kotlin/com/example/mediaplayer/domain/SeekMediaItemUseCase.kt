package com.example.mediaplayer.domain

import androidx.media3.common.MediaItem
import com.example.mediaplayer.service.MusicController
import com.example.mediaplayer.service.PlayerEvent
import javax.inject.Inject

class SeekMediaItemUseCase @Inject constructor(
    private val mediaController: MusicController,
    private val loadMediaItemsUseCase: LoadMediaItemsUseCase
) {
    suspend operator fun invoke(mediaItem: MediaItem?, playerEvent: PlayerEvent) {
        loadMediaItemsUseCase().collect { mediaLoadingState ->
            when (mediaLoadingState) {
                is MediaLoadingState.Ready -> {
                    var index = mediaLoadingState.mediaItems.indexOf(mediaItem)
                    when(playerEvent) {
                        PlayerEvent.Forward -> {
                            index += 1
                            if (index >= mediaLoadingState.mediaItems.size) {
                                index = 0
                            }
                        }
                        PlayerEvent.Backward -> {
                            index -= 1
                            if (index < 0) {
                                index = mediaLoadingState.mediaItems.size-1
                            }
                        }
                        else -> {}
                    }

                    mediaController.seekMediaItem(index)
                }
                else -> {}
            }
        }
    }
}


