package io.github.donghune


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Size(
    @SerialName("height")
    val height: Int?,
    @SerialName("width")
    val width: Int?
)