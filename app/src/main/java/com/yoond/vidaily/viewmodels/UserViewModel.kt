package com.yoond.vidaily.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.amplifyframework.datastore.generated.model.User
import com.yoond.vidaily.data.UserItem
import com.yoond.vidaily.data.UserRepository
import java.io.File

class UserViewModel: ViewModel() {
    private val repository = UserRepository()

    fun uploadUser(profileImage: File, username: String) =
        repository.uploadUser(profileImage, username)

    fun createFollower(fId: String): LiveData<Boolean> =
        repository.createFollower(fId)

    fun deleteFollower(fId: String): LiveData<Boolean> =
        repository.deleteFollower(fId)

    fun getUser(uid: String): LiveData<User> =
        repository.getUser(uid)

    fun getFollowers(uId: String): LiveData<MutableList<UserItem>> =
        repository.getFollowers(uId)

    fun getFollowings(uId: String): LiveData<MutableList<UserItem>> =
        repository.getFollowings(uId)
}