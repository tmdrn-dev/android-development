package com.example.datastorewithhilt.data.repository

import com.example.datastorewithhilt.data.model.TimerData
import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    val timeFlow: Flow<TimerData>

    suspend fun start(second: Int)

    suspend fun stop()

    suspend fun pause()

    suspend fun resume()
}