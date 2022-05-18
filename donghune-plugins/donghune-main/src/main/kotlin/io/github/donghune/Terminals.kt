package io.github.donghune


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Terminals(
    @SerialName("deadends")
    val deadends: List<Int>?,
    @SerialName("entrance")
    val entrance: Int?,
    @SerialName("exit")
    val exit: Int?
)