package io.github.donghune

import io.github.donghune.api.extensions.mainWorld
import io.github.donghune.api.manager.Config
import io.github.donghune.api.manager.ConfigManager
import kotlinx.serialization.Serializable
import org.bukkit.World
import org.bukkit.entity.Chicken
import org.bukkit.entity.Entity

val regenConfig = RegenConfigManager.get()

@Serializable
data class RegenConfig(
    val world: World = mainWorld,
    val hive: Int = 20,
    val sheep: Int = 20,
    val chicken: Int = 20,
    val cow: Int = 20,
) : Config("RegenConfig") {
    override fun update() {
        RegenConfigManager.update(this)
    }
}

object RegenConfigManager : ConfigManager<RegenConfig>(
    "", RegenConfig.serializer(), RegenConfig()
) {
    val cows = mutableListOf<Entity>()
    val chickens = mutableListOf<Chicken>()
}