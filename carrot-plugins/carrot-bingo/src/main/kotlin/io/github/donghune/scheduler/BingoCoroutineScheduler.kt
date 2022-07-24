package io.github.donghune.scheduler

import io.github.donghune.BingoManager
import io.github.donghune.api.TranslateManager
import io.github.donghune.api.extensions.Title
import io.github.donghune.api.extensions.sendActionBar
import io.github.donghune.api.extensions.sendTitle
import io.github.donghune.api.mccoroutine.ExpandableKBukkitScheduler
import io.github.donghune.api.mccoroutine.KBukkitScheduler
import io.github.donghune.api.resourceKey
import io.github.donghune.entity.BingoConfigManager
import org.bukkit.Bukkit
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.RenderType

object BingoCoroutineScheduler : ExpandableKBukkitScheduler() {
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
            Bukkit.getOnlinePlayers().forEach {
                val scoreboardManager = Bukkit.getScoreboardManager() ?: return@forEach
                val scoreboard = scoreboardManager.newScoreboard

                val objective = scoreboard.registerNewObjective("biome", "dummy", "바이옴", RenderType.INTEGER)
                objective.displaySlot = DisplaySlot.SIDEBAR

                val score = objective.getScore(
                    TranslateManager.translate(
                        "biome.${it.location.block.biome.key.namespace}.${it.location.block.biome.key.key}"
                    ) ?: ""
                )
                score.score = 1
                it.scoreboard = scoreboard
            }
        }
        onStop {
            SpecialTimeScheduler.stop()
            BingoManager.printRank()
            BingoManager.resetAll()
        }
    }
}

