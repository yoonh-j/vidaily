package com.yoond.vidaily.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.datastore.generated.model.User
import com.yoond.vidaily.data.UserRepository
import java.io.File

class UserViewModel: ViewModel() {
    private val repository = UserRepository()

    fun uploadUser(profileImage: File, username: String) =
        repository.uploadUser(profileImage, username)

    fun createFollowing(fId: String) =
        repository.createFollowing(fId)

    fun createFollower(fId: String) =
        repository.createFollower(fId)

    fun getUser(uid: String): LiveData<User> =
        repository.getUser(uid)

    fun getFollowers(fIds: List<String>): LiveData<MutableList<User>> =
        repository.getFollowers(fIds)

    fun getFollowings(fIds: List<String>): LiveData<MutableList<User>> =
        repository.getFollowings(fIds)
}