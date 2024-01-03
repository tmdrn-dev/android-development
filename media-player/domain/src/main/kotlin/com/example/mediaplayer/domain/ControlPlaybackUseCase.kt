package com.example.mediaplayer.domain

import androidx.media3.common.MediaItem
import com.example.mediaplayer.data.repository.foo.MusicRepository
import com.example.mediaplayer.service.ControllerState
import com.example.mediaplayer.service.MusicController
import com.example.mediaplayer.service.PlayerEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ControlPlaybackUseCase @Inject constructor(
    private val mediaController: MusicController,
) {
    suspend operator fun invoke(playerEvent: PlayerEvent) {
        when (playerEvent) {
            PlayerEvent.Backward -> mediaController.onPlayerEvent(PlayerEvent.Backward)
            PlayerEvent.Forward -> mediaController.onPlayerEvent(PlayerEvent.Forward)
            PlayerEvent.PlayPause -> {
                mediaController.onPlayerEvent(PlayerEvent.PlayPause)

//                if (player.isPlaying) {
//                    player.pause()
////                    stopProgressUpdate()
//                } else {
//                    player.play()
//                    _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = true)
////                    startProgressUpdate()
//                }
            }
//            PlayerEvent.Stop -> stopProgressUpdate()
//            is PlayerEvent.UpdateProgress -> player.seekTo((player.duration * playerEvent.newProgress).toLong())
            else -> {}
        }
    }
}

