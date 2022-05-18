package io.github.donghune

import io.github.donghune.api.KSchduler
import io.github.donghune.api.extensions.mainWorld
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftVillager
import org.bukkit.entity.Villager
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class VisitorScheduler(override val plugin: JavaPlugin) : KSchduler {

    private lateinit var timer: Timer

    override fun start() {
        val period = VisitorConfigManager.get().spawnDelay.toLong()
        Bukkit.getScheduler().runTaskTimer(plugin, visitorRunnable, 0, period)
        Bukkit.getScheduler().runTaskTimer(plugin, navigationRunnable, 0, 1L)
    }

    private val navigationRunnable = Runnable {
        VisitorManager.getVisitors().forEach { (visitor, location) -> visitor.navigation(location) }
    }

    private val visitorRunnable = Runnable {
        val world = mainWorld
        val config = VisitorConfigManager.get()

        // 주민 생성
        VisitorManager.spawnVillagerOnStartLocation(config, world)

        // 주민 배치
        VisitorManager.assignVillagerToTeam(config)
    }

    override fun stop() {
        VisitorManager.getVisitors().map { it.first }.forEach { it.remove() }
        timer.cancel()
    }

    private fun Villager.navigation(location: Location) {
        val entityLiving = (this as CraftVillager).handle
        val pathEntity = entityLiving.D().a(location.x, location.y, location.z, 1)
        entityLiving.D().a(pathEntity, 1.0)
    }

}