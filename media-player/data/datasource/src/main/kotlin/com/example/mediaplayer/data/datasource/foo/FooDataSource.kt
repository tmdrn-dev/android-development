package com.example.mediaplayer.data.datasource.foo

import com.example.mediaplayer.data.datasource.DataSource
import com.example.mediaplayer.data.model.DataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FooDataSource : DataSource {
    override fun getData(): DataModel {
        return DataModel("data")
    }

    override fun requestData(): Flow<DataModel> = flow {
        emit(getData())
    }
}
