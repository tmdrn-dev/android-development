package com.example.mediaplayer.feature.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.example.mediaplayer.common.controller.MediaPlayerController
import com.example.mediaplayer.common.controller.MediaState
import com.example.mediaplayer.common.controller.PlayerEvent
import com.example.mediaplayer.domain.LoadMediaItemsUseCase
import com.example.mediaplayer.domain.MediaLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingBarViewModel @Inject constructor(
    private val loadMediaItemsUseCase: LoadMediaItemsUseCase,
    private val mediaPlayerController: MediaPlayerController,
) : ViewModel() {
    private var _currentItem = MutableStateFlow<MediaItem?>(null)
    val currentItem: StateFlow<MediaItem?> = _currentItem.asStateFlow()

    private val _mediaState = MutableStateFlow<MediaState>(MediaState.Initial)
    val mediaState = _mediaState.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    private var mediaItems: List<MediaItem> = emptyList()

    init {
        viewModelScope.launch {
            loadMediaItemsUseCase().collect { mediaLoadingState ->
                when (mediaLoadingState) {
                    is MediaLoadingState.Ready -> {
                        mediaItems = mediaLoadingState.mediaItems
                        _uiState.value = UiState.Ready
                    }
                    else -> _uiState.value = UiState.Initial
                }
            }
        }

        viewModelScope.launch {
            mediaPlayerController.mediaState.collect {
                _mediaState.value = it
            }
        }

        viewModelScope.launch {
            mediaPlayerController.preparedMediaItem.collect {
                _currentItem.value = it
            }
        }
    }

    fun onUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            var index = mediaItems.indexOf(_currentItem.value)
            val size = mediaItems.size
            when (uiEvent) {
                UiEvent.Backward -> {
                    index -= 1
                    if (index < 0) {
                            index = size - 1
                    }
                    mediaPlayerController.seekMediaItem(index % size)
                }

                UiEvent.Forward -> {
                    index += 1
                    if (index >= size) {
                        index = 0
                    }
                    mediaPlayerController.seekMediaItem(index)
                }

                UiEvent.PlayPause -> mediaPlayerController.onPlayerEvent(PlayerEvent.PlayPause)
            }
        }
    }
}

sealed class UiState {
    data object Initial : UiState()
    data object Ready : UiState()
}

sealed class UiEvent {
    data object PlayPause : UiEvent()
    data object Backward : UiEvent()
    data object Forward : UiEvent()
}
