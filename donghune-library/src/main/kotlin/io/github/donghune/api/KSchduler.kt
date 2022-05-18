package io.github.donghune.api

import io.github.donghune.api.extensions.WithPlugin
import org.bukkit.plugin.java.JavaPlugin

interface KSchduler : WithPlugin<JavaPlugin> {
    fun start()
    fun stop()
}