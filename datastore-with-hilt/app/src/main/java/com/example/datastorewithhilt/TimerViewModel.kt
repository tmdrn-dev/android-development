package com.example.datastorewithhilt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastorewithhilt.data.model.TimerData
import com.example.datastorewithhilt.data.repository.TimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val timerRepository: TimerRepository
) : ViewModel() {

    // To Do: val timerTime: StateFlow<TimerUiState>
    val timerTime = timerRepository.timeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TimerData(0)
    )

    fun startTimer(time: Int) {
        viewModelScope.launch {
            timerRepository.start(time)
        }
    }

    fun stopTimer() {
        viewModelScope.launch {
            timerRepository.stop()
        }
    }

    fun pauseTimer() {
        viewModelScope.launch {
            timerRepository.pause()
        }
    }

    fun resumeTimer() {
        viewModelScope.launch {
            timerRepository.resume()
        }
    }
}
