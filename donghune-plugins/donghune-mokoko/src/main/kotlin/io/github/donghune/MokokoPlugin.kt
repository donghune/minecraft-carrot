package io.github.donghune

import io.github.donghune.api.DHEPlugin
import io.github.donghune.api.extensions.registerEvents
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class MokokoPlugin : DHEPlugin() {
    override fun onEnable() {
        super.onEnable()
        MokokoCommand.init(this)
        registerEvents(object : Listener {
            @EventHandler
            fun a(event: PlayerJoinEvent) {
                PlayerMokokoManager.createDefault(event.player.uniqueId.toString())
            }
        })
    }
}

fun <T> List<T>.copyOf(): List<T> {
    return mutableListOf<T>().also { it.addAll(this) }
}

fun <T> List<T>.mutableCopyOf(): MutableList<T> {
    return mutableListOf<T>().also { it.addAll(this) }
}