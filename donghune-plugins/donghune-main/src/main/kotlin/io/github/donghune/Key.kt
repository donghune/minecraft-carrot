package io.github.donghune


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Key(
    @SerialName("door")
    val door: Int?,
    @SerialName("id")
    val id: Int?,
    @SerialName("location")
    val location: Int?
)