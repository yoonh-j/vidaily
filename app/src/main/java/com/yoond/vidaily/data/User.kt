package com.yoond.vidaily.data

data class User(
    val uid: String,
    val username: String,
    val following: Int,
    val follower: Int,
    val videoUrls: List<String>
)
