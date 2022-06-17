package io.github.donghune.api

import io.github.donghune.api.mccoroutine.SuspendingPlugin
import io.github.donghune.api.mccoroutine.mcCoroutine
import kotlinx.coroutines.runBlocking
import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin: JavaPlugin

open class DHEPlugin : JavaPlugin(), SuspendingPlugin {
    override suspend fun onEnableAsync() {
    }

    override suspend fun onDisableAsync() {
    }

    override suspend fun onLoadAsync() {
    }

    override fun onEnable() {
        plugin = this

        mcCoroutine.getCoroutineSession(this).isManipulatedServerHeartBeatEnabled = true
        runBlocking {
            onEnableAsync()
        }
        // Disables runBlocking hack to not interfere with other tasks.
        mcCoroutine.getCoroutineSession(this).isManipulatedServerHeartBeatEnabled = false
    }

    override fun onDisable() {
        runBlocking {
            onDisableAsync()
        }
    }

    override fun onLoad() {
        runBlocking {
            onLoadAsync()
        }
    }
}