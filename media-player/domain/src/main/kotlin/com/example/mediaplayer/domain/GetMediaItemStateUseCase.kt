package com.example.mediaplayer.domain

import com.example.mediaplayer.common.controller.MediaPlayerController
import com.example.mediaplayer.common.controller.MediaState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetMediaItemStateUseCase @Inject constructor(
    private val mediaController: com.example.mediaplayer.common.controller.MediaPlayerController,
) {
    operator fun invoke(): StateFlow<com.example.mediaplayer.common.controller.MediaState> {
        return mediaController.mediaState
    }
}

