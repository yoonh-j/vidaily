package com.yoond.vidaily.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Metadata
import com.amplifyframework.datastore.generated.model.Video
import com.yoond.vidaily.utils.DAY_IN_MILLIS
import com.yoond.vidaily.utils.getTodayStartInMillis
import java.io.File

class VideoRepository {

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

    fun getAllMetaData(): LiveData<MutableList<Metadata>> {
        val allMetadata = MutableLiveData<MutableList<Metadata>>()

        Amplify.API.query(
            ModelQuery.list(Metadata::class.java),
            { response ->
                Log.i("METADATA_LIST", response.toString())

                if (response.hasData()) {
                    val list = mutableListOf<Metadata>()

                    response.data.items.forEach { metadata ->
                        if (metadata != null) {
                            list.add(metadata)
                            Log.i("METADATA_LIST", metadata.title)
                        }
                    }
                    allMetadata.postValue(list) // background thread이기 때문에 postValue
                }
            },
            { Log.e("METADATA_LIST", "failed: ", it) }
        )
        return allMetadata
    }

    fun getMetadataByDate(): LiveData<MutableList<Metadata>> {
        val todayStart = getTodayStartInMillis()
        val metadataList = MutableLiveData<MutableList<Metadata>>()

        Amplify.API.query(
            ModelQuery.list(
                Metadata::class.java,
                Metadata.CREATED_AT.between(
                    todayStart.toString(),
                    todayStart.plus(DAY_IN_MILLIS).toString()
                )
            ),
            { response ->
                Log.i("METADATA_TODAY_LIST", response.toString())

                if (response.hasData()) {
                    val list = mutableListOf<Metadata>()

                    response.data.items.forEach { metadata ->
                        if (metadata != null) {
                            list.add(metadata)
                            Log.i("METADATA_TODAY_LIST", metadata.title)
                        }
                    }
                    metadataList.postValue(list) // background thread이기 때문에 postValue
                }
            },
            { Log.e("METADATA_TODAY_LIST", "failed: ", it) }
        )
        return metadataList
    }

    fun getMetadataByViews(): LiveData<MutableList<Metadata>> {
        val metadataList = MutableLiveData<MutableList<Metadata>>()

        Amplify.API.query(
            ModelQuery.list(Metadata::class.java),
            { response ->
                Log.i("VIDEO_REPOSITORY", "getMetadataByViews succeeded: $response")

                if (response.hasData()) {
                    val list = mutableListOf<Metadata>()

                    response.data.items.forEach { metadata ->
                        if (metadata != null) {
                            list.add(metadata)
                        }
                    }
                    metadataList.postValue(list) // background thread이기 때문에 postValue
                }
            },
            { Log.e("VIDEO_REPOSITORY", "getMetadataByViews failed: ", it) }
        )
        return metadataList
    }

    fun getMetadataByFollowing(fIds: List<String>): LiveData<MutableList<Metadata>> {
        val metadataList = MutableLiveData<MutableList<Metadata>>()

        fIds.forEach { fId ->
            Amplify.API.query(ModelQuery.list(Metadata::class.java, Metadata.UID.contains(fId)),
                { response ->
                    if (response.hasData()) {
                        Log.i("VIDEO_REPOSITORY", "getMetadataByFollowing succeeded, $response")

                        val list = mutableListOf<Metadata>()
                        response.data.items.forEach { metadata ->
                            if (metadata != null) {
                                list.add(metadata)
                            }
                        }
                        metadataList.postValue(list)
                    }
                },
                { Log.e("VIDEO_REPOSITORY", "getMetadataByFollowing failed", it) }
            )
        }

        return metadataList
    }

    /**
     * uploads video in storage
     * if succeeded, creates metadata and video in db
     */
    fun uploadVideo(
        video: File,
        title: String,
        description: String,
        timeInMillis: Long
    ) {
        val vid = "${Amplify.Auth.currentUser.userId}-$timeInMillis"

        Amplify.Storage.uploadFile(
            "videos/$vid",
            video,
            {
                createMetadata(vid, title, timeInMillis)
                createVideo(vid, description)
                Log.d("VIDEO_REPOSITORY", "uploadVideo success: ${it.key}")
            },
            { Log.e("VIDEO_REPOSITORY", "uploadVideo failed", it)}
        )
    }

    private fun createMetadata(
        vid: String,
        title: String,
        timeInMillis: Long
    ) {
        // get a url of the uploaded video
        Amplify.Storage.getUrl(
            "videos/$vid",
            { result ->
                val uid = Amplify.Auth.currentUser.userId
                val url = result.url.toString()
                val metadata = Metadata.builder()
                    .url(url)
                    .title(title)
                    .views(0)
                    .likes(0)
                    .createdAt(timeInMillis.toString())
                    .uid(uid)
                    .id(vid)
                    .build()

                Amplify.API.mutate(ModelMutation.create(metadata),
                    { Log.i("VIDEO_REPOSITORY", "createMetadata success: $it") },
                    { Log.e("VIDEO_REPOSITORY", "createMetadata failed", it) }
                )
            },
            { Log.e("VIDEO_REPOSITORY", "createMetadata getUrl failed", it) }
        )
    }

    private fun createVideo(vid: String, description: String) {
        val video = Video.builder()
            .id(vid)
            .description(description)
            .build()

        Amplify.API.mutate(ModelMutation.create(video),
            { Log.i("VIDEO_REPOSITORY", "createVideo success: $it")} ,
            { Log.e("VIDEO_REPOSITORY", "createVideo failed", it) }
        )
    }
}