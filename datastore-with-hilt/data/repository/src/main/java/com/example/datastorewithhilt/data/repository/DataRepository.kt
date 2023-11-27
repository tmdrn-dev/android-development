package com.example.datastorewithhilt.data.repository

import com.example.datastorewithhilt.data.model.FakeDataModel
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun getData(): Flow<FakeDataModel>

    suspend fun requestData(): Flow<FakeDataModel>
}