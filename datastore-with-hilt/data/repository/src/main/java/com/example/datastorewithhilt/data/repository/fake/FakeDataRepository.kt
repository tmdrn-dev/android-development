package com.example.datastorewithhilt.data.repository.fake

import com.example.datasource.FakeDataSource
import com.example.datastorewithhilt.data.model.FakeDataModel
import com.example.datastorewithhilt.data.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FakeDataRepository @Inject constructor(
    private val dataSource: FakeDataSource
): DataRepository {
    override suspend fun getData(): Flow<FakeDataModel> {
        return flow {
            emit(FakeDataModel(dataSource.getData()))
        }
    }

    override suspend fun requestData(): Flow<FakeDataModel> {
        return dataSource.requestData().map { time ->
            FakeDataModel(time)
        }
    }
}