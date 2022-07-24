package io.github.donghune.scheudler

import io.github.donghune.VisitorManager
import io.github.donghune.api.extensions.Title
import io.github.donghune.api.extensions.chatColor
import io.github.donghune.api.extensions.sendTitle
import io.github.donghune.api.mccoroutine.KBukkitScheduler
import io.github.donghune.entity.visitorConfig
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle

class GameTimer : KBukkitScheduler() {

    private val bossBar = Bukkit.createBossBar("", BarColor.GREEN, BarStyle.SOLID)

    init {
        onStart {
            Bukkit.getOnlinePlayers().sendTitle {
                it.playSound(it.location, Sound.BLOCK_BELL_USE, 0.1f, 1f)
                Title(
                    title = visitorConfig.openingMessage,
                    subTitle = "",
                    fadeIn = 0,
                    time = 20,
                    fadeOut = 0
                )
            }
            visitorConfig.markers.forEach {
                VisitorManager.spawn(it)
            }
        }
        onDuringSec {
            bossBar.setTitle("&a남은시간 ${"%02d".format(leftSec / 60)}:${"%02d".format(leftSec % 60)}".chatColor())
            Bukkit.getOnlinePlayers().forEach { bossBar.addPlayer(it) }
        }
        onStop {
            Bukkit.getOnlinePlayers().sendTitle {
                it.playSound(it.location, Sound.BLOCK_IRON_DOOR_CLOSE, 1f, 1f)
                Title(
                    title = visitorConfig.closingMessage,
                    subTitle = "",
                    fadeIn = 0,
                    time = 20,
                    fadeOut = 0
                )
            }
            listOf(*VisitorManager.getVisitors().toTypedArray()).forEach {
                VisitorManager.remove(it)
            }
            bossBar.removeAll()
        }
    }
}