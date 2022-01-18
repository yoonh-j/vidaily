package com.yoond.vidaily.viewmodels

import androidx.lifecycle.ViewModel
import com.yoond.vidaily.data.UserRepository
import java.io.File

class UserViewModel: ViewModel() {
    private val repository = UserRepository()

    fun uploadUser(profileImage: File, username: String) =
        repository.uploadUser(profileImage, username)
}