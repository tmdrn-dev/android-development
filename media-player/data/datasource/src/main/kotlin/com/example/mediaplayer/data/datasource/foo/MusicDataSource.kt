package com.example.mediaplayer.data.datasource.foo

import android.content.Context
import android.net.Uri
import com.example.mediaplayer.data.datasource.MediaDataSource
import com.example.mediaplayer.data.model.JsonArray
import com.example.mediaplayer.data.model.JsonObject
import com.example.mediaplayer.data.model.MediaCatalog
import com.example.mediaplayer.data.model.MediaData
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MusicDataSource(
    private val context: Context
): MediaDataSource {
    override fun getData(): Flow<MediaCatalog> = flow {
        val jsonString = context.assets.open("media_catalog.json").bufferedReader().use { it.readText() }
        val jsonArray = Gson().fromJson(jsonString, JsonArray::class.java)

        val mediaData = jsonArray.music.map { jsonObject ->
            MediaData(
                id = jsonObject.id,
                title = jsonObject.title,
                album = jsonObject.album,
                artist = jsonObject.artist,
//                genre = jsonObject.genre,
                source = Uri.parse(
                    getResourceUriByName(
                        context,
                        "raw",
                        jsonObject.source
                    )
                ),
                image = Uri.parse (
                    getResourceUriByName(
                        context,
                        "drawable",
                        jsonObject.image
                    )
                )
//                trackNumber = jsonObject.trackNumber,
//                totalTrackCount = jsonObject.totalTrackCount,
//                duration = jsonObject.duration
            )
        }

        emit(MediaCatalog(mediaList = mediaData))
    }
}

fun getResourceUriByName (
    context: Context,
    resourceName: String,
    resourceType: String
): String {
    val modifiedResourceName = resourceName.split(".")[0]
    val resourceId = context.resources.getIdentifier(
        modifiedResourceName,
        resourceType,
        context.packageName
    )
    return "android.resource://" + context.packageName + "/" + resourceId
}