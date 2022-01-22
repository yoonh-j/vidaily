package com.yoond.vidaily.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoItem (
    val id: String,
    val title: String,
    val description: String,
    val views: Int,
    val likes: Int,
    val createdAt: String,
    val uid: String,
    val username: String,
    var videoUrl: String,
    var profileUrl: String
) : Parcelable {
    override fun toString(): String =
        "$title \nvideoUrl: $videoUrl\nprofileUrl: $profileUrl"
}

