package io.github.donghune.scheduler

import io.github.donghune.VisitorManager
import io.github.donghune.VisitorManager.removeWithPassengers
import io.github.donghune.api.KSchduler
import io.github.donghune.api.extensions.mainWorld
import io.github.donghune.entity.visitorConfig
import io.github.donghune.visitorPlugin
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftVillager
import org.bukkit.entity.Villager
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

object VisitorNavigationScheduler : KSchduler {

    override val plugin: JavaPlugin
        get() = visitorPlugin

    private lateinit var visitorTask: BukkitTask
    private lateinit var navigationTask: BukkitTask

    override fun start() {
        val period = visitorConfig.visitorOption.spawnDelay.toLong()
        visitorTask = Bukkit.getScheduler().runTaskTimer(plugin, visitorRunnable, 0, period * 20L)
        navigationTask = Bukkit.getScheduler().runTaskTimer(plugin, navigationRunnable, 0, 1L)
    }

    private val navigationRunnable = Runnable {
        VisitorManager.getVisitors().forEach { (visitor, location) ->
            visitor.navigation(location)
            if (visitor.location.distance(location) <= 2) {
                VisitorManager.onArrival(visitor, location)
            }
        }
    }

    private val visitorRunnable = Runnable {
        val world = mainWorld

        // 주민 생성
        VisitorManager.spawnVillagerOnStartLocation(visitorConfig, world)

        // 주민 배치
        VisitorManager.assignVillagerToTeam(visitorConfig)
    }

    override fun stop() {
        VisitorManager.getVisitors().forEach { it.first.removeWithPassengers() }
        visitorTask.cancel()
        navigationTask.cancel()
    }

    private fun Villager.navigation(location: Location) {
        val entityLiving = (this as CraftVillager).handle
        val a = entityLiving.navigation.a(location.x, location.y, location.z, 1)
        entityLiving.navigation.a(a, visitorConfig.visitorOption.walkSpeed)
    }

}