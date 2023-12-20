package com.example.mediaplayer.data.datasource.foo

import android.content.Context
import com.example.mediaplayer.data.datasource.MediaDataSource
import com.example.mediaplayer.data.model.MediaCatalog
import com.example.mediaplayer.data.model.MediaData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MusicDataSource(private val context: Context):
    MediaDataSource {
    override fun getData(): Flow<List<MediaData>> = flow {
        val jsonString = context.assets.open("media_catalog.json")
            .bufferedReader()
            .use { it.readText() }
        val mediaCatalog = Gson().fromJson(jsonString, MediaCatalog::class.java)
        emit(mediaCatalog.music)
    }
}
