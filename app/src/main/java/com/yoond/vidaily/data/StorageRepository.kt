package com.yoond.vidaily.data

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Metadata
import java.io.File

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

    fun getAllMetaData() {
        Amplify.API.query(
            ModelQuery.list(Metadata::class.java),
            { response ->
                if (response.hasData()) {
                    response.data.forEach { metadata ->
                        Log.i("METADATA_LIST", metadata.title)
                    }
                }
            },
            { Log.e("METADATA_LIST", "failed: ", it) }
        )
    }

    fun uploadVideo(
        video: File,
        key: String,
        uid: String,
        title: String,
        description: String,
        timeInMillis: Long
    ) {
        Amplify.Storage.uploadFile(
            "videos/$key",
            video,
            {
                uploadMetadata(key, uid, title, description, timeInMillis)
                Log.d("UPLOAD_VIDEO", "video success: ${it.key}")
            },
            { Log.e("UPLOAD_VIDEO", "video failed", it)}
        )
    }

    private fun uploadMetadata(
        key: String,
        uid: String,
        title: String,
        description: String,
        timeInMillis: Long
    ) {
        // get a url of the uploaded video
        Amplify.Storage.getUrl(
            "videos/$key",
            { result ->
                val url = result.url.toString()
                val metadata = Metadata.builder()
                    .key(key)
                    .uid(uid)
                    .url(url)
                    .title(title)
                    .timeInMillis(timeInMillis.toString())
                    .views(0)
                    .likes(0)
                    .description(description)
                    .build()

                Amplify.API.mutate(ModelMutation.create(metadata),
                    { Log.i("UPLOAD_METADATA", "success: $it") },
                    { Log.e("UPLOAD_METADATA", "failed", it) }
                )
            },
            { Log.e("UPLOAD_METADATA", "url failed", it) }
        )
    }
}