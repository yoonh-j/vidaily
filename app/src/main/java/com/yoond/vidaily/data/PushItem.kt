package com.yoond.vidaily.data

data class PushItem(
    var to: String? = null,
    var notification: Notification = Notification()
) {
    data class Notification(
        var body: String? = null,
        var title: String? = null
    )
}
