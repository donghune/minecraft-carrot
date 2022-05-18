package io.github.donghune


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Room(
    @SerialName("distance")
    val distance: Int?,
    @SerialName("doors")
    val doors: Doors?,
    @SerialName("entrance")
    val entrance: Boolean?,
    @SerialName("exit")
    val exit: Boolean?,
    @SerialName("id")
    val id: Int?,
    @SerialName("keyInRoom")
    val keyInRoom: Int?,
    @SerialName("template")
    val template: String?,
    @SerialName("x")
    val x: Int?,
    @SerialName("y")
    val y: Int?
)