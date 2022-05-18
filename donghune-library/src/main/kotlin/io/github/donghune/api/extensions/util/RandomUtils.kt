package io.github.donghune.api.extensions.util

import kotlin.random.Random

fun <E> Collection<E>.randomOrNull(): E? = runCatching { random() }.getOrNull()
fun <E> Collection<E>.randomIndex(): Int = if(size > 0) Random.nextInt(size) else -1

fun <E> Collection<E>.randomize(): MutableList<E> {
    val oldList = toMutableList()
    val newList = mutableListOf<E>()

    for(i in 0 until size) {
        val index = oldList.randomIndex()
        newList.add(oldList[index])
        oldList.removeAt(index)
    }

    return newList
}