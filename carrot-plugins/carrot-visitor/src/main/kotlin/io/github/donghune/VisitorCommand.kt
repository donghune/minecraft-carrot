package io.github.donghune

import io.github.donghune.api.kommand.argument.integer
import io.github.donghune.api.kommand.kommand
import io.github.donghune.entity.VisitorConfigManager
import io.github.donghune.entity.visitorConfig
import io.github.donghune.inventory.SalesItemManagerInventory
import io.github.donghune.inventory.VisitorMarkersInventory

object VisitorCommand {
    fun initialize(plugin: VisitorPlugin) {
        plugin.kommand {
            register("손님") {
                then("시작") {
                    executes {
                        VisitorManager.start()
                    }
                }
                then("종료") {
                    executes {
                        VisitorManager.stop()
                    }
                }
                then("아이템") {
                    executes {
                        SalesItemManagerInventory().open(it.player)
                    }
                }
                then("특별손님확률") {
                    then("숫자" to integer()) {
                        executes {
                            val number: Int = it.parseArgument("숫자")

                            visitorConfig.specialGuestChance = number
                            it.player.sendMessage("특별손님 등장 확률을 $number 로 수정하였습니다.")
                        }
                    }
                }
                then("장소") {
                    executes {
                        visitorConfig.markers.add(it.player.location)
                        visitorConfig.update()
                        it.player.sendMessage("해당 위치로 마커를 추가하였습니다.")
                    }
                }
                then("목록") {
                    executes {
                        VisitorMarkersInventory().open(it.player)
                    }
                }
                then("등장시간") {
                    then("time" to integer()) {
                        executes {
                            val time: Int = it.parseArgument("time")

                            visitorConfig.guestAppearanceTime = time
                            visitorConfig.update()

                            it.player.sendMessage("등장시간 을 $time (초) 로 수정하였습니다.")
                        }
                    }
                }
                then("유지시간") {
                    then("time" to integer()) {
                        executes {
                            val time: Int = it.parseArgument("time")

                            visitorConfig.guestRetentionTime = time
                            visitorConfig.update()

                            it.player.sendMessage("유지시간 을 $time (초) 로 수정하였습니다.")
                        }
                    }
                }

                then("종료시간") {
                    then("time" to integer()) {
                        executes {
                            val time: Int = it.parseArgument("time")

                            visitorConfig.playTime = time
                            visitorConfig.update()

                            it.player.sendMessage("종료시간 을 $time (초) 로 수정하였습니다.")
                        }
                    }
                }
                then("리로드") {
                    executes {
                        VisitorConfigManager.reload()
                        it.player.sendMessage("손님 시스템을 리로드 하였습니다.")
                    }
                }
            }
            register("visitor") {
                then("start") {
                    executes {
                        VisitorManager.start()
                    }
                }
                then("stop") {
                    executes {
                        VisitorManager.stop()
                    }
                }
                then("item") {
                    executes {
                        SalesItemManagerInventory().open(it.player)
                    }
                }
                then("special-visitor-chance") {
                    then("number" to integer()) {
                        executes {
                            val number: Int = it.parseArgument("number")

                            visitorConfig.specialGuestChance = number
                            it.player.sendMessage("특별손님 등장 확률을 $number 로 수정하였습니다.")
                        }
                    }
                }
                then("marker") {
                    executes {
                        visitorConfig.markers.add(it.player.location)
                        visitorConfig.update()
                        it.player.sendMessage("해당 위치로 마커를 추가하였습니다.")
                    }
                }
                then("list") {
                    executes {
                        VisitorMarkersInventory().open(it.player)
                    }
                }
                then("appearance-time") {
                    then("time" to integer()) {
                        executes {
                            val time: Int = it.parseArgument("time")

                            visitorConfig.guestAppearanceTime = time
                            visitorConfig.update()

                            it.player.sendMessage("등장시간 을 $time (초) 로 수정하였습니다.")
                        }
                    }
                }
                then("retention-time") {
                    then("time" to integer()) {
                        executes {
                            val time: Int = it.parseArgument("time")

                            visitorConfig.guestRetentionTime = time
                            visitorConfig.update()

                            it.player.sendMessage("유지시간 을 $time (초) 로 수정하였습니다.")
                        }
                    }
                }

                then("play-time") {
                    then("time" to integer()) {
                        executes {
                            val time: Int = it.parseArgument("time")

                            visitorConfig.playTime = time
                            visitorConfig.update()

                            it.player.sendMessage("종료시간 을 $time (초) 로 수정하였습니다.")
                        }
                    }
                }
                then("reload") {
                    executes {
                        VisitorConfigManager.reload()
                        it.player.sendMessage("손님 시스템을 리로드 하였습니다.")
                    }
                }
            }
        }
    }
}