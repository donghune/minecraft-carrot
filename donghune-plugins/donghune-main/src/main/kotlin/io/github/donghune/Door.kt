package io.github.donghune


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Door(
    @SerialName("exit")
    val exit: Boolean?,
    @SerialName("id")
    val id: Int?,
    @SerialName("key")
    val key: Int?,
    @SerialName("orientation")
    val orientation: String?,
    @SerialName("rooms")
    val rooms: List<Int>?
)