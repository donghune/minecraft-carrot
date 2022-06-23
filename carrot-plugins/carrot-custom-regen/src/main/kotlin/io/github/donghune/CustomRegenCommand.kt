package io.github.donghune

import io.github.donghune.api.kommand.kommand
import org.bukkit.block.data.type.Beehive

object CustomRegenCommand {
    fun initialize(plugin: CustomRegenPlugin) {
        plugin.kommand {
            register("cregen") {
                then("start") {
                    executes {
                        ChickenCheckerScheduler.start(Int.MAX_VALUE / 20)
                    }
                }
                then("stop") {
                    executes {
                        ChickenCheckerScheduler.stop()
                    }
                }
                then("honey") {
                    executes {
                        val block = it.player.getTargetBlockExact(100) ?: return@executes
                        val hive = (block.blockData as Beehive)
                        hive.honeyLevel = hive.maximumHoneyLevel
                        block.blockData = hive
                    }
                }
            }
        }
    }
}