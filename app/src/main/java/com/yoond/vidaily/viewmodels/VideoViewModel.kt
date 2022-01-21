package com.yoond.vidaily.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.datastore.generated.model.Comment
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

    fun getComments(vId: String): LiveData<MutableList<Comment>> =
        repository.getComments(vId)

    fun getVideo(vId: String) =
        repository.getVideo(vId)

    fun uploadVideo(
        video: File,
        title: String,
        description: String,
        timeInMillis: Long
    ) = repository.uploadVideo(video, title, description, timeInMillis)
}