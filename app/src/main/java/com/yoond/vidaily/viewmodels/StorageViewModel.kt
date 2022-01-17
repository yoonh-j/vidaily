package com.yoond.vidaily.viewmodels

import androidx.lifecycle.ViewModel
import com.yoond.vidaily.data.StorageRepository
import java.io.File

class StorageViewModel: ViewModel() {
    private val repository = StorageRepository()

    fun getAllVideos() =
        repository.getAllVideos()

    fun getAllMetaData() =
        repository.getAllMetaData()

    fun uploadVideo(
        video: File,
        key: String,
        uid: String,
        title: String,
        description: String,
        timeInMillis: Long
    ) = repository.uploadVideo(video, key, uid, title, description, timeInMillis)
}