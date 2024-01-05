package com.example.mediaplayer.domain

import androidx.media3.common.MediaItem
import com.example.mediaplayer.common.controller.MediaPlayerController
import com.example.mediaplayer.common.controller.PlayerEvent
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SeekMediaItemUseCase @Inject constructor(
    private val mediaController: com.example.mediaplayer.common.controller.MediaPlayerController,
    private val loadMediaItemsUseCase: LoadMediaItemsUseCase
) {
    suspend operator fun invoke(
        mediaItem: MediaItem?,
        playerEvent: com.example.mediaplayer.common.controller.PlayerEvent
    ): MediaItem? {
        val mediaLoadingState = loadMediaItemsUseCase().firstOrNull {
                it is MediaLoadingState.Ready
            } as? MediaLoadingState.Ready

        mediaLoadingState?.let { state ->
            var index = state.mediaItems.indexOf(mediaItem)
            when (playerEvent) {
                com.example.mediaplayer.common.controller.PlayerEvent.Forward -> {
                    index += 1
                    if (index >= state.mediaItems.size) {
                        index = 0
                    }
                }
                com.example.mediaplayer.common.controller.PlayerEvent.Backward -> {
                    index -= 1
                    if (index < 0) {
                        index = state.mediaItems.size - 1
                    }
                }
                else -> {}
            }

            val seekMediaItem = state.mediaItems.getOrNull(index)
            seekMediaItem?.let {
                mediaController.seekMediaItem(index)
            }

            return seekMediaItem
        }

        return null
    }
}


