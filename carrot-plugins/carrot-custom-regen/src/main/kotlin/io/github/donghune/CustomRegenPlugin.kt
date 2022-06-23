package io.github.donghune

import io.github.donghune.api.BasePlugin
import org.bukkit.Bukkit

class CustomRegenPlugin : BasePlugin() {
    override fun onEnable() {
        super.onEnable()
        Bukkit.getPluginManager().registerEvents(CustomRegenListener(), this)
        CustomRegenCommand.initialize(this)
    }

    override fun onDisable() {
        super.onDisable()
    }
}