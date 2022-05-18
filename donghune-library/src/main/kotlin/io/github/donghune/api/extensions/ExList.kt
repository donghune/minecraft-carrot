package io.github.donghune.api.extensions

fun <T> List<T>.copyOf(): List<T> {
    return mutableListOf<T>().also { it.addAll(this) }
}

fun <T> List<T>.mutableCopyOf(): MutableList<T> {
    return mutableListOf<T>().also { it.addAll(this) }
}