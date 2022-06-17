package io.github.donghune.scheduler

import io.github.donghune.api.KSchduler
import io.github.donghune.entity.visitorConfig
import io.github.donghune.util.toTimeFormat
import io.github.donghune.visitorPlugin
import org.bukkit.Bukkit
import org.bukkit.boss.BossBar
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

object VisitorTimeBarScheduler : KSchduler {

    override val plugin: JavaPlugin
        get() = visitorPlugin

    private lateinit var timer: BukkitTask
    private var leftTime: Int = 0

    lateinit var bossBar: BossBar

    override fun start() {
        leftTime = visitorConfig.gameTime
        bossBar = Bukkit.createBossBar(
            leftTime.toTimeFormat(), visitorConfig.bossBarTimer.barColor, visitorConfig.bossBarTimer.barStyle
        )
        Bukkit.getOnlinePlayers().forEach { bossBar.addPlayer(it) }
        timer = Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            Bukkit.getOnlinePlayers().filter { !bossBar.players.contains(it) }.forEach { bossBar.addPlayer(it) }
            leftTime -= 1
            bossBar.setTitle(leftTime.toTimeFormat())
        }, 0L, 20L)
    }

    override fun stop() {
        timer.cancel()
        Bukkit.getOnlinePlayers().forEach { bossBar.removePlayer(it) }
    }

}