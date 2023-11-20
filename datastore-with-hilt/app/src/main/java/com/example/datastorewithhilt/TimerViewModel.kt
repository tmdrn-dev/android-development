package com.example.datastorewithhilt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastorewithhilt.domain.FormattedTimerUseCase
import com.example.datastorewithhilt.domain.PauseTimerUseCase
import com.example.datastorewithhilt.domain.ResumeTimerUseCase
import com.example.datastorewithhilt.domain.StartTimerUseCase
import com.example.datastorewithhilt.domain.StopTimerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val startTimerUseCase: StartTimerUseCase,
    private val stopTimerUseCase: StopTimerUseCase,
    private val pauseTimerUseCase: PauseTimerUseCase,
    private val resumeTimerUseCase: ResumeTimerUseCase,
    private val formattedTimerUseCase: FormattedTimerUseCase
) : ViewModel() {
    // To Do: val timerTime: StateFlow<TimerUiState>
    val timerTime: StateFlow<String> = formattedTimerUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = "00:00:00"
        )

    fun startTimer(time: Int) {
        viewModelScope.launch {
            startTimerUseCase(time)
        }
    }

    fun stopTimer() {
        viewModelScope.launch {
            stopTimerUseCase()
        }
    }

    fun pauseTimer() {
        viewModelScope.launch {
            pauseTimerUseCase()
        }
    }

    fun resumeTimer() {
        viewModelScope.launch {
            resumeTimerUseCase()
        }
    }
}
