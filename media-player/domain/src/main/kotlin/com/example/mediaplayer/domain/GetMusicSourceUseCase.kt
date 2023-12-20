package com.example.mediaplayer.domain

import com.example.mediaplayer.data.model.MediaData
import com.example.mediaplayer.data.repository.foo.MusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMusicSourceUseCase @Inject constructor(
    private val mediaRepository: MusicRepository
) {
    operator fun invoke(): Flow<List<MediaData>> {
        return mediaRepository.getMediaSource()
    }
}