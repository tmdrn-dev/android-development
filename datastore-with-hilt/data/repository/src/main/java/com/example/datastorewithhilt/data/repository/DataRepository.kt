package com.example.datastorewithhilt.data.repository

import com.example.datastorewithhilt.data.model.FakeDataModel
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getData(): Flow<FakeDataModel>

    fun requestData(): Flow<FakeDataModel>
}