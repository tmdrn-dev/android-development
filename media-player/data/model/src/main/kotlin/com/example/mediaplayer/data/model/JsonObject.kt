package com.example.mediaplayer.data.model

data class JsonObject(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val genre: String,
    val source: String,
    val image: String,
    val trackNumber: Int,
    val totalTrackCount: Int,
    val duration: Int
)

data class JsonArray(
    val music: List<JsonObject>
)