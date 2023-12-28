package com.example.mediaplayer.data.datasource

import com.example.mediaplayer.data.model.JsonObject
import com.example.mediaplayer.data.model.MediaCatalog
import com.example.mediaplayer.data.model.MediaData
import kotlinx.coroutines.flow.Flow

interface MediaDataSource {
    fun getData(): Flow<MediaCatalog>
}