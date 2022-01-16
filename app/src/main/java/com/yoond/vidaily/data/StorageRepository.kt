package com.yoond.vidaily.data

import android.util.Log
import com.amplifyframework.core.Amplify
import java.io.File
import java.util.*

class StorageRepository {

    fun getAllVideos() {
        Amplify.Storage.list("videos/",
            { result ->
                result.items.forEach { item ->
                    Log.d("FILE_LIST", item.key)
                }
            },
        { Log.e("FILE_LIST", "failed") }
        )
    }

    fun uploadVideo(key: String, video: File) {
        Amplify.Storage.uploadFile(
            "videos/$key",
            video,
            { Log.d("FILE_UPLOAD", "success: ${it.key}") },
            { Log.e("FILE_UPLOAD", "failed", it)}
        )
    }
}