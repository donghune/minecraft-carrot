package io.github.donghune

import io.github.donghune.api.kommand.argument.integer
import io.github.donghune.api.kommand.kommand
import io.github.donghune.entity.BingoConfigManager
import io.github.donghune.inventory.BingoItemRemoveInventory
import io.github.donghune.scheduler.BingoCoroutineScheduler
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

val playerByGauge = mutableMapOf<Player, Int>()

var Player.gauge: Int
    get() = playerByGauge[this] ?: 0
    set(value) {
        playerByGauge[this] = value
    }

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
                then("time") {
                    then("add") {
                        then("time" to integer()) {
                            executes {
                                val time: Int = it.parseArgument("time")
                                BingoCoroutineScheduler.addTime(time)
                            }
                        }
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
                            BingoConfigManager.get().update()
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