package io.github.donghune

import io.github.donghune.api.mccoroutine.KBukkitScheduler
import kotlinx.coroutines.cancel
import org.bukkit.Bukkit
import org.bukkit.entity.Chicken

object ChickenCheckerScheduler : KBukkitScheduler() {
    init {
        val scheduler = mutableListOf<ChickenRegenScheduler>()
        onStart {
            scheduler.clear()
            RegenConfigManager.chickens.clear()
        }
        onDuringSec {
            Bukkit.getWorld("world")!!.entities
                .filter { !RegenConfigManager.chickens.contains(it) }
                .filterIsInstance(Chicken::class.java)
                .forEach {
                    val chickenRegenScheduler = ChickenRegenScheduler(it)
                    scheduler.add(chickenRegenScheduler)
                    chickenRegenScheduler.start(RegenConfigManager.get().chicken)
                }
        }
        onStop {
            scheduler.forEach { it.cancel() }
            RegenConfigManager.chickens.clear()
        }
    }
}