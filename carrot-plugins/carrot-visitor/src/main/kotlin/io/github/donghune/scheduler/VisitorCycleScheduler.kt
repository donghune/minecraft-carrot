package io.github.donghune.scheduler

import io.github.donghune.VisitorManager
import io.github.donghune.VisitorManager.removeWithPassengers
import io.github.donghune.api.KSchduler
import io.github.donghune.api.extensions.chatColor
import io.github.donghune.entity.visitorConfig
import io.github.donghune.visitorPlugin
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

object VisitorCycleScheduler : KSchduler {

    override val plugin: JavaPlugin
        get() = visitorPlugin

    private lateinit var timer: BukkitTask

    override fun start() {
        Bukkit.getOnlinePlayers()
            .forEach { it.sendTitle(visitorConfig.messages.startMessage.chatColor(), "", 10, 20, 10) }
        VisitorManager.reset()
        VisitorNavigationScheduler.start()
        if (visitorConfig.bossBarTimer.isVisible) {
            VisitorTimeBarScheduler.start()
        }

        timer = Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            stop()
        }, visitorConfig.gameTime * 20L)
    }

    override fun stop() {
        Bukkit.getOnlinePlayers()
            .forEach { it.sendTitle(visitorConfig.messages.endMessage.chatColor(), "", 10, 20, 10) }
        VisitorNavigationScheduler.stop()
        if (visitorConfig.bossBarTimer.isVisible) {
            VisitorTimeBarScheduler.stop()
        }
    }

}