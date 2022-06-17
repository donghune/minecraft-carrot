package io.github.donghune

import io.github.donghune.api.PagingList
import io.github.donghune.api.displayName
import io.github.donghune.api.displayNameOrLocaleMaterial
import io.github.donghune.api.extensions.toPrettyString
import io.github.donghune.api.kommand.argument.*
import io.github.donghune.api.kommand.kommand
import io.github.donghune.argument.visitorTeam
import io.github.donghune.entity.*
import io.github.donghune.scheduler.VisitorCycleScheduler
import org.bukkit.Material
import org.bukkit.entity.Player

object VisitorCommand {
    fun initialize(plugin: VisitorPlugin) {
        plugin.kommand {
            register("visitor") {
                executes {
                    it.sender.sendMessage(
                        """
                        /visitor start - 손님 시스템을 시작
                        /visitor stop - 손님 시스템을 중단
                        /visitor config - 시스템 설정 명령어
                        /visitor team - 팀 관리 명령어
                    """.trimIndent()
                    )
                }
                then("start") {
                    executes {
                        VisitorCycleScheduler.start()
                    }
                }
                then("stop") {
                    executes {
                        VisitorCycleScheduler.stop()
                    }
                }
                then("config") {
                    executes {
                        it.sender.sendMessage(
                            """
                            /visitor config locations - 위치를 설정합니다.
                            /visitor config gameTime <time> - 게임 시간을 설정합니다 (초)
                            /visitor config visitorOption - 손님을 수정합니다.
                            /visitor config bossBarTimer - 보스바를 수정합니다.
                            /visitor config messages - 메시지를 수정합니다.
                        """.trimIndent()
                        )
                    }
                    then("locations") {
                        executes {
                            it.sender.sendMessage(
                                """
                                /visitor config locations startLocation - 현재 위치를 주민 생성위치로 지정합니다.
                                /visitor config locations endLocation - 현재 위치를 주민 퇴장위치로 지정합니다.
                            """.trimIndent()
                            )
                        }
                        then("startLocation") {
                            executes {
                                VisitorConfigManager.get().apply {
                                    locations.startLocation = it.player.location
                                }.update()
                            }
                        }
                        then("endLocation") {
                            executes {
                                VisitorConfigManager.get().apply {
                                    locations.endLocation = it.player.location
                                }.update()
                            }
                        }
                    }
                    then("gameTime") {
                        then("time" to integer()) {
                            executes {
                                VisitorConfigManager.get().apply {
                                    gameTime = it.parseArgument("time")
                                }
                            }
                        }
                    }
                    then("visitorOption") {
                        executes {
                            it.sender.sendMessage(
                                """
                                /visitor config visitorOption spawnDelay <delay> - 손님의 생성 주기를 설정합니다 (초)
                                /visitor config visitorOption waitDelay <delay> - 손님의 대기시간을 설정합니다 (초)
                                /visitor config visitorOption walkSpeed <speed> - 손님의 이동속도를 설정합니다 (1이 엄청 빠름 [소수점])
                                /visitor config visitorOption items - 손님의 아이템을 설정합니다.
                            """.trimIndent()
                            )
                        }
                        then("spawnDelay") {
                            then("delay" to integer()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        visitorOption.spawnDelay = it.parseArgument("delay")
                                    }.update()
                                }
                            }
                        }
                        then("waitDelay") {
                            then("delay" to integer()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        visitorOption.waitDelay = it.parseArgument("delay")
                                    }.update()
                                }
                            }
                        }
                        then("walkSpeed") {
                            then("speed" to double()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        visitorOption.walkSpeed = it.parseArgument("speed")
                                    }.update()
                                }
                            }
                        }
                        then("items") {
                            executes {
                                it.sender.sendMessage(
                                    """
                                    /visitor config visitorOption items list - 손님의 아이템 목록을 확인합니다.
                                    /visitor config visitorOption items add <max> <price> - 손님의 아이템을 설정합니다.
                                    /visitor config visitorOption items remove <index> - 손님의 아이템을 삭제.
                                """.trimIndent()
                                )
                            }
                            then("list") {
                                then("page" to integer()) {
                                    executes {
                                        val pagingList = PagingList(10, VisitorConfigManager.get().visitorOption.items)
                                        val page = pagingList.getPage(it.parseArgument("page"))
                                        page.forEach { visitorItem ->
                                            it.sender.sendMessage("[%s] C:%d E:%d".format(
                                                visitorItem.itemStack.localizedName,
                                                visitorItem.maxAmount,
                                                visitorItem.price
                                            ))
                                        }
                                    }
                                }
                            }
                            then("add") {
                                then("max" to integer()) {
                                    then("price" to integer()) {
                                        executes {
                                            VisitorConfigManager.get().apply {
                                                visitorOption.items.add(
                                                    VisitorItem(
                                                        it.player.inventory.itemInMainHand,
                                                        it.parseArgument("max"),
                                                        it.parseArgument("price")
                                                    )
                                                )
                                            }.update()
                                        }
                                    }
                                }
                            }
                            then("remove") {
                                then("index" to integer()) {
                                    executes {
                                        VisitorConfigManager.get().apply {
                                            visitorOption.items.removeAt(it.parseArgument("index"))
                                        }
                                    }
                                }
                            }
                        }
                    }
                    then("bossBarTimer") {
                        executes {
                            it.sender.sendMessage(
                                """
                                /visitor config bossBarTimer isVisible <value> - 보스바 출력 여부를 선택합니다. [true,false]
                                /visitor config bossBarTimer barColor <value> - 보스바 색상을 설정합니다.
                                /visitor config bossBarTimer barStyle <value> - 보스바 스타일을 설정합니다.
                            """.trimIndent()
                            )
                        }
                        then("isVisible") {
                            then("value" to bool()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        bossBarTimer.isVisible = it.parseArgument("value")
                                    }.update()
                                }
                            }
                        }
                        then("barColor") {
                            then("value" to bossBarColor()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        bossBarTimer.barColor = it.parseArgument("value")
                                    }.update()
                                }
                            }
                        }
                        then("barStyle") {
                            then("value" to bossBarStyle()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        bossBarTimer.barStyle = it.parseArgument("value")
                                    }.update()
                                }
                            }
                        }
                    }
                    then("messages") {
                        executes {
                            it.sender.sendMessage(
                                """
                                /visitor config messages tradeSuccessMessage - 거래성공 메시지를 설정합니다.
                                /visitor config messages tradeSuccessType <type> - 거래성공 출력 위치를 설정합니다.
                                /visitor config messages hitAroundMessage 거래시간만료 메시지를 설정합니다.
                                /visitor config messages hitAroundType <type> - 거래시간만료 출력 위치를 설정합니다.
                                /visitor config messages tradeFailMessage <message> - 거래실패 메시지를 설정합니다.
                                /visitor config messages tradeFailType <type> - 거래실패 출력 위치를 설정합니다.
                                /visitor config messages startMessage <message> - 게임시작 메시지를 설정합니다.
                                /visitor config messages endMessage <message> - 게임종료 메시지를 설정합니다.
                            """.trimIndent()
                            )
                        }
                        then("tradeSuccessMessage") {
                            executes {
                                it.sender.sendMessage(
                                    """
                                    /visitor config messages tradeSuccessMessage add <message> - 거래 성공 메시지를 추가합니다.
                                    /visitor config messages tradeSuccessMessage remove <index> - 거래 성공 메시지를 삭제합니다.
                                """.trimIndent()
                                )
                            }
                            then("add") {
                                then("message" to string()) {
                                    executes {
                                        VisitorConfigManager.get().apply {
                                            messages.tradeSuccessMessage.add(it.parseArgument("message"))
                                        }.update()
                                    }
                                }
                            }
                            then("remove") {
                                then("index" to integer()) {
                                    executes {
                                        VisitorConfigManager.get().apply {
                                            messages.tradeSuccessMessage.removeAt(it.parseArgument("index"))
                                        }.update()
                                    }
                                }
                            }
                        }
                        then("tradeSuccessType") {
                            then("type" to chatMessageType()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        messages.tradeSuccessType = it.parseArgument("type")
                                    }.update()
                                }
                            }
                        }
                        then("hitAroundMessage") {
                            executes {
                                it.sender.sendMessage(
                                    """
                                    /visitor config messages hitAroundMessage add <message> - 거래시간만료 메시지를 추가합니다.
                                    /visitor config messages hitAroundMessage remove <index> - 거래시간만료 메시지를 삭제합니다.
                                """.trimIndent()
                                )
                            }
                            then("add") {
                                then("message" to string()) {
                                    executes {
                                        VisitorConfigManager.get().apply {
                                            messages.tradeSuccessMessage.add(it.parseArgument("message"))
                                        }.update()
                                    }
                                }
                            }
                            then("remove") {
                                then("index" to integer()) {
                                    executes {
                                        VisitorConfigManager.get().apply {
                                            messages.tradeSuccessMessage.removeAt(it.parseArgument("index"))
                                        }.update()
                                    }
                                }
                            }
                        }
                        then("hitAroundType") {
                            then("type" to chatMessageType()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        messages.tradeSuccessType = it.parseArgument("type")
                                    }.update()
                                }
                            }
                        }
                        then("tradeFailMessage") {
                            then("message" to string()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        messages.tradeFailMessage = it.parseArgument("message")
                                    }.update()
                                }
                            }
                        }
                        then("tradeFailType") {
                            then("type" to chatMessageType()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        messages.tradeFailType = it.parseArgument("type")
                                    }.update()
                                }
                            }
                        }
                        then("startMessage") {
                            then("message" to string()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        messages.startMessage = it.parseArgument("message")
                                    }.update()
                                }
                            }
                        }
                        then("endMessage") {
                            then("message" to string()) {
                                executes {
                                    VisitorConfigManager.get().apply {
                                        messages.endMessage = it.parseArgument("message")
                                    }.update()
                                }
                            }
                        }
                    }
                }
                then("comboReward") {
//                    from
//                    to
//                    count
                }
                then("team") {
                    executes {
                        it.sender.sendMessage(
                            """
                            /visitor team status - 팀 상태보기
                            /visitor team create <teamName> - 팀 생성
                            /visitor team delete <teamName> - 팀 삭제
                            /visitor team anchor <teamName> - 팀 주민이 설 위치 설정
                            /visitor team player add <teamName> <playerName> - 플레이어 추가
                            /visitor team player remove <teamName> <playerName> - 플레이어 추방
                        """.trimIndent()
                        )
                    }
                    then("status") {
                        executes {
                            showTeamInfo(it.player)
                        }
                    }
                    then("create") {
                        then("teamName" to string()) {
                            executes {
                                createTeam(
                                    player = it.player,
                                    teamName = it.parseArgument("teamName")
                                )
                            }
                        }
                    }
                    then("delete") {
                        then("teamName" to visitorTeam()) {
                            executes {
                                deleteTeam(
                                    player = it.player,
                                    team = it.parseArgument("teamName")
                                )
                            }
                        }
                    }
                    then("anchor") {
                        then("mark") {
                            then("teamName" to visitorTeam()) {
                                then("blockType" to map(Material.values().filter { it.isBlock }
                                    .associateBy { it.name })) {
                                    executes { context ->
                                        val visitorItem: VisitorTeam = context.parseArgument("teamName")
                                        visitorItem.anchorLocations.forEach {
                                            it.clone().apply {
                                                add(0.0, -1.0, 0.0)
                                            }.block.type = context.parseArgument("blockType")
                                        }
                                    }
                                }
                            }
                        }
                        then("add") {
                            then("teamName" to visitorTeam()) {
                                executes {
                                    addTeamAnchor(
                                        player = it.player,
                                        team = it.parseArgument("teamName")
                                    )
                                }
                            }
                        }
                        then("remove") {
                            then("teamName" to visitorTeam()) {
                                then("position" to integer()) {
                                    executes {
                                        removeTeamAnchor(
                                            player = it.player,
                                            team = it.parseArgument("teamName"),
                                            position = it.parseArgument("position")
                                        )
                                    }
                                }
                            }
                        }
                        then("edit") {
                            then("teamName" to visitorTeam()) {
                                then("position" to integer()) {
                                    executes {
                                        editTeamAnchor(
                                            player = it.player,
                                            team = it.parseArgument("teamName"),
                                            position = it.parseArgument("position")
                                        )
                                    }
                                }
                            }
                        }
                    }
                    then("player") {
                        then("add") {
                            then("teamName" to visitorTeam()) {
                                then("playerName" to player()) {
                                    executes {
                                        addPlayerToTeam(
                                            player = it.player,
                                            team = it.parseArgument("teamName"),
                                            target = it.parseArgument("playerName")
                                        )
                                    }
                                }
                            }
                        }
                        then("remove") {
                            then("teamName" to visitorTeam()) {
                                then("playerName" to player()) {
                                    executes {
                                        removePlayerToTeam(
                                            player = it.player,
                                            team = it.parseArgument("teamName"),
                                            target = it.parseArgument("playerName")
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showTeamInfo(player: Player) {
        val visitorConfig = VisitorConfigManager.get()

        visitorConfig.teams.forEach {
            player.sendMessage(it.name)
            player.sendMessage("players : ${it.playerList.joinToString(", ") { player -> player.toString() }}")
            player.sendMessage("anchor : ${it.anchorLocations.joinToString(", ") { anchor -> anchor.toPrettyString() }}")
        }
    }

    private fun createTeam(player: Player, teamName: String) {
        val visitorConfig = VisitorConfigManager.get()

        if (visitorConfig.teams.find { it.name == teamName } != null) {
            player.sendMessage("이미 존재하는 팀 이름 입니다.")
            return
        }

        visitorConfig.teams.add(VisitorTeam(teamName, mutableListOf()))
        visitorConfig.update()
        player.sendMessage("[$teamName] 팀을 추가하였습니다.")
    }

    private fun deleteTeam(player: Player, team: VisitorTeam) {
        val visitorConfig = VisitorConfigManager.get()
        visitorConfig.teams.removeIf { it.name == team.name }
        visitorConfig.update()
        player.sendMessage("[${team.name}] 팀을 삭제하였습니다.")
    }

    private fun addTeamAnchor(player: Player, team: VisitorTeam) {
        team.anchorLocations.add(player.location.block.location)
        visitorConfig.update()
        player.sendMessage("[${team.name}] 팀의 앵커위치를 수정하였습니다..")
    }

    private fun removeTeamAnchor(player: Player, team: VisitorTeam, position: Int) {
        team.anchorLocations.removeAt(position)
        visitorConfig.update()
        player.sendMessage("[${team.name}] 팀의 앵커위치를 수정하였습니다..")
    }

    private fun editTeamAnchor(player: Player, team: VisitorTeam, position: Int) {
        team.anchorLocations.set(position, player.location.block.location)
        visitorConfig.update()
        player.sendMessage("[${team.name}] 팀의 앵커위치를 수정하였습니다..")
    }

    private fun addPlayerToTeam(player: Player, team: VisitorTeam, target: Player) {
        visitorConfig.teams.forEach { it.playerList.remove(target.uniqueId) }
        team.playerList.add(target.uniqueId)
        player.sendMessage("[${team.name}] 팀에 [${target.name} 를 추가하였습니다.]")
    }

    private fun removePlayerToTeam(player: Player, team: VisitorTeam, target: Player) {
        visitorConfig.teams.forEach { it.playerList.remove(target.uniqueId) }
        team.playerList.remove(target.uniqueId)
        player.sendMessage("[${team.name}] 팀에 [${target.name} 를 삭제하였습니다.]")
    }
}