package com.yoond.vidaily.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.generated.model.User
import java.io.File

/**
 * manages data related to users and following/followers
 */
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
                    .follower(mutableListOf<String>())
                    .following(mutableListOf<String>())
                    .id(uid)
                    .build()

                Amplify.API.mutate(
                    ModelMutation.create(user),
                    { Log.i("USER_REPOSITORY", "createUser success: $it") },
                    { Log.e("USER_REPOSITORY", "createUser failed: ", it) }
                )
            },
            { Log.e("USER_REPOSITORY", "createUser, getUrl failed", it) }
        )
    }

    fun createFollowing(fId: String) {
        // 여기서 curUser의 following 목록 갱신
        val uid = Amplify.Auth.currentUser.userId

        Amplify.API.query(
            ModelQuery.get(User::class.java, uid),
            { response ->
                if (response.hasData() && response.data.following != null) {
                    response.data.following.add(fId)
                    Amplify.API.mutate(
                        ModelMutation.update(response.data),
                        { Log.i("USER_REPOSITORY", "updateFollowing succeeded: $it") },
                        { Log.e("USER_REPOSITORY", "updateFollowing failed: ", it) }
                    )
                }
                Log.i("USER_REPOSITORY", "createFollowing succeeded: ${response.data.following}")
            },
            { Log.e("USER_REPOSITORY", "createFollowing failed: ", it) }
        )
    }

    fun createFollower(fId: String) {
        val uid = Amplify.Auth.currentUser.userId
        // 여기서 fId를 가진 유저의 follower 목록 갱신
        Amplify.API.query(
            ModelQuery.get(User::class.java, fId),
            { following ->
                if (following.hasData() && following.data.follower != null) {
                    following.data.follower.add(uid)

                    Amplify.API.mutate(
                        ModelMutation.update(following.data),
                        { Log.i("USER_REPOSITORY", "updateFollower succeeded: ${it.data.follower}") },
                        { Log.e("USER_REPOSITORY", "updateFollower failed: ", it) }
                    )
                }
                Log.i("USER_REPOSITORY", "createFollower succeeded: ${following.data.follower}")
            },
            { Log.e("USER_REPOSITORY", "getFollowing failed: ", it) }
        )
    }

    fun getUser(uid: String): LiveData<User> {
        val u = MutableLiveData<User>()

        Amplify.API.query(ModelQuery.get(User::class.java, uid),
            { response ->
                if (response.hasData()) {
                    u.postValue(response.data)
                }
                Log.i("USER_REPOSITORY", "getUser succeeded: $response")
            },
            { Log.d("USER_REPOSITORY", "getUser failed: ", it) }
        )
        return u
    }

    /**
     * returns a list of follower users
     * @param fIds: a list of the current user's follower ids
     */
    fun getFollowers(fIds: List<String>): LiveData<MutableList<User>> {
        val followerList = MutableLiveData<MutableList<User>>()

        fIds.forEach { fId ->
            Amplify.API.query(ModelQuery.list(User::class.java, User.ID.contains(fId)),
                { following ->
                    if (following.hasData()) {
                        Log.i("USER_REPOSITORY", "getFollowers succeeded: $following")
                        val list = mutableListOf<User>()

                        following.data.items.forEach { item ->
                            if (item != null) {
                                list.add(item)
                            }
                        }
                        followerList.postValue(list)
                    }
                },
                { Log.e("USER_REPOSITORY", "getFollowers failed", it) }
            )
        }
        return followerList
    }

    /**
     * returns a list of following users
     * @param fIds: a list of the current user's following ids
     */
    fun getFollowings(fIds: List<String>): LiveData<MutableList<User>> {
        val followingList = MutableLiveData<MutableList<User>>()

        fIds.forEach { fId ->
            Amplify.API.query(ModelQuery.list(User::class.java, User.ID.contains(fId)),
                { following ->
                    if (following.hasData()) {
                        Log.i("USER_REPOSITORY", "getFollowings succeeded: $following")
                        val list = mutableListOf<User>()

                        following.data.items.forEach { item ->
                            if (item != null) {
                                list.add(item)
                            }
                        }
                        followingList.postValue(list)
                    }
                },
                { Log.e("USER_REPOSITORY", "getFollowing failed", it) }
            )
        }
        return followingList
    }
}