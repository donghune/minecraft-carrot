package io.github.donghune.scheudler

import io.github.donghune.api.extensions.chatColor
import io.github.donghune.api.mccoroutine.KBukkitScheduler
import org.bukkit.entity.ArmorStand

class VisitorTimer(
    private val armorStand: ArmorStand,
    private val onFinish: () -> Unit
) : KBukkitScheduler() {
    init {
        onStart {

        }
        onDuringSec {
            armorStand.customName = "&c${"%02d".format(leftSec / 60)}:${"%02d".format(leftSec % 60)}".chatColor()
        }
        onStop {
            onFinish()
        }
    }
}