package com.example.datastorewithhilt.domain

import com.example.datastorewithhilt.data.model.TimerData
import com.example.datastorewithhilt.data.repository.TimerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FormattedTimerUseCase @Inject constructor (
    private val timerRepository: TimerRepository
) {
    operator fun invoke(): Flow<String> {
        return timerRepository.timeFlow
            .map { timerData ->
                formatTime(timerData)
            }
    }

    private fun formatTime(time: TimerData): String {
        val hours = time.second / 3600
        val minutes = (time.second % 3600) / 60
        val sec = time.second % 60
        return String.format("%02d:%02d:%02d", hours, minutes, sec)
    }
}

class StartTimerUseCase @Inject constructor(private val timerRepository: TimerRepository) {
    suspend operator fun invoke(seconds: Int) {
        timerRepository.start(seconds)
    }
}

class StopTimerUseCase @Inject constructor(private val timerRepository: TimerRepository) {
    suspend operator fun invoke() {
        timerRepository.stop()
    }
}

class PauseTimerUseCase @Inject constructor(private val timerRepository: TimerRepository) {
    suspend operator fun invoke() {
        timerRepository.pause()
    }
}

class ResumeTimerUseCase @Inject constructor(private val timerRepository: TimerRepository) {
    suspend operator fun invoke() {
        timerRepository.resume()
    }
}