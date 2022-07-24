package io.github.donghune.scheduler

import io.github.donghune.api.mccoroutine.ExpandableKBukkitScheduler
import io.github.donghune.api.mccoroutine.KBukkitScheduler
import io.github.donghune.api.translate
import io.github.donghune.entity.BingoConfigManager
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.inventory.ItemStack

object SpecialTimeScheduler : ExpandableKBukkitScheduler() {

    var specialItem: ItemStack? = null
    private val bossBar =
        Bukkit.createBossBar("특별 이벤트! [${specialItem?.translate}] 를 찾아라!", BarColor.BLUE, BarStyle.SOLID)

    init {
        onStart {
            specialItem = BingoConfigManager.get().items.random()
            println(specialItem)
            bossBar.setTitle("특별 이벤트! [${specialItem?.translate}] 를 찾아라!")
            Bukkit.getOnlinePlayers().forEach { bossBar.addPlayer(it) }
        }
        onDuringSec {
            Bukkit.getOnlinePlayers().forEach { bossBar.addPlayer(it) }
            bossBar.progress = (leftTime / totalTime.toDouble())
        }
        onStop {
            specialItem = null
            Bukkit.getOnlinePlayers().forEach { bossBar.removePlayer(it) }
        }
    }
}