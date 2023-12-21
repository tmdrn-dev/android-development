package com.example.mediaplayer.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class MediaData(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val genre: String,
    @RawRes val source: Int,
    @DrawableRes val image: Int,
    val trackNumber: Int,
    val totalTrackCount: Int,
    val duration: Int
)

data class MediaCatalog(
    val music: List<MediaData>
)