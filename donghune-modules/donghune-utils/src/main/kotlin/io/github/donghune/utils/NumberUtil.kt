package io.github.donghune.utils

fun Number.clearZero() = if (this.toInt().toDouble() == this) this.toInt() else this
