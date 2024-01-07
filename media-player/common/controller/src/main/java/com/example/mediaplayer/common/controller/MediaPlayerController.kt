package com.example.mediaplayer.common.controller

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.mediaplayer.common.service.MediaPlayerService
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MediaPlayerController @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val _controllerState = MutableStateFlow<ControllerState>(ControllerState.Initial)
    val controllerState = _controllerState.asStateFlow()

    private val _mediaState = MutableStateFlow<MediaState>(MediaState.Initial)
    val mediaState = _mediaState.asStateFlow()

    private val _preparedMediaItem = MutableStateFlow<MediaItem?>(null)
    val preparedMediaItem = _preparedMediaItem.asStateFlow()

    private val sessionToken = SessionToken(
        context, ComponentName(context, MediaPlayerService::class.java)
    )
    private var controllerFuture: ListenableFuture<MediaController>
    private lateinit var controller: MediaController

    private var job: Job? = null

    init {
        controllerFuture = MediaController.Builder(context, sessionToken)
            .buildAsync()
        controllerFuture.addListener(
            {
                controller = controllerFuture.get()
                _controllerState.value = ControllerState.Ready
                initController()
                job = Job()
            },
            // https://developer.android.com/guide/topics/media/session/mediacontroller#create-controller
            MoreExecutors.directExecutor()
        )
    }

    fun addMediaItemList(mediaItemList: List<MediaItem>) {
        controller.setMediaItems(mediaItemList)
        controller.prepare()
        _preparedMediaItem.value = controller.getMediaItemAt(0)
    }

    fun seekMediaItem(index: Int) {
        if (index < 0 || index >= controller.mediaItemCount) {
            return
        }
        controller.seekToDefaultPosition(index)
        controller.prepare()
        _preparedMediaItem.value = controller.getMediaItemAt(index)
    }

    suspend fun onPlayerEvent(playerEvent: PlayerEvent) {
        when (playerEvent) {
            PlayerEvent.Backward -> controller.seekBack()
            PlayerEvent.Forward -> controller.seekForward()
            PlayerEvent.PlayPause -> {
                when(controller.isPlaying) {
                    true -> {
                        controller.pause()
                        _mediaState.value = MediaState.Playing(false)
                    }
                    false -> {
                        controller.play()
                        _mediaState.value = MediaState.Playing(true)
                        _preparedMediaItem.value = controller.currentMediaItem
                    }
                }
            }
            PlayerEvent.Stop -> {
                controller.stop()
                _mediaState.value = MediaState.Playing(false)
            }
            else -> {}
        }
    }

    private fun initController() {
        controller.addListener(
            object : Player.Listener {
                @SuppressLint("SwitchIntDef")
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        ExoPlayer.STATE_BUFFERING -> _mediaState.value =
                            MediaState.Buffering(controller.currentPosition)
                        ExoPlayer.STATE_READY -> _mediaState.value =
                            MediaState.Ready(controller.duration)
                    }
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    if (isPlaying) {
                        _mediaState.value = MediaState.Playing(true)
                        _preparedMediaItem.value = controller.currentMediaItem
                    } else {
                        _mediaState.value = MediaState.Playing(false)
                    }
                }
            }
        )
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(500)
            _mediaState.value = MediaState.Progress(
                controller.currentPosition
            )
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _mediaState.value = MediaState.Playing(false)
    }
}

sealed class ControllerState {
    data object Initial : ControllerState()
    data object Ready : ControllerState()
}

sealed class PlayerEvent {
    object Idle : PlayerEvent()
    object PlayPause : PlayerEvent()
    object Backward : PlayerEvent()
    object Forward : PlayerEvent()
    object Stop : PlayerEvent()
    data class UpdateProgress(val newProgress: Float) : PlayerEvent()
}

sealed interface MediaState {
    data object Initial : MediaState
    data class Ready(val duration: Long) : MediaState
    data class Progress(val progress: Long) : MediaState
    data class Buffering(val progress: Long) : MediaState
    data class Playing(val isPlaying: Boolean): MediaState
}