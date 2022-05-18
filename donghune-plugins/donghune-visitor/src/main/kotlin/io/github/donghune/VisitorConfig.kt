package io.github.donghune

import io.github.donghune.api.extensions.emptyLocation
import io.github.donghune.api.manager.Config
import io.github.donghune.api.manager.ConfigManager
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object VisitorConfigManager : ConfigManager<VisitorConfig>(
    "", VisitorConfig.serializer(), VisitorConfig()
)

@Serializable
data class VisitorConfig(
    var startLocation: @Contextual Location = emptyLocation(),
    var spawnDelay: Int = 20,
    var items: List<@Contextual ItemStack> = emptyList(),
    val teams: MutableList<VisitorTeam> = mutableListOf()
) : Config("VisitorConfig") {
    override fun update() {
        VisitorConfigManager.update(this)
    }
}

@Serializable
data class VisitorTeam(
    val name: String = "", var anchorLocation: @Contextual Location = emptyLocation()
) {
    val playerList: MutableList<Player> = mutableListOf()
}

