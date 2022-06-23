package io.github.donghune.api.extensions

import net.md_5.bungee.api.ChatMessageType
import org.bukkit.entity.Player

fun Collection<Player>.sendActionBar(block: (Player) -> (String)) {
    forEach {
        it.spigot().sendMessage(ChatMessageType.ACTION_BAR, block(it).toComponent())
    }
}

class Title(
    val title: String = "",
    val subTitle: String = "",
    val fadeIn: Int = 20,
    val time: Int = 20,
    val fadeOut: Int = 20,
)

fun Collection<Player>.sendTitle(block: (Player) -> (Title)) {
    forEach {
        val title = block(it)
        it.sendTitle(title.title, title.subTitle, title.fadeIn, title.time, title.fadeOut)
    }
}