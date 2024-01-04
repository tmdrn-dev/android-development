package com.example.mediaplayer.service

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MusicController @Inject constructor(
    @ApplicationContext context: Context,
) {
    private val _controllerState = MutableStateFlow<ControllerState>(
        ControllerState.Initial
    )
    val controllerState = _controllerState.asStateFlow()

    private val _simpleMediaState = MutableStateFlow<SimpleMediaState>(
        SimpleMediaState.Initial
    )
    val simpleMediaState = _simpleMediaState.asStateFlow()

    private val sessionToken = SessionToken(
        context, ComponentName(context, MusicService::class.java)
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
    }

    fun seekMediaItem(mediaItemIndex: Int) {
        if (mediaItemIndex < 0 || mediaItemIndex >= controller.mediaItemCount) {
            return
        }
        controller.seekToDefaultPosition(mediaItemIndex)
        controller.prepare()
    }

    suspend fun onPlayerEvent(playerEvent: PlayerEvent) {
        when (playerEvent) {
            PlayerEvent.Backward -> controller.seekBack()
            PlayerEvent.Forward -> controller.seekForward()
            PlayerEvent.PlayPause -> {
                if (controller.isPlaying) {
                    controller.pause()
                    stopProgressUpdate()
                } else {
                    controller.play()
                    _simpleMediaState.value = SimpleMediaState.Playing(
                        controller.currentMediaItemIndex
                    )
                    startProgressUpdate()
                }
            }
            PlayerEvent.Stop -> stopProgressUpdate()
            is PlayerEvent.UpdateProgress -> controller.seekTo(
                (controller.duration * playerEvent.newProgress).toLong()
            )

            else -> {}
        }
    }

    private fun initController() {
        controller.addListener(object : Player.Listener {

            @SuppressLint("SwitchIntDef")
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    ExoPlayer.STATE_BUFFERING -> _simpleMediaState.value =
                        SimpleMediaState.Buffering(controller.currentPosition)
                    ExoPlayer.STATE_READY -> _simpleMediaState.value =
                        SimpleMediaState.Ready(controller.duration)
                }
            }

            @OptIn(DelicateCoroutinesApi::class)
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) {
                    _simpleMediaState.value = SimpleMediaState.Playing(
                        controller.currentMediaItemIndex
                    )
                    GlobalScope.launch(Dispatchers.Main) {
                        startProgressUpdate()
                    }
                } else {
                    stopProgressUpdate()
                }
            }
        })
    }

    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(500)
            _simpleMediaState.value = SimpleMediaState.Progress(
                controller.currentPosition
            )
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _simpleMediaState.value = SimpleMediaState.Stop
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

sealed interface SimpleMediaState {
    data object Initial : SimpleMediaState
    data class Ready(val duration: Long) : SimpleMediaState
    data class Progress(val progress: Long) : SimpleMediaState
    data class Buffering(val progress: Long) : SimpleMediaState
    data class Playing(
        val currentMediaItemIndex: Int
    ) : SimpleMediaState

    data object Stop : SimpleMediaState
}