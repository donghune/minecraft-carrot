package io.github.donghune

import io.github.donghune.api.BasePlugin
import org.bukkit.Bukkit

class FishGamePlugin : BasePlugin() {
    override fun onEnable() {
        super.onEnable()
        Bukkit.getPluginManager().registerEvents(FishGameListener(), this)
    }
}