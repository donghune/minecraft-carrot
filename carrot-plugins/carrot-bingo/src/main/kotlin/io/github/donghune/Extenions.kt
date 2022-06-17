package io.github.donghune

import net.kyori.adventure.bossbar.BossBar
import org.bukkit.Bukkit

fun BossBar.removeAll() {
    Bukkit.getOnlinePlayers().forEach {
        it.hideBossBar(this)
    }
}

fun BossBar.addAll() {
    Bukkit.getOnlinePlayers().forEach {
        it.showBossBar(this)
    }
}