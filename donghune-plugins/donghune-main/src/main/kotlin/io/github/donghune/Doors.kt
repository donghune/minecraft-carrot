package io.github.donghune


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Doors(
    @SerialName("e")
    val e: Int?,
    @SerialName("n")
    val n: Int?,
    @SerialName("s")
    val s: Int?,
    @SerialName("w")
    val w: Int?
)