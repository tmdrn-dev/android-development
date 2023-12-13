package com.example.datastorewithhilt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastorewithhilt.data.model.FakeDataModel
import com.example.datastorewithhilt.domain.FakeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FakeViewModel @Inject constructor(
    private val fakeDataUseCase: FakeDataUseCase,
) : ViewModel() {
    val data = fakeDataUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )
}