package com.example.datasource

import com.example.datastorewithhilt.data.model.FakeDataModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataSource {
    fun getData(): FakeDataModel {
        return FakeDataModel((System.currentTimeMillis() / 1000L).toInt())
    }

    fun requestData(): Flow<FakeDataModel> = flow {
        while (true) {
            emit(getData()) // 현재 시간을 emit
            delay(1000) // 1초마다 반복
        }
    }
}