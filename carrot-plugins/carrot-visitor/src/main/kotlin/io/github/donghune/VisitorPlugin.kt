package io.github.donghune

import io.github.donghune.VisitorManager.removeWithPassengers
import io.github.donghune.api.DHEPlugin
import io.github.donghune.api.extensions.mainWorld
import io.github.donghune.entity.VisitorConfig
import io.github.donghune.entity.VisitorConfigManager
import io.github.donghune.entity.visitorConfig
import io.github.donghune.scheduler.VisitorWaitingScheduler
import org.bukkit.Difficulty
import org.bukkit.GameRule
import org.bukkit.plugin.java.JavaPlugin

lateinit var visitorPlugin: JavaPlugin

class VisitorPlugin : DHEPlugin() {
    override fun onEnable() {
        super.onEnable()
        visitorPlugin = this

        VisitorConfigManager.get()
        VisitorCommand.initialize(this)
        VisitorListener.initialize(this)
    }

    override fun onDisable() {
        super.onDisable()
        VisitorManager.getVisitors().forEach { it.first.removeWithPassengers() }
        VisitorWaitingScheduler.armorStands.forEach { it.remove() }
    }
}

