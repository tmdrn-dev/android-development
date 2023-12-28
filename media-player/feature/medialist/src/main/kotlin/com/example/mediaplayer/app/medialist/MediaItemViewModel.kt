package com.example.mediaplayer.app.medialist

import android.net.Uri
import com.example.mediaplayer.domain.GetMusicSourceUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MediaMetadata.MEDIA_TYPE_FOLDER_ALBUMS
import com.example.mediaplayer.data.model.MediaData
import com.example.mediaplayer.feature.medialist.R
import com.example.mediaplayer.service.MusicServiceHandler
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
    private val musicServiceHandler: MusicServiceHandler,
//    private val getMusicSourceUseCase: GetMusicSourceUseCase,
) : ViewModel() {
//    private val _mediaItems = MutableStateFlow<List<MediaData>>(emptyList())
//    val mediaItems: StateFlow<List<MediaData>> = _mediaItems.asStateFlow()

    private val _uiState = MutableStateFlow<UIState>(UIState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadData()
            _uiState.value = UIState.Ready

//            musicServiceHandler.simpleMediaState.collect { mediaState ->
//                when (mediaState) {
//                    is SimpleMediaState.Buffering -> calculateProgressValues(mediaState.progress)
//                    SimpleMediaState.Initial -> _uiState.value = UIState.Initial
//                    is SimpleMediaState.Playing -> isPlaying = mediaState.isPlaying
//                    is SimpleMediaState.Progress -> calculateProgressValues(mediaState.progress)
//                    is SimpleMediaState.Ready -> {
//                        duration = mediaState.duration
//                        _uiState.value = UIState.Ready
//                    }
//                }
//            }

            musicServiceHandler.onPlayerEvent(PlayerEvent.PlayPause)
        }
    }

    private fun loadData() {
        val mediaItem = MediaItem.Builder()
            //.setUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
            .setUri(Uri.parse("android.resource://com.example.mediaplayer/" + R.raw.drop_and_roll))
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setMediaType(MEDIA_TYPE_FOLDER_ALBUMS)
                    .setArtworkUri(Uri.parse("android.resource://com.example.mediaplayer/" + R.drawable.drop_and_roll_art))
                    .setAlbumTitle("SoundHelix")
                    .setDisplayTitle("Song 1")
                    .build()
            ).build()

        val mediaItem2 = MediaItem.Builder()
            //.setUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
            .setUri(Uri.parse("android.resource://com.example.mediaplayer/" + R.raw.jazz_in_paris))
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setMediaType(MEDIA_TYPE_FOLDER_ALBUMS)
                    .setArtworkUri(Uri.parse("android.resource://com.example.mediaplayer/" + R.drawable.jazz_in_paris_art))
                    .setAlbumTitle("SoundHelix")
                    .setDisplayTitle("Song 1")
                    .build()
            ).build()

        val mediaItemList = mutableListOf<MediaItem>(mediaItem, mediaItem2)

//        musicServiceHandler.addMediaItem(mediaItem)
        musicServiceHandler.addMediaItemList(mediaItemList)
    }

//    private fun loadMediaItems() {
//        viewModelScope.launch {
//            getMusicSourceUseCase().collect { items ->
//                _mediaItems.value = items
//            }
//        }
//    }

    sealed class UIState {
        object Initial : UIState()
        object Ready : UIState()
    }
}
