package io.github.donghune.api.extensions

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.Plugin
import kotlin.reflect.KClass

inline fun <reified T : Event> KListener<*>.event(
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = false,
        noinline block: T.() -> Unit
) = event(plugin, priority, ignoreCancelled, block)

fun <T : Event> KListener<*>.event(
    type: KClass<T>,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    block: T.() -> Unit
) = event(plugin, type, priority, ignoreCancelled, block)

inline fun <reified T : Event> Listener.event(
        plugin: Plugin,
        priority: EventPriority = EventPriority.NORMAL,
        ignoreCancelled: Boolean = false,
        noinline block: T.() -> Unit
) {
    event<T>(plugin, T::class, priority, ignoreCancelled, block)
}

fun <T : Event> Listener.event(
    plugin: Plugin,
    type: KClass<T>,
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    block: T.() -> Unit
) {
    Bukkit.getServer().pluginManager.registerEvent(
        type.java,
        this,
        priority,
        { _, event ->
            if(type.isInstance(event))
                (event as? T)?.block()
        },
        plugin,
        ignoreCancelled
    )
}

inline fun WithPlugin<*>.events(block: KListener<*>.() -> Unit) = plugin.events(block)
inline fun Plugin.events(block: KListener<*>.() -> Unit) = SimpleKListener(this).apply(block)

fun Listener.registerEvents(plugin: Plugin)
        = plugin.server.pluginManager.registerEvents(this, plugin)

fun Listener.unregisterListener() = HandlerList.unregisterAll(this)

fun Event.callEvent() = Bukkit.getServer().pluginManager.callEvent(this)

val PlayerMoveEvent.displaced: Boolean
    get() = this.from.x != this.to!!.x || this.from.y != this.to!!.y || this.from.z != this.to!!.z

interface KListener<T : Plugin> : Listener, WithPlugin<T>

class SimpleKListener(override val plugin: Plugin) : KListener<Plugin>