package com.yoond.vidaily.viewmodels

import androidx.lifecycle.ViewModel
import com.yoond.vidaily.data.StorageRepository
import java.io.File

class StorageViewModel: ViewModel() {
    private val repository = StorageRepository()

    fun getAllVideos() =
        repository.getAllVideos()

    fun uploadVideo(key: String, video: File) =
        repository.uploadVideo(key, video)
}