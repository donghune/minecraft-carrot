package io.github.donghune

import io.github.donghune.api.BasePlugin
import org.bukkit.plugin.java.JavaPlugin

lateinit var visitorPlugin: JavaPlugin

class VisitorPlugin : BasePlugin() {
    override fun onEnable() {
        super.onEnable()
        visitorPlugin = this
        VisitorCommand.initialize(this)
        VisitorListener.initialize(this)
    }

    override fun onDisable() {
        super.onDisable()
        VisitorManager.stop()
    }
}