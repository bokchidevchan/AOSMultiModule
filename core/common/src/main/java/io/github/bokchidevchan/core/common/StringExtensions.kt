package io.github.bokchidevchan.core.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    return this.matches(emailRegex)
}

fun String.capitalizeFirst(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}

fun String.truncate(maxLength: Int, suffix: String = "..."): String {
    return if (this.length <= maxLength) this
    else this.take(maxLength - suffix.length) + suffix
}

fun Long.toFormattedDate(pattern: String = "yyyy-MM-dd HH:mm"): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this))
}

fun Long.toRelativeTime(): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    return when {
        diff < 60_000 -> "방금 전"
        diff < 3_600_000 -> "${diff / 60_000}분 전"
        diff < 86_400_000 -> "${diff / 3_600_000}시간 전"
        diff < 604_800_000 -> "${diff / 86_400_000}일 전"
        else -> toFormattedDate("yyyy.MM.dd")
    }
}
