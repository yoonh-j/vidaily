package com.yoond.vidaily.data

import android.util.Log
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import java.io.File

class UserRepository {

    /**
     * uploads profile image in storage
     * if succeeded, creates user in db
     */
    fun uploadUser(
        profileImage: File,
        username: String
    ) {
        val uid = Amplify.Auth.currentUser.userId

        Amplify.Storage.uploadFile(
            "profiles/$uid",
            profileImage,
            {
                createUser(username)
                Log.d("USER_REPOSITORY", "uploadProfileImage success: ${it.key}")
            },
            { Log.e("USER_REPOSITORY", "uploadProfileImage failed", it)}
        )
    }

    /**
     * creates user in db
     */
    private fun createUser(username: String) {
        val uid = Amplify.Auth.currentUser.userId

        Amplify.Storage.getUrl("profiles/$uid",
            { result ->
                val url = result.url.toString()
                val user = User.builder()
                    .username(username)
                    .profileUrl(url)
                    .following(0)
                    .follower(0)
                    .id(uid)
                    .build()

                Amplify.API.mutate(
                    ModelMutation.create(user),
                    { Log.i("UPLOAD_USER", "success: $it") },
                    { Log.e("UPLOAD_USER", "failed: ", it) }
                )
            },
            {}
        )
    }
}