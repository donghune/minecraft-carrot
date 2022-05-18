package io.github.donghune

import io.github.donghune.api.manager.Config
import io.github.donghune.api.manager.ConfigManager
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bukkit.Location

@Serializable
data class MokokoConfig(
    val locations: List<@Contextual Location>
) : Config("MokokoConfig") {
    override fun update() {
        MokokoConfigManager.update(this)
    }
}

object MokokoConfigManager : ConfigManager<MokokoConfig>(
    "", MokokoConfig.serializer(), MokokoConfig(emptyList())
)