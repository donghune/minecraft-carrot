package io.github.donghune

import io.github.donghune.api.manager.Entity
import io.github.donghune.api.manager.EntityManager
import kotlinx.serialization.Serializable

@Serializable
data class PlayerMokoko(
    val uuid: String,
    val data: Map<Int, Boolean>
) : Entity(uuid)

fun PlayerMokoko.create() = PlayerMokokoManager.create(this)
fun PlayerMokoko.update() = PlayerMokokoManager.update(this)
fun PlayerMokoko.delete() = PlayerMokokoManager.delete(this)

object PlayerMokokoManager : EntityManager<PlayerMokoko>(
    "players",
    PlayerMokoko.serializer(),
    { PlayerMokoko(it, List(MokokoConfigManager.get().locations.size) { index -> index to false }.toMap()) }
)