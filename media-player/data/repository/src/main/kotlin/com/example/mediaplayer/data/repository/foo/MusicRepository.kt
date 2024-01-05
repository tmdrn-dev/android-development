package com.example.mediaplayer.data.repository.foo

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.example.mediaplayer.data.datasource.MediaDataSource
import com.example.mediaplayer.data.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MusicRepository @Inject constructor(
    private val dataSource: MediaDataSource
) : MediaRepository {

    override val mediaItemList: Flow<List<MediaItem>> =
        dataSource.getMediaData().map { mediaDataList ->
            mediaDataList.map { mediaData ->
                MediaItem.Builder()
                    .setUri(mediaData.source)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setMediaType(MediaMetadata.MEDIA_TYPE_FOLDER_ALBUMS)
                            .setArtworkUri(mediaData.image)
                            .setTitle(mediaData.title)
                            .setArtist(mediaData.artist)
                            .build()
                    ).build()
            }
        }
}

