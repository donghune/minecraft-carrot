package io.github.donghune

import io.github.donghune.api.kommand.kommand
import org.bukkit.block.data.type.Beehive

object CustomRegenCommand {
    fun initialize(plugin: CustomRegenPlugin) {
        plugin.kommand {
            register("cregen") {
                then("honey") {
                    executes {
                        val block = it.player.getTargetBlock(100)
                        val hive = (block?.blockData as Beehive)
                        hive.honeyLevel = hive.maximumHoneyLevel
                        block.blockData = hive
                    }
                }
            }
        }
    }
}