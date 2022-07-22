package io.github.donghune

import io.github.donghune.api.BasePlugin
import org.bukkit.Bukkit

lateinit var bingoPlugin: BingoPlugin

class BingoPlugin : BasePlugin() {
    override fun onEnable() {
        super.onEnable()
        bingoPlugin = this
        BingoCommand.initialize(this)
        Bukkit.getPluginManager().registerEvents(BingoListener(), this)
    }
}

