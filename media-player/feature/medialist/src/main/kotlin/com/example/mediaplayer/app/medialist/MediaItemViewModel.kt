package com.example.mediaplayer.app.medialist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.example.mediaplayer.common.controller.MediaPlayerController
import com.example.mediaplayer.domain.LoadMediaItemsUseCase
import com.example.mediaplayer.domain.MediaLoadingState
import com.example.mediaplayer.common.controller.PlayerEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaItemViewModel @Inject constructor(
    private val loadMediaItemsUseCase: LoadMediaItemsUseCase,
    private val mediaPlayerController: MediaPlayerController,
) : ViewModel() {
    private var _mediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val mediaItems: StateFlow<List<MediaItem>> = _mediaItems.asStateFlow()

    private var _currentItem = MutableStateFlow<MediaItem?>(null)
    val currentItem: StateFlow<MediaItem?> = _currentItem.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadMediaItemsUseCase().collect { mediaLoadingState ->
                when (mediaLoadingState) {
                    is MediaLoadingState.Ready -> {
                        _mediaItems.value = mediaLoadingState.mediaItems
                        if (_mediaItems.value.isNotEmpty())
                            _uiState.value = UiState.Ready
                    }
                    else -> {
                        _uiState.value = UiState.Initial
                    }
                }
            }
        }

        viewModelScope.launch {
            mediaPlayerController.preparedMediaItem.collect {
                _currentItem.value = it
            }
        }
    }

    fun seekMediaItem(mediaItem: MediaItem) {
        viewModelScope.launch {
            val index = mediaItems.value.indexOf(mediaItem)
            mediaPlayerController.seekMediaItem(index)
            mediaPlayerController.onPlayerEvent(PlayerEvent.PlayPause)
        }
    }
}

sealed class UiState {
    data object Initial : UiState()
    data object Ready : UiState()
}
