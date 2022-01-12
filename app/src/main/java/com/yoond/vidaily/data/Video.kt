package com.yoond.vidaily.data

data class Video (
    val key: String,
    val uid: String,
    val url: String,
    val title: String,
    val description: String,
    val timeInMillis: Long,
    val views: Int,
    val likes: Int
) {
    override fun toString() = "$key $uid \n $url \n $title \n $description \n $timeInMillis $views $likes"
}