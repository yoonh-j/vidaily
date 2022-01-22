package com.yoond.vidaily.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.api.graphql.model.ModelSubscription
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
                            list.add(VideoItem(
                                video.id,
                                video.title,
                                video.description,
                                video.views,
                                video.likes,
                                video.createdAt,
                                video.uid,
                                video.user.username,
                                "",
                                ""
                            ))
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
                            list.add(VideoItem(
                                video.id,
                                video.title,
                                video.description,
                                video.views,
                                video.likes,
                                video.createdAt,
                                video.uid,
                                video.user.username,
                                "",
                                ""
                            ))
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
                                list.add(VideoItem(
                                    video.id,
                                    video.title,
                                    video.description,
                                    video.views,
                                    video.likes,
                                    video.createdAt,
                                    video.uid,
                                    video.user.username,
                                    "",
                                    ""
                                ))
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

    fun getVideosByUser(uId: String): LiveData<MutableList<VideoItem>> {
        val videoList = MutableLiveData<MutableList<VideoItem>>()

        Amplify.API.query(ModelQuery.list(Video::class.java, Video.UID.contains(uId)),
            { response ->
                if (response.hasData()) {
                    val list = mutableListOf<VideoItem>()

                    response.data.items.forEach { video ->
                        if (video != null) {
                            list.add(VideoItem(
                                video.id,
                                video.title,
                                video.description,
                                video.views,
                                video.likes,
                                video.createdAt,
                                video.uid,
                                video.user.username,
                                "",
                                ""
                            ))
                        }
                    }
                    videoList.postValue(list) // background thread이기 때문에 postValue
                }
            },
            { Log.e("VIDEO_REPOSITORY", "getVideosByFollowing failed", it) }
        )
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

    fun getComments(vid: String): LiveData<MutableList<CommentItem>> {
        val commentList = MutableLiveData<MutableList<CommentItem>>()

        Amplify.API.query(ModelQuery.list(Comment::class.java, Comment.VID.contains(vid)),
            { result ->
                if (result.hasData()) {
                    val list = mutableListOf<CommentItem>()

                    result.data.items.forEach { comment ->
                        if (comment != null) {
                            list.add(CommentItem(
                                comment.id,
                                comment.content,
                                comment.createdAt,
                                comment.vid,
                                comment.uid,
                                comment.user.username,
                                ""
                            ))
                        }
                    }
                    commentList.postValue(list)
                }
            },
            { Log.e("VIDEO_REPOSITORY", "getComments failed", it) }
        )
        return commentList
    }

    fun subscribeComments(vId: String): LiveData<CommentItem> {
        val comment = MutableLiveData<CommentItem>()

        Amplify.API.subscribe(ModelSubscription.onCreate(Comment::class.java),
            { Log.i("VIDEO_REPOSITORY", "subscribeComments established") },
            { response ->
                if (response.data.vid == vId) {
                    val item = response.data as Comment

                    comment.postValue(CommentItem(
                        item.id,
                        item.content,
                        item.createdAt,
                        item.vid,
                        item.uid,
                        item.user.username,
                        ""
                    ))
                }
            },
            { Log.e("VIDEO_REPOSITORY", "subscribeComments failed", it) },
            { Log.i("VIDEO_REPOSITORY", "subscribeComments completed") }
        )
        return comment
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

    fun createComment(content: String, createdAt: String, vId: String) {
        val uid = Amplify.Auth.currentUser.userId
        val comment = Comment.builder()
            .content(content)
            .createdAt(createdAt)
            .vid(vId)
            .uid(uid)
            .build()
        Amplify.API.mutate(ModelMutation.create(comment),
            { Log.i("VIDEO_REPOSITORY", "createComment succeeded: $it") },
            { Log.e("VIDEO_REPOSITORY", "createComment failed", it) }
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

    fun updateVideoViews(videoItem: VideoItem) {
        val item = Video.builder()
            .title(videoItem.title)
            .views(videoItem.views + 1)
            .likes(videoItem.likes)
            .createdAt(videoItem.createdAt)
            .uid(videoItem.uid)
            .description(videoItem.description)
            .id(videoItem.id)
            .build()
        Amplify.API.mutate(ModelMutation.update(item),
            { Log.i("VIDEO_REPOSITORY", "updateVideoViews success: $it")} ,
            { Log.e("VIDEO_REPOSITORY", "updateVideoViews failed", it) }
        )
    }
}