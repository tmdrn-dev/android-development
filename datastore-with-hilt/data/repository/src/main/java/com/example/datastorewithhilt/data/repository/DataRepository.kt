package com.example.datastorewithhilt.data.repository

import com.example.datastorewithhilt.data.model.FakeDataModel
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun getData(): FakeDataModel

    fun requestData(): Flow<FakeDataModel>
}