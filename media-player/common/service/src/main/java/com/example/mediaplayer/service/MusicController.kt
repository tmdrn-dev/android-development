package com.example.mediaplayer.service

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaController
import androidx.media3.session.MediaSession
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
    private val sessionToken = SessionToken(context, ComponentName(context, MusicService::class.java))
    private var controllerFuture: ListenableFuture<MediaController>
    private lateinit var controller: MediaController

    private var job: Job? = null

    private val _controllerState = MutableStateFlow<ControllerState>(ControllerState.Initial)
    val controllerState = _controllerState.asStateFlow()

    init {
        controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener(
            {
                controller = controllerFuture.get()
                _controllerState.value = ControllerState.Ready
                initController()
                job = Job()
            },
            // 참고: UI가 아닌 다른 프로세스에서 서비스를 실행하는 경우 MoreExecutors.directExecutor() 대신 ContextCompat.getMainExecutor()를 사용하세요.
            MoreExecutors.directExecutor()
            //ContextCompat.getMainExecutor(context)
        )
    }

    private val _simpleMediaState = MutableStateFlow<SimpleMediaState>(SimpleMediaState.Initial)
    val simpleMediaState = _simpleMediaState.asStateFlow()


    fun addMediaItem(mediaItem: MediaItem) {
        controller.setMediaItem(mediaItem)
        controller.prepare()
    }

    fun addMediaItemList(mediaItemList: List<MediaItem>) {
        controller.setMediaItems(mediaItemList)
        controller.prepare()
    }

    fun seekMediaItem(mediaItemIndex: Int) {
        if (mediaItemIndex < 0 || mediaItemIndex >= controller.mediaItemCount) {
            // 인덱스가 유효 범위를 벗어났을 경우 처리
            return
        }
        controller.seekToDefaultPosition(mediaItemIndex)
        controller.prepare()
    }

    fun getCurrentMediaItemIndex(): Int {
        return controller.currentMediaItemIndex
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
                    _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = true)
                    startProgressUpdate()
                }
            }
            PlayerEvent.Stop -> stopProgressUpdate()
            is PlayerEvent.UpdateProgress -> controller.seekTo((controller.duration * playerEvent.newProgress).toLong())
        }
    }

    private fun initController() {
        //controller.playWhenReady = true
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
                _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = isPlaying)
                if (isPlaying) {
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
            _simpleMediaState.value = SimpleMediaState.Progress(controller.currentPosition)
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()
        _simpleMediaState.value = SimpleMediaState.Playing(isPlaying = false)
    }
}

sealed class ControllerState {
    data object Initial : ControllerState()
    data object Ready : ControllerState()
}
