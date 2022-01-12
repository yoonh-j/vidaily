package com.yoond.vidaily.data

data class VideoMinimal(
    val key: String,
    val title: String,
    val username: String,
    val views: Int,
    val profileUrl: String,
    val videoUrl: String
) {
    override fun toString() = "$key, $title $username $views \n$profileUrl\n$videoUrl"
}
