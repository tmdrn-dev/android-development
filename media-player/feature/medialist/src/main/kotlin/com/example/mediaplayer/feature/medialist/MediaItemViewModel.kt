package com.example.mediaplayer.feature.medialist

import com.example.mediaplayer.domain.GetMusicSourceUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaplayer.data.model.MediaData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaItemViewModel @Inject constructor(
    private val getMusicSourceUseCase: GetMusicSourceUseCase,
) : ViewModel() {
    private val _mediaItems = MutableStateFlow<List<MediaData>>(emptyList())
    val mediaItems: StateFlow<List<MediaData>> = _mediaItems.asStateFlow()

    init {
        loadMediaItems()
    }

    private fun loadMediaItems() {
        viewModelScope.launch {
            getMusicSourceUseCase().collect { items ->
                _mediaItems.value = items
            }
        }
    }
}
