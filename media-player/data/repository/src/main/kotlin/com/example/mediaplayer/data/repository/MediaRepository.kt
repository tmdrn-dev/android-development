package com.example.mediaplayer.data.repository

import com.example.mediaplayer.data.model.MediaData
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    fun getMediaSource(): Flow<List<MediaData>>
}
