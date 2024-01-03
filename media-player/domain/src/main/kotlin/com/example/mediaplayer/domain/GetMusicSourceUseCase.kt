package com.example.mediaplayer.domain

import android.media.browse.MediaBrowser
import androidx.media3.common.MediaItem
import com.example.mediaplayer.data.model.MediaData
import com.example.mediaplayer.data.repository.foo.MusicRepository
import com.example.mediaplayer.service.MusicController
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMusicSourceUseCase @Inject constructor(
    private val mediaRepository: MusicRepository,
    private val mediaController: MusicController,
) {
    operator fun invoke(): Flow<List<MediaItem>> {
        return mediaRepository.mediaItemList
    }
}