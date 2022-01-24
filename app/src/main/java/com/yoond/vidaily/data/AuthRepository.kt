package com.yoond.vidaily.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.PushToken

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

    fun logout() {
        Amplify.Auth.signOut(
            { Log.i("AUTH_REPOSITORY", "logout succeeded") },
            { Log.e("AUTH_REPOSITORY", "logout failed", it) }
        )
    }

    /**
     * creates push token in db
     */
    fun createPushToken(pushToken: String) {
        val uid = Amplify.Auth.currentUser.userId
        val token = PushToken.builder()
            .token(pushToken)
            .id(uid)
            .build()
        Amplify.API.mutate(ModelMutation.create(token),
            { Log.i("USER_REPOSITORY", "createPushToken succeeded, $it") },
            { Log.e("USER_REPOSITORY", "createPushToken failed", it) }
        )
    }

    fun updatePushToken(pushToken: String) {
        val uid = Amplify.Auth.currentUser.userId
        val token = PushToken.builder()
            .token(pushToken)
            .id(uid)
            .build()

        Amplify.API.mutate(
            ModelMutation.update(token),
            { Log.i("USER_REPOSITORY", "updatePushToken succeeded: $it") },
            { Log.e("USER_REPOSITORY", "updatePushToken failed", it) }
        )
    }
}