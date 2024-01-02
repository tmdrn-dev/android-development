package com.example.mediaplayer.app.medialist

import android.net.Uri
import com.example.mediaplayer.domain.GetMusicSourceUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MediaMetadata.MEDIA_TYPE_FOLDER_ALBUMS
import com.example.mediaplayer.data.model.MediaData
import com.example.mediaplayer.data.repository.MediaRepository
import com.example.mediaplayer.feature.medialist.R
import com.example.mediaplayer.service.ControllerState
import com.example.mediaplayer.service.MusicController
import com.example.mediaplayer.service.MusicServiceHandler
import com.example.mediaplayer.service.PlayerEvent
import com.example.mediaplayer.service.SimpleMediaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaItemViewModel @Inject constructor(
//    private val musicServiceHandler: MusicServiceHandler,
    private val musicServiceHandler:MusicController,
    private val getMusicSourceUseCase: GetMusicSourceUseCase,
) : ViewModel() {
    private var _mediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val mediaItems: StateFlow<List<MediaItem>> = _mediaItems.asStateFlow()

    private val _currentItem = MutableStateFlow<MediaItem?>(null)
    val currentItem: StateFlow<MediaItem?> = _currentItem.asStateFlow()

    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            loadMediaItems()
            _uiState.value = UIState.Ready

            musicServiceHandler.simpleMediaState.collect { mediaState ->
                when (mediaState) {
//                    is SimpleMediaState.Buffering -> calculateProgressValues(mediaState.progress)
//                    SimpleMediaState.Initial -> _uiState.value = UIState.Initial
                    is SimpleMediaState.Playing -> currentItem(mediaState.isPlaying)
                    else -> {}
//                    is SimpleMediaState.Progress -> calculateProgressValues(mediaState.progress)
//                    is SimpleMediaState.Ready -> {
//                        duration = mediaState.duration
//                        _uiState.value = UIState.Ready
                    }
                }
            }

//            musicServiceHandler.onPlayerEvent(PlayerEvent.PlayPause)
        }

    private fun loadMediaItems() {
        viewModelScope.launch {
            getMusicSourceUseCase().collect{
                _mediaItems.value = it
                musicServiceHandler.controllerState.collect { controllerState ->
                    when (controllerState) {
                        is ControllerState.Ready -> {musicServiceHandler.addMediaItemList(it)}
                        else -> {}
                    }
                }
            }
        }
    }

    fun selectMusic(mediaItem: MediaItem) {
        val index = mediaItems.value.indexOf(mediaItem)
        if (index > -1) {
            musicServiceHandler.seekMediaItem(index)
        } else {
            musicServiceHandler.addMediaItem(mediaItem)
        }
        viewModelScope.launch {
            musicServiceHandler.onPlayerEvent(PlayerEvent.PlayPause)
        }
    }

    private fun currentItem(isPlaying: Boolean) {
        if (isPlaying) {
            val index = musicServiceHandler.getCurrentMediaItemIndex()
            _currentItem.value = mediaItems.value[index]
            println("[SK] currentItem = ${currentItem.value?.mediaMetadata?.title.toString()}")
        }
    }

    sealed class UIState {
        data object Initial : UIState()
        data object Ready : UIState()
    }
}
