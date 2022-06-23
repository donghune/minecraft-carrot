package io.github.donghune.scheduler

import io.github.donghune.BingoManager
import io.github.donghune.api.extensions.Title
import io.github.donghune.api.mccoroutine.KBukkitScheduler
import io.github.donghune.api.translate
import io.github.donghune.entity.BingoConfigManager
import io.github.donghune.api.extensions.sendActionBar
import io.github.donghune.api.extensions.sendTitle
import io.github.donghune.api.inventory.updateGUI
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.inventory.ItemStack

object BingoCoroutineScheduler : KBukkitScheduler() {
    init {
        onStart {
            BingoManager.createAll()
            Bukkit.getOnlinePlayers().sendTitle { Title("빙고 시작!", "[Shift] + [F] 를 눌러 빙고판을 확인 할 수 있습니다.") }
        }
        onDuringSec { sec ->
            Bukkit.getOnlinePlayers().sendActionBar { "%d:%02d".format(sec / 60, sec % 60) }
            if (sec != totalTime && sec != 0 && sec % BingoConfigManager.get().specialTime == 0) {
                if (SpecialTimeScheduler.isRunning) {
                    SpecialTimeScheduler.stop()
                }
                SpecialTimeScheduler.start(BingoConfigManager.get().specialTime)
            }
        }
        onStop {
            SpecialTimeScheduler.stop()
            BingoManager.printRank()
            BingoManager.resetAll()
        }
    }
}

object SpecialTimeScheduler : KBukkitScheduler() {

    var specialItem: ItemStack? = null
    private val bossBar =
        Bukkit.createBossBar("특별 이벤트! [${specialItem?.translate}] 를 찾아라!", BarColor.BLUE, BarStyle.SOLID)

    init {
        onStart {
            specialItem = BingoConfigManager.get().items.random()
            bossBar.setTitle("특별 이벤트! [${specialItem?.translate}] 를 찾아라!")
            Bukkit.getOnlinePlayers().updateGUI()
            Bukkit.getOnlinePlayers().forEach { bossBar.addPlayer(it) }
        }
        onDuringSec {
            Bukkit.getOnlinePlayers().forEach { bossBar.addPlayer(it) }
            bossBar.progress = (leftSec / totalTime.toDouble())
        }
        onStop {
            specialItem = null
            Bukkit.getOnlinePlayers().updateGUI()
            Bukkit.getOnlinePlayers().forEach { bossBar.removePlayer(it) }
        }
    }
}