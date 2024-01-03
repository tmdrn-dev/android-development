package com.example.mediaplayer.domain

import androidx.media3.common.MediaItem
import com.example.mediaplayer.data.repository.foo.MusicRepository
import com.example.mediaplayer.service.ControllerState
import com.example.mediaplayer.service.MusicController
import com.example.mediaplayer.service.PlayerEvent
import com.example.mediaplayer.service.SimpleMediaState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMediaItemStateUseCase @Inject constructor(
    private val mediaController: MusicController,
) {
    operator fun invoke(): StateFlow<SimpleMediaState> {
        return mediaController.simpleMediaState
    }
}

