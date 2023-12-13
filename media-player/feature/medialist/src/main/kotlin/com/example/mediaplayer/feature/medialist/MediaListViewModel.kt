package com.example.mediaplayer.feature.medialist

import com.example.mediaplayer.data.model.DataModel
import com.example.mediaplayer.domain.SampleUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MediaListViewModel @Inject constructor(
    private val sampleUseCase: SampleUseCase,
) : ViewModel() {
    val data: StateFlow<DataModel> =
        sampleUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DataModel("none"),
            )
}
