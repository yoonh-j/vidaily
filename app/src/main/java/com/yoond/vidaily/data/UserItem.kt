package com.yoond.vidaily.data

data class UserItem(
    val id: String,
    val username: String,
    val following: List<String>,
    val follower: List<String>,
    var profileUrl: String
) {
    override fun toString(): String =
        " $username id: $id follower: $follower, following: $following\n$profileUrl"
}
