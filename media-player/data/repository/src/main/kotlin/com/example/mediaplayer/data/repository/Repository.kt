package com.example.mediaplayer.data.repository

import com.example.mediaplayer.data.model.DataModel
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getData(): DataModel

    fun requestData(): Flow<DataModel>
}
