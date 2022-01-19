package com.yoond.vidaily.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify

class AuthRepository {

    fun signUp(email: String, pwd: String) {
        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), email)
            .build()
        Amplify.Auth.signUp(
            email,
            pwd,
            options,
            { Log.d("AUTH_REPOSITORY", "signUp succeeded: $it") },
            { Log.e("AUTH_REPOSITORY", "signUp failed", it) })
    }

    fun confirmSignUp(email: String, code: String): LiveData<Boolean> {
        val isSignedUp = MutableLiveData<Boolean>(false)

        Amplify.Auth.confirmSignUp(
            email,
            code,
            { result ->
                if (result.isSignUpComplete) {
                    isSignedUp.postValue(true)
                }
                Log.i("AUTH_REPOSITORY", "signup suceeded")
            },
            { Log.e("AUTH_REPOSITORY", "signup failed:", it)}
        )
        return isSignedUp
    }

    fun login(email: String, pwd: String): LiveData<Boolean> {
        val isLoggedIn = MutableLiveData<Boolean>(false)

        Amplify.Auth.signIn(email, pwd,
            { result ->
                if (result.isSignInComplete) {
                    Log.i("AUTH_REPOSITORY", "login succeeded")
                    isLoggedIn.postValue(true)
                }
            },
            { Log.e("AUTH_REPOSITORY", "login failed:", it) }
        )
        return isLoggedIn
    }
}