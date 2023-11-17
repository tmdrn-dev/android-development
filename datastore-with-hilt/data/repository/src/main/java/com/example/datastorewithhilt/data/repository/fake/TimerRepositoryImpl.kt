package com.example.datastorewithhilt.data.repository.fake

import com.example.datastorewithhilt.data.repository.TimerRepository
import com.example.datastorewithhilt.data.model.TimerData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.*

class TimerRepositoryImpl : TimerRepository {
    private val _timeFlow = MutableStateFlow(TimerData(0))
    override val timeFlow = _timeFlow.asStateFlow()

    private var timerJob: Job? = null
    private var remainingTime = 0

    override suspend fun start(second: Int) {
        remainingTime = second
        _timeFlow.value = TimerData(remainingTime)
        timerJob?.cancel()
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive && remainingTime > 0) {
                delay(1000)
                remainingTime--
                _timeFlow.value = TimerData(remainingTime)
            }
        }
    }

    override suspend fun stop() {
        timerJob?.cancel()
        remainingTime = 0
        _timeFlow.value = TimerData(0)
    }

    override suspend fun pause() {
        timerJob?.cancel()
    }

    override suspend fun resume() {
        timerJob?.cancel()
        timerJob = CoroutineScope(Dispatchers.Default).launch {
            while (isActive && remainingTime > 0) {
                delay(1000)
                remainingTime--
                _timeFlow.value = TimerData(remainingTime)
            }
        }
    }
}