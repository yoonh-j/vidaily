package com.yoond.vidaily.data

import com.amplifyframework.datastore.generated.model.Video

data class VideoItem (
    val video: Video,
    var videoUrl: String,
    var profileUrl: String
) {
    override fun toString(): String =
        "${video.title} \nvideoUrl: $videoUrl\nprofileUrl: $profileUrl"
}

