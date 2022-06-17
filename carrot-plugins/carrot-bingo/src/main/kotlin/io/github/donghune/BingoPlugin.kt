package io.github.donghune

import io.github.donghune.api.DHEPlugin
import org.bukkit.Bukkit

lateinit var bingoPlugin: BingoPlugin

class BingoPlugin : DHEPlugin() {
    override fun onEnable() {
        super.onEnable()
        bingoPlugin = this
        BingoCommand.initialize(this)
        Bukkit.getPluginManager().registerEvents(BingoListener(), this)
    }

    override fun onDisable() {
        super.onDisable()
    }
}

