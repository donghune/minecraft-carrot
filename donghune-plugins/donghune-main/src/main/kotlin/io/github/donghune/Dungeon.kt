package io.github.donghune


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dungeon(
    @SerialName("doors")
    val doors: List<Door>?,
    @SerialName("keys")
    val keys: List<Key>?,
    @SerialName("rooms")
    val rooms: List<Room>?,
    @SerialName("size")
    val size: Size?,
    @SerialName("terminals")
    val terminals: Terminals?
)