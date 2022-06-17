package io.github.donghune.scheduler

import io.github.donghune.BingoManager
import io.github.donghune.addAll
import io.github.donghune.api.extensions.toComponent
import io.github.donghune.api.mccoroutine.KCoroutineScheduler
import io.github.donghune.entity.BingoConfigManager
import io.github.donghune.removeAll
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.inventory.ItemStack


object BingoCoroutineScheduler : KCoroutineScheduler() {
    init {
        onStart {
            BingoManager.createAll()
        }
        onDuringSec { sec ->
            Bukkit.getOnlinePlayers().forEach {
                it.sendActionBar("%d:%02d".format(sec / 60, sec % 60).toComponent())
            }
            if (sec % 2 == 0) {
                SpecialTimeScheduler.stop()
                job += SpecialTimeScheduler.start(2)
            }
        }
        onStop {
            SpecialTimeScheduler.stop()
            BingoManager.printRank()
            BingoManager.resetAll()
        }
    }
}

object SpecialTimeScheduler : KCoroutineScheduler() {

    private val bossBar = BossBar.bossBar(Component.text(""), 1f, BossBar.Color.GREEN, BossBar.Overlay.PROGRESS)
    var isSpecialTime = false
    var specialItem: ItemStack? = null

    init {
        onStart {
            isSpecialTime = true
            specialItem = BingoConfigManager.get().items.random()
            Component.text("현재 ")
                .append(specialItem?.displayName() ?: Component.text(""))
                .append(Component.text(" 을 가져오시면 교환티켓으로 바꿔드립니다!"))
                .also { bossBar.name(it) }

            bossBar.addAll()
        }
        onStop {
            isSpecialTime = false
            bossBar.removeAll()
        }
    }
}