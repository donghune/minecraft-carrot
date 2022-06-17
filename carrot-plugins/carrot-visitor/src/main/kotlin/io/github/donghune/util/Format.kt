package io.github.donghune.util

val format = "%02d:%02d"

fun Int.toTimeFormat(): String {
    return format.format(this / 60, this % 60)
}