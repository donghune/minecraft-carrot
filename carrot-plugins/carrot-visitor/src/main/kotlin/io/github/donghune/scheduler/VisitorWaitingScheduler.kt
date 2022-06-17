package io.github.donghune.scheduler

import io.github.donghune.VisitorManager
import io.github.donghune.api.KSchduler
import io.github.donghune.api.extensions.chatColor
import io.github.donghune.entity.VisitorConfigManager
import io.github.donghune.entity.visitorConfig
import io.github.donghune.util.toTimeFormat
import org.bukkit.Bukkit
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Villager
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

class VisitorWaitingScheduler(override val plugin: JavaPlugin, private val villager: Villager) : KSchduler {

    companion object {
        val armorStands = mutableListOf<ArmorStand>()
    }

    private var hitAroundTask: BukkitTask? = null
    private var armorStandMoveTask: BukkitTask? = null
    private var stopWatchTask: BukkitTask? = null

    private var leftTimeEntity: ArmorStand? = null

    var leftTime = visitorConfig.visitorOption.waitDelay

    override fun start() {
        val period = VisitorConfigManager.get().visitorOption.waitDelay.toLong()
        hitAroundTask = Bukkit.getScheduler().runTaskLater(plugin, hitAroundRunnable, period * 20)
        armorStandMoveTask = Bukkit.getScheduler().runTaskTimer(plugin, armorStandMoveRunnable, 0, 1L)
        stopWatchTask = Bukkit.getScheduler().runTaskTimer(plugin, stopWatchRunnable, 0, 20L)
        spawnTimeEntity()
    }

    private val hitAroundRunnable = Runnable {
        VisitorManager.hitTheLoad(villager)
    }

    private val armorStandMoveRunnable = Runnable {
        leftTimeEntity?.teleport(villager)
    }

    private val stopWatchRunnable = Runnable {
        leftTime -= 1
        setCustomNameToLeftTime()
    }

    override fun stop() {
        hitAroundTask?.cancel()
        armorStandMoveTask?.cancel()
        stopWatchTask?.cancel()
        leftTimeEntity?.remove()
    }

    private fun spawnTimeEntity() {
        leftTimeEntity = (villager.world.spawnEntity(
            villager.location.clone().add(0.0, 0.5, 0.0),
            EntityType.ARMOR_STAND
        ) as ArmorStand).apply {
            isVisible = false
            setGravity(false)
            customName = "&c${leftTime.toTimeFormat()}".chatColor()
            isCustomNameVisible = true
            isCollidable = false
        }.also { armorStands.add(it) }
    }

    private fun setCustomNameToLeftTime() {
        leftTimeEntity?.customName = "&c${leftTime.toTimeFormat()}".chatColor()
    }

}