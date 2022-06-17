package io.github.donghune.api.extensions

import org.bukkit.Bukkit

// logger
object Log {
    fun info(message: String) = Bukkit.getLogger().info(message)
    fun warn(message: String) = Bukkit.getLogger().warning(message)
    fun severe(message: String) = Bukkit.getLogger().severe(message)
    fun fine(message: String) = Bukkit.getLogger().fine(message)
}
