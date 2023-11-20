package com.example.datastorewithhilt.domain

import com.example.datastorewithhilt.data.repository.TimerRepository
import com.example.datastorewithhilt.data.model.TimerData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FormattedTimerUseCase @Inject constructor (
    private val timerRepository: TimerRepository
) {
    operator fun invoke(): Flow<String> {
        return timerRepository.timeFlow
            .map { timerData ->
                // 초를 hh:mm:ss 형태로 변환
                formatSecondsToHMS(timerData)
            }
    }

    private fun formatSecondsToHMS(time: TimerData): String {
        val hours = time.second / 3600
        val minutes = (time.second % 3600) / 60
        val sec = time.second % 60
        return String.format("%02d:%02d:%02d", hours, minutes, sec)
    }
}