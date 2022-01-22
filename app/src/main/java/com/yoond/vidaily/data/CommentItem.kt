package com.yoond.vidaily.data

data class CommentItem(
    val id: String,
    val content: String,
    val createdAt: String,
    val vId: String,
    val uId: String,
    val username: String,
    var profileUrl: String
) {
    override fun toString(): String =
        "$id $content \n$profileUrl"
}
