package io.github.donghune.api

import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin : JavaPlugin

open class DHEPlugin : JavaPlugin() {
    override fun onEnable() {
        plugin = this
    }
}