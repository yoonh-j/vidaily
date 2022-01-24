package com.yoond.vidaily.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yoond.vidaily.data.AuthRepository

class AuthViewModel: ViewModel() {
    private val repository = AuthRepository()

    fun signUp(email: String, pwd: String) =
        repository.signUp(email, pwd)

    fun confirmSignUp(email: String, code: String): LiveData<Boolean> =
        repository.confirmSignUp(email, code)

    fun login(email: String, pwd: String): LiveData<Boolean> =
        repository.login(email, pwd)

    fun logout() =
        repository.logout()

    fun createPushToken(token: String) =
        repository.createPushToken(token)

    fun updatePushToken(pushToken: String) =
        repository.updatePushToken(pushToken)

}