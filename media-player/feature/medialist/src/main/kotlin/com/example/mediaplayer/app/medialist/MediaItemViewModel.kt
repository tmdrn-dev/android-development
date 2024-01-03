package com.example.mediaplayer.app.medialist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import com.example.mediaplayer.domain.ControlPlaybackUseCase
import com.example.mediaplayer.domain.GetMediaItemStateUseCase
import com.example.mediaplayer.domain.LoadMediaItemsUseCase
import com.example.mediaplayer.domain.MediaLoadingState
import com.example.mediaplayer.domain.SeekMediaItemUseCase
import com.example.mediaplayer.service.PlayerEvent
import com.example.mediaplayer.service.SimpleMediaState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaItemViewModel @Inject constructor(
    private val loadMediaItemsUseCase: LoadMediaItemsUseCase,
    private val getMediaItemStateUseCase: GetMediaItemStateUseCase,
    private val seekMediaItemUseCase: SeekMediaItemUseCase,
    private val controlPlaybackUseCase: ControlPlaybackUseCase,
) : ViewModel() {
    private var _mediaItems = MutableStateFlow<List<MediaItem>>(emptyList())
    val mediaItems: StateFlow<List<MediaItem>> = _mediaItems.asStateFlow()

    private val _currentItem = MutableStateFlow<MediaItem?>(null)
    val currentItem: StateFlow<MediaItem?> = _currentItem.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        loadMediaItems()
        viewModelScope.launch {
            getMediaItemStateUseCase().collect{ mediaState ->
                when (mediaState) {
//                    is SimpleMediaState.Buffering -> calculateProgressValues(mediaState.progress)
                    SimpleMediaState.Initial -> _uiState.value = UiState.Initial
                    is SimpleMediaState.Playing -> updateCurrentMediaItem(
                        mediaState.currentMediaItemIndex
                    )
                    else -> {}
//                    is SimpleMediaState.Progress -> calculateProgressValues(mediaState.progress)
//                    is SimpleMediaState.Ready -> {
//                        duration = mediaState.duration
//                        _uiState.value = UIState.Ready
                    }
                }
            }
        }

    private fun loadMediaItems() {
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
    }

    fun seekMediaItem(mediaItem: MediaItem) {
        viewModelScope.launch {
            seekMediaItemUseCase(mediaItem, PlayerEvent.Idle)
        }

        onUiEvent(UiEvent.PlayPause)
    }

    fun onUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                UiEvent.Backward -> {
                    seekMediaItemUseCase(currentItem.value, PlayerEvent.Backward)
//                    controlPlaybackUseCase(PlayerEvent.Backward)
                }
                UiEvent.Forward -> {
                    seekMediaItemUseCase(currentItem.value, PlayerEvent.Forward)
//                    controlPlaybackUseCase(PlayerEvent.Forward)
                }
                UiEvent.PlayPause -> controlPlaybackUseCase(PlayerEvent.PlayPause)
//                is UIEvent.UpdateProgress -> {
//                    progress = uiEvent.newProgress
//                    simpleMediaServiceHandler.onPlayerEvent(
//                        PlayerEvent.UpdateProgress(
//                            uiEvent.newProgress
//                        )
//                    )
//                }
            }
        }
    }

    private fun updateCurrentMediaItem(index: Int) {
        _currentItem.value = mediaItems.value[index]
        println("[SK] currentItem = ${currentItem.value?.mediaMetadata?.title.toString()}")
    }

    sealed class UiState {
        data object Initial : UiState()
        data object Ready : UiState()
    }

    sealed class UiEvent {
        data object PlayPause : UiEvent()
        data object Backward : UiEvent()
        data object Forward : UiEvent()
//        data class UpdateProgress(val newProgress: Float) : UIEvent()
    }
}
