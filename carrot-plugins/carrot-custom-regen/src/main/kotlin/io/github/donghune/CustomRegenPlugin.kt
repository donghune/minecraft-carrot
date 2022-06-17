package io.github.donghune

import io.github.donghune.api.DHEPlugin
import io.github.donghune.api.mccoroutine.KCoroutineScheduler
import org.bukkit.Bukkit
import org.bukkit.entity.Chicken

class CustomRegenPlugin : DHEPlugin() {
    override fun onEnable() {
        super.onEnable()
        Bukkit.getPluginManager().registerEvents(CustomRegenListener(), this)
        CustomRegenCommand.initialize(this)

        val kCoroutineScheduler = KCoroutineScheduler()
        kCoroutineScheduler.onDuringSec {
            Bukkit.getWorld("world")!!.entities
                .filterIsInstance(Chicken::class.java)
                .filter { !it.isCustomNameVisible }
                .forEach {
                    ChickenRegenScheduler(it).start(RegenConfigManager.get().chicken)
                }
        }
        kCoroutineScheduler.start(Int.MAX_VALUE / 20)
    }

    override fun onDisable() {
        super.onDisable()
    }
}