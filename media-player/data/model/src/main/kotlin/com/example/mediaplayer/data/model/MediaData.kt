package com.example.mediaplayer.data.model

import android.net.Uri

data class MediaData(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val source: Uri,
    val image: Uri,
)
