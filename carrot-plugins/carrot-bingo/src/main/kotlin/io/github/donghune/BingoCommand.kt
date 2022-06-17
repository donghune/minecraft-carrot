package io.github.donghune

import io.github.donghune.api.kommand.kommand
import io.github.donghune.entity.BingoConfigManager
import io.github.donghune.inventory.BingoItemRemoveInventory
import io.github.donghune.scheduler.BingoCoroutineScheduler
import org.bukkit.Material

object BingoCommand {
    fun initialize(plugin: BingoPlugin) {
        plugin.kommand {
            register("bingo") {
                then("start") {
                    executes {
                        BingoCoroutineScheduler.start(BingoConfigManager.get().gamePlayTime)
                    }
                }
                then("stop") {
                    executes {
                        BingoCoroutineScheduler.stop()
                    }
                }
                then("item") {
                    then("add") {
                        executes {
                            val player = it.player

                            if (player.inventory.itemInMainHand.type == Material.AIR) {
                                player.sendMessage("손에 아이템이 없습니다.")
                                return@executes
                            }

                            BingoConfigManager.get().items.add(player.inventory.itemInMainHand)
                            player.sendMessage("아이템을 등록하였습니다.")
                        }
                    }
                    then("remove") {
                        executes {
                            BingoItemRemoveInventory().open(it.player)
                        }
                    }
                }
            }
        }
    }
}