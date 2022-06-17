package io.github.donghune

import io.github.donghune.api.manager.Config
import io.github.donghune.api.manager.ConfigManager
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Chicken
import org.bukkit.entity.Entity
import org.bukkit.entity.Sheep
import java.time.LocalDateTime

@Serializable
data class RegenConfig(
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
}