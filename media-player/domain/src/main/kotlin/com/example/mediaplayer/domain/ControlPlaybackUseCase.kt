package com.example.mediaplayer.domain

import com.example.mediaplayer.common.controller.MediaPlayerController
import com.example.mediaplayer.common.controller.PlayerEvent
import javax.inject.Inject

class ControlPlaybackUseCase @Inject constructor(
    private val mediaController: com.example.mediaplayer.common.controller.MediaPlayerController,
) {
    suspend operator fun invoke(playerEvent: com.example.mediaplayer.common.controller.PlayerEvent) {
        when (playerEvent) {
            com.example.mediaplayer.common.controller.PlayerEvent.Backward -> mediaController.onPlayerEvent(
                com.example.mediaplayer.common.controller.PlayerEvent.Backward)
            com.example.mediaplayer.common.controller.PlayerEvent.Forward -> mediaController.onPlayerEvent(
                com.example.mediaplayer.common.controller.PlayerEvent.Forward)
            com.example.mediaplayer.common.controller.PlayerEvent.PlayPause -> mediaController.onPlayerEvent(
                com.example.mediaplayer.common.controller.PlayerEvent.PlayPause)
//            PlayerEvent.Stop -> stopProgressUpdate()
//            is PlayerEvent.UpdateProgress -> player.seekTo((player.duration * playerEvent.newProgress).toLong())
            else -> {}
        }
    }
}

