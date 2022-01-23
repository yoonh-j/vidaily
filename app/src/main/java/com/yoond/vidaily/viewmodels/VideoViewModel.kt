package com.yoond.vidaily.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yoond.vidaily.data.CommentItem
import com.yoond.vidaily.data.VideoItem
import com.yoond.vidaily.data.VideoRepository
import java.io.File

class VideoViewModel: ViewModel() {
    private val repository = VideoRepository()

    fun getAllVideos() =
        repository.getAllVideos()

    fun getVideosByDate(): LiveData<MutableList<VideoItem>> =
        repository.getVideosByDate()

    fun getVideosByViews(): LiveData<MutableList<VideoItem>> =
        repository.getVideosByViews()

    fun getVideosByFollowing(fIds: List<String>): LiveData<MutableList<VideoItem>> =
        repository.getVideosByFollowing(fIds)

    fun getVideosByUser(uId: String): LiveData<MutableList<VideoItem>> =
        repository.getVideosByUser(uId)

    fun getVideosByQuery(query: String): LiveData<MutableList<VideoItem>> =
        repository.getVideosByQuery(query)

    fun getComments(vId: String): LiveData<MutableList<CommentItem>> =
        repository.getComments(vId)

    fun subscribeComments(vId: String): LiveData<CommentItem> =
        repository.subscribeComments(vId)

    fun getVideo(vId: String) =
        repository.getVideo(vId)

    fun updateVideoViews(videoItem: VideoItem) =
        repository.updateVideoViews(videoItem)

    fun uploadVideo(
        video: File,
        title: String,
        description: String,
        timeInMillis: Long
    ) = repository.uploadVideo(video, title, description, timeInMillis)

    fun uploadComment(content: String, createdAt: String, vId: String) =
        repository.createComment(content, createdAt, vId)
}