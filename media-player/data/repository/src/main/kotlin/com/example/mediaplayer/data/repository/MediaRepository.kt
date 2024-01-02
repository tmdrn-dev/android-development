package com.example.mediaplayer.data.repository

import androidx.media3.common.MediaItem
import com.example.mediaplayer.data.model.MediaData
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    val mediaItemList: Flow<List<MediaItem>>
}
