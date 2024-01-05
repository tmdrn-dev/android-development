package com.example.mediaplayer.domain

import androidx.media3.common.MediaItem
import com.example.mediaplayer.data.repository.foo.MusicRepository
import com.example.mediaplayer.common.controller.ControllerState
import com.example.mediaplayer.common.controller.MediaPlayerController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadMediaItemsUseCase @Inject constructor(
    private val mediaRepository: MusicRepository,
    private val mediaController: com.example.mediaplayer.common.controller.MediaPlayerController,
) {
    suspend operator fun invoke(): Flow<MediaLoadingState> = flow {
        mediaRepository.mediaItemList.collect { mediaItemList ->
            mediaController.controllerState.collect { controllerState ->
                when (controllerState) {
                    is com.example.mediaplayer.common.controller.ControllerState.Ready -> {
                        mediaController.addMediaItemList(mediaItemList)
                        emit(MediaLoadingState.Ready(mediaItemList))
                    }
                    else -> {
                        emit(MediaLoadingState.Loading)
                    }
                }
            }
        }
    }
}

sealed interface MediaLoadingState {
    data object Loading : MediaLoadingState
    data class Ready(
        val mediaItems: List<MediaItem>
    ) : MediaLoadingState
}