package com.example.mediaplayer.data.repository.foo

import com.example.mediaplayer.data.datasource.foo.FooDataSource
import com.example.mediaplayer.data.model.DataModel
import com.example.mediaplayer.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FooRepository @Inject constructor(
    private val dataSource: FooDataSource
) : Repository {
    override fun getData(): DataModel {
        return dataSource.getData()
    }

    override fun requestData(): Flow<DataModel> {
        return dataSource.requestData()
    }
}
