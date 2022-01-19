package com.yoond.vidaily.utils

import java.util.*

const val DAY_IN_MILLIS = 86400000L

fun getTodayStartInMillis(): Long {
    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)

    return cal.timeInMillis
}