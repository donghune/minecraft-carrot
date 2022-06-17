package io.github.donghune.entity

import io.github.donghune.api.manager.Config
import io.github.donghune.api.manager.ConfigManager
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bukkit.inventory.ItemStack

@Serializable
data class BingoConfig(
    val gamePlayTime: Int = 60 * 5,
    val specialTime: Int = 60 * 1,
    val items: MutableList<@Contextual ItemStack> = mutableListOf(),
) : Config("BingoConfig") {
    override fun update() {
        BingoConfigManager.update(this)
    }
}

object BingoConfigManager : ConfigManager<BingoConfig>(
    "", BingoConfig.serializer(), BingoConfig()
)