package com.example.mediaplayer.data.datasource

import com.example.mediaplayer.data.model.DataModel
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getData(): DataModel

    fun requestData(): Flow<DataModel>
}