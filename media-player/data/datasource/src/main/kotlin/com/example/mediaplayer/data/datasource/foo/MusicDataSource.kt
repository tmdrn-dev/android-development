package com.example.mediaplayer.data.datasource.foo

import android.content.Context
import com.example.mediaplayer.data.datasource.MediaDataSource
import com.example.mediaplayer.data.model.JsonArray
import com.example.mediaplayer.data.model.JsonObject
import com.example.mediaplayer.data.model.MediaData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MusicDataSource(
    private val context: Context
): MediaDataSource {
    override fun getData(): Flow<List<MediaData>> = flow {
        val jsonString = context.assets.open("media_catalog.json").bufferedReader().use { it.readText() }
        val jsonArray = Gson().fromJson(jsonString, JsonArray::class.java)

        val updatedItems = jsonArray.music.map { jsonObject ->
            println("[SK] mediaItem.image: ${jsonObject.image}")
            println("[SK] context.packageName: ${context.packageName}")
            MediaData(
                id = jsonObject.id,
                title = jsonObject.title,
                album = jsonObject.album,
                artist = jsonObject.artist,
                genre = jsonObject.genre,
                source = getResourceIdByName(context, jsonObject.source),
                image = getResourceIdByName(context, jsonObject.image),
                trackNumber = jsonObject.trackNumber,
                totalTrackCount = jsonObject.totalTrackCount,
                duration = jsonObject.duration
            )
        }

        emit(updatedItems)
    }
}

fun getResourceIdByName(context: Context, resourceName: String): Int {
    return context.resources.getIdentifier(resourceName, "drawable", context.packageName)
}