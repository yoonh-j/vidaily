package com.yoond.vidaily.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.Comment
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

    fun getVideosByDate(): LiveData<MutableList<VideoItem>> {
        val todayStart = getTodayStartInMillis()
        val videoList = MutableLiveData<MutableList<VideoItem>>()

        Amplify.API.query(
            ModelQuery.list(
                Video::class.java,
                Video.CREATED_AT.between(
                    todayStart.toString(),
                    todayStart.plus(DAY_IN_MILLIS).toString()
                )
            ),
            { response ->
                Log.i("VIDEO_REPOSITORY", "getVideosByDate succeeded: $response")

                if (response.hasData()) {
                    val list = mutableListOf<VideoItem>()

                    response.data.items.forEach { video ->
                        if (video != null) {
                            list.add(VideoItem(video, "", ""))
                        }
                    }
                    videoList.postValue(list) // background thread이기 때문에 postValue
                }
            },
            { Log.e("VIDEO_REPOSITORY", "getVideosByDate failed: ", it) }
        )
        return videoList
    }

    fun getVideosByViews(): LiveData<MutableList<VideoItem>> {
        val videoList = MutableLiveData<MutableList<VideoItem>>()

        Amplify.API.query(
            ModelQuery.list(Video::class.java),
            { response ->
                Log.i("VIDEO_REPOSITORY", "getVideosByViews succeeded: $response")

                if (response.hasData()) {
                    val list = mutableListOf<VideoItem>()

                    response.data.items.forEach { video ->
                        if (video != null) {
                            list.add(VideoItem(video, "", ""))
                        }
                    }
                    videoList.postValue(list) // background thread이기 때문에 postValue
                }
            },
            { Log.e("VIDEO_REPOSITORY", "getVideosByViews failed: ", it) }
        )
        return videoList
    }

    fun getVideosByFollowing(fIds: List<String>): LiveData<MutableList<VideoItem>> {
        val videoList = MutableLiveData<MutableList<VideoItem>>()

        fIds.forEach { fId ->
            Amplify.API.query(ModelQuery.list(Video::class.java, Video.UID.contains(fId)),
                { response ->
                    if (response.hasData()) {
                        val list = mutableListOf<VideoItem>()

                        response.data.items.forEach { video ->
                            if (video != null) {
                                list.add(VideoItem(video, "", ""))
                            }
                        }
                        videoList.postValue(list) // background thread이기 때문에 postValue
                    }
                },
                { Log.e("VIDEO_REPOSITORY", "getVideosByFollowing failed", it) }
            )
        }

        return videoList
    }

    /**
     * gets video by vId
     */
    fun getVideo(vId: String): LiveData<Video> {
        val video = MutableLiveData<Video>()

        Amplify.API.query(ModelQuery.get(Video::class.java, vId),
            { response ->
                if (response.hasData()) {
                    Log.i("VIDEO_REPOSITORY", "getVideo succeeded, $response")
                    video.postValue(response.data)
                }
            },
            { Log.e("VIDEO_REPOSITORY", "getVideo failed", it)}
        )
        return video
    }

    fun getComments(vid: String): LiveData<MutableList<Comment>> {
        val commentList = MutableLiveData<MutableList<Comment>>()

        Amplify.API.query(ModelQuery.list(Comment::class.java, Comment.VID.contains(vid)),
            { result ->
                if (result.hasData()) {
                    val list = mutableListOf<Comment>()

                    result.data.items.forEach { comment ->
                        if (comment != null) {
                            list.add(comment)
                        }
                    }
                    commentList.postValue(list)
                }
            },
            { Log.e("VIDEO_REPOSITORY", "getComments failed", it) }
        )
        return commentList
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
                createVideo(vid, title,timeInMillis, description)
                Log.i("VIDEO_REPOSITORY", "uploadVideo success: ${it.key}")
            },
            { Log.e("VIDEO_REPOSITORY", "uploadVideo failed", it)}
        )
    }

    private fun createVideo(
        vid: String,
        title: String,
        timeInMillis: Long,
        description: String
    ) {
        val uid = Amplify.Auth.currentUser.userId
        val video = Video.builder()
            .title(title)
            .views(0)
            .likes(0)
            .createdAt(timeInMillis.toString())
            .uid(uid)
            .description(description)
            .id(vid)
            .build()

        Amplify.API.mutate(ModelMutation.create(video),
            { Log.i("VIDEO_REPOSITORY", "createVideo success: $it")} ,
            { Log.e("VIDEO_REPOSITORY", "createVideo failed", it) }
        )
    }
}