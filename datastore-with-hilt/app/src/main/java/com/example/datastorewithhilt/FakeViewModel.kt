package com.example.datastorewithhilt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastorewithhilt.data.model.FakeDataModel
import com.example.datastorewithhilt.domain.FakeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FakeViewModel @Inject constructor(
    private val fakeUseCase: FakeUseCase,
) : ViewModel() {
    private val _data = MutableStateFlow<FakeDataModel?>(null)
    val data = _data.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            fakeUseCase().collect { newData ->
                _data.value = newData
            }
        }
    }
}