package com.example.mediaplayer.data.repository.foo

import com.example.mediaplayer.data.datasource.MediaDataSource
import com.example.mediaplayer.data.model.MediaData
import com.example.mediaplayer.data.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val dataSource: MediaDataSource
) : MediaRepository {
    override fun getMediaSource(): Flow<List<MediaData>> {
        val mediaCatalog = dataSource.getData().collect {
            it.mediaList.map {

            }
        }
    }
}

