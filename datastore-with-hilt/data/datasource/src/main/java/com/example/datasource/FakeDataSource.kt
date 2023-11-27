package com.example.datasource

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataSource {
    fun getData(): Int {
        return (System.currentTimeMillis() / 1000L).toInt() // 현재 시간을 초 단위로 반환
    }

    fun requestData(): Flow<Int> = flow {
        while (true) {
            emit(getData()) // 현재 시간을 emit
            delay(1000) // 1초마다 반복
        }
    }
}