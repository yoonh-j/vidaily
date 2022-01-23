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

        val user = User.builder()
            .username(username)
            .follower(mutableListOf<String>())
            .following(mutableListOf<String>())
            .id(uid)
            .build()

        Amplify.API.mutate(
            ModelMutation.create(user),
            { Log.i("USER_REPOSITORY", "createUser success: $it") },
            { Log.e("USER_REPOSITORY", "createUser failed: ", it) }
        )
    }

    fun createFollower(fId: String): LiveData<Boolean> {
        val isDone = MutableLiveData<Boolean>()
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

        // 여기서 fId를 가진 유저의 follower 목록 갱신
        Amplify.API.query(
            ModelQuery.get(User::class.java, fId),
            { following ->
                if (following.hasData() && following.data.follower != null) {
                    following.data.follower.add(uid)

                    Amplify.API.mutate(
                        ModelMutation.update(following.data),
                        { isDone.postValue(true) },
                        {
                            isDone.postValue(false)
                            Log.e("USER_REPOSITORY", "updateFollower failed: ", it)
                        }
                    )
                }
                Log.i("USER_REPOSITORY", "createFollower succeeded: ${following.data.follower}")
            },
            { Log.e("USER_REPOSITORY", "getFollowing failed: ", it) }
        )
        return isDone
    }

    fun deleteFollower(fId: String): LiveData<Boolean> {
        val isDone = MutableLiveData<Boolean>()
        // 여기서 curUser의 following 목록 갱신
        val uid = Amplify.Auth.currentUser.userId

        Amplify.API.query(
            ModelQuery.get(User::class.java, uid),
            { response ->
                if (response.hasData() && response.data.following != null) {
                    response.data.following.remove(fId)
                    Amplify.API.mutate(
                        ModelMutation.update(response.data),
                        { Log.i("USER_REPOSITORY", "deleteFollowing succeeded: $it") },
                        { Log.e("USER_REPOSITORY", "deleteFollowing failed: ", it) }
                    )
                }
                Log.i("USER_REPOSITORY", "deleteFollowing succeeded: ${response.data.following}")
            },
            { Log.e("USER_REPOSITORY", "deleteFollowing failed: ", it) }
        )
        // fId를 가진 user의 follower 목록 갱신
        Amplify.API.query(
            ModelQuery.get(User::class.java, fId),
            { following ->
                if (following.hasData() && following.data.follower != null) {
                    following.data.follower.remove(uid)

                    Amplify.API.mutate(
                        ModelMutation.update(following.data),
                        { isDone.postValue(true) },
                        {
                            isDone.postValue(false)
                            Log.e("USER_REPOSITORY", "deleteFollower failed: ", it)
                        }
                    )
                }
                Log.i("USER_REPOSITORY", "deleteFollower succeeded: ${following.data.follower}")
            },
            { Log.e("USER_REPOSITORY", "getFollowing failed: ", it) }
        )
        return isDone
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
     * returns a list of follower/following users
     * @param uId: 팔로워 목록을 가져오고자 하는 유저
     * @return following 목록에 uId가 있는 유저 목록
     */
    fun getFollowers(uId: String): LiveData<MutableList<UserItem>> {
        val followerList = MutableLiveData<MutableList<UserItem>>()

        // uId가 following 하는 유저 목록
        Amplify.API.query(ModelQuery.list(User::class.java, User.FOLLOWING.contains(uId)),
            { followers ->
                if (followers.hasData()) {
                    Log.i("USER_REPOSITORY", "getFollowers succeeded: $followers")
                    val list = mutableListOf<UserItem>()

                    followers.data.items.forEach { item ->
                        if (item != null) {
                            list.add(UserItem(
                                item.id,
                                item.username,
                                item.following,
                                item.follower,
                                ""
                            ))
                        }
                    }
                    followerList.postValue(list)
                }
            },
            { Log.e("USER_REPOSITORY", "getFollowers failed", it) }
        )
        return followerList
    }

    /**
     * returns a list of follower/following users
     * @param uId: 팔로잉 목록을 가져오고자 하는 유저
     * @return follower 목록에 uId가 있는 유저 목록
     */
    fun getFollowings(uId: String): LiveData<MutableList<UserItem>> {
        val followingList = MutableLiveData<MutableList<UserItem>>()

        // uId를 follow하는 유저 목록
        Amplify.API.query(ModelQuery.list(User::class.java, User.FOLLOWER.contains(uId)),
            { following ->
                if (following.hasData()) {
                    Log.i("USER_REPOSITORY", "getFollowings succeeded: $following")
                    val list = mutableListOf<UserItem>()

                    following.data.items.forEach { item ->
                        if (item != null) {
                            list.add(UserItem(
                                item.id,
                                item.username,
                                item.following,
                                item.follower,
                                ""
                            ))
                        }
                    }
                    followingList.postValue(list)
                }
            },
            { Log.e("USER_REPOSITORY", "getFollowings failed", it) }
        )
        return followingList
    }
}