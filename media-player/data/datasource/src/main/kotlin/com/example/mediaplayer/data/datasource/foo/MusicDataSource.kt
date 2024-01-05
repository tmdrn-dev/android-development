package com.example.mediaplayer.data.datasource.foo

import android.content.Context
import android.net.Uri
import com.example.mediaplayer.data.datasource.MediaDataSource
import com.example.mediaplayer.data.model.JsonArray
import com.example.mediaplayer.data.model.MediaData
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow

class MusicDataSource(
    private val context: Context
): MediaDataSource {
    override fun getMediaData() = flow {
        val jsonString = context.assets.open("media_catalog.json")
            .bufferedReader()
            .use { it.readText() }
        val jsonArray = Gson().fromJson(jsonString, JsonArray::class.java)

        val mediaDataList = jsonArray.music.map { jsonObject ->
            MediaData(
                id = jsonObject.id,
                title = jsonObject.title,
                album = jsonObject.album,
                artist = jsonObject.artist,
                source = Uri.parse(getResourceUriByName(
                        context,
                        "raw",
                        jsonObject.source
                    )
                ),
                image = Uri.parse(getResourceUriByName(
                        context,
                        "drawable",
                        jsonObject.image
                    )
                ),
            )
        }

        emit(mediaDataList)
    }
}

fun getResourceUriByName (
    context: Context,
    resourceType: String,
    resourceName: String
): String {
    val modifiedResourceName = resourceName.split(".")[0]
    val resourceId = context.resources.getIdentifier(
        modifiedResourceName,
        resourceType,
        context.packageName
    )
    val result = "android.resource://" + context.packageName + "/" + resourceId
    return result
}