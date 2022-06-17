package io.github.donghune.api.extensions

import translateColor
import java.text.DecimalFormat

private val df = DecimalFormat("#,###")

fun Long.moneyFormat(colorCode: String = "&6"): String {
    return "$colorCode${df.format(this)}&f".translateColor()
}

fun Int.moneyFormat(colorCode: String = "&c6"): String {
    return "$colorCode${df.format(this)}&f".translateColor()
}