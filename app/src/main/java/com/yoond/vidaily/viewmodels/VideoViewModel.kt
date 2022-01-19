package com.yoond.vidaily.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.datastore.generated.model.Metadata
import com.yoond.vidaily.data.VideoRepository
import java.io.File

class VideoViewModel: ViewModel() {
    private val repository = VideoRepository()

    fun getAllVideos() =
        repository.getAllVideos()

    fun getAllMetaData(): LiveData<MutableList<Metadata>> =
        repository.getAllMetaData()

    fun getMetadataByDate(): LiveData<MutableList<Metadata>> =
        repository.getMetadataByDate()

    fun getMetadataByViews(): LiveData<MutableList<Metadata>> =
        repository.getMetadataByViews()

    fun getMetadataByFollowing(fIds: List<String>): LiveData<MutableList<Metadata>> =
        repository.getMetadataByFollowing(fIds)

    fun uploadVideo(
        video: File,
        title: String,
        description: String,
        timeInMillis: Long
    ) = repository.uploadVideo(video, title, description, timeInMillis)
}