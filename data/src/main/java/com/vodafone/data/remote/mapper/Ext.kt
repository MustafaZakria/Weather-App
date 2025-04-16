package com.vodafone.data.remote.mapper

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Int.asCurrentDate(): String {
    val time = Date(this.toLong() * 1000)

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time.time

    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return "" + dayName(calendar.timeInMillis) + ", " + day + " " + monthName(calendar.timeInMillis)
}

fun Int.asDailyDate(): String {
    val time = Date(this.toLong() * 1000)

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time.time

    val day = calendar.get(Calendar.DAY_OF_MONTH)

    return "" + dayName(prefix = "EEE", timeStamp = calendar.timeInMillis) + ", " + day
}

internal fun dayName(timeStamp: Long, prefix: String = "EEEE"): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeStamp
    val date = calendar.timeInMillis
    return SimpleDateFormat(prefix, Locale.ENGLISH).format(date)
}

internal fun monthName(timeStamp: Long): String? {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeStamp
    val date = calendar.timeInMillis
    return SimpleDateFormat("MMM", Locale.ENGLISH).format(date)
}

fun String.asFormattedIconUrl(): String {
    return "https:$this"
}