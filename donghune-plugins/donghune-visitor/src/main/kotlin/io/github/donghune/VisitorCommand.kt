package io.github.donghune

import io.github.donghune.api.extensions.emptyLocation
import io.github.donghune.api.extensions.toPrettyString
import io.github.monun.kommand.kommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object VisitorCommand {
    fun initialize(plugin: VisitorPlugin) {
        plugin.kommand {
            register("visitor") {
                executes {
                    player.sendMessage(
                        """
                        /config spawn_delay [value]
                        /config start_location
                        /team status
                        /team create [team_name]
                        /team delete [team_name]
                        /team anchor [team_name] 
                        /team player add [team_name] [player_name]
                        /team player delete [team_name] [player_name]
                    """.trimIndent()
                    )
                }
                then("start") {
                    executes {
                        VisitorScheduler(plugin).start()
                    }
                }
                then("config") {
                    then("item") {
                        executes {
                            VisitorInventory.open(player)
                        }
                    }
                    then("spawn_delay") {
                        then("value" to int()) {
                            executes { context ->
                                VisitorConfigManager.get().apply {
                                    spawnDelay = context["value"]
                                }.update()
                                player.sendMessage("생성 딜레이를 수정하였습니다.")
                            }
                        }
                    }
                    then("start_location") {
                        executes {
                            VisitorConfigManager.get().apply {
                                startLocation = player.location.toBlockLocation()
                            }.update()
                            player.sendMessage("시작 포인트를 설정하였습니다.")
                        }
                    }
                }
                then("team") {
                    then("status") {
                        executes {
                            showTeamInfo(player)
                        }
                    }
                    then("create") {
                        then("team_name" to string()) {
                            executes { context ->
                                createTeam(
                                    player, context["team_name"]
                                )
                            }
                        }
                    }
                    then("delete") {
                        then("team_name" to string()) {
                            executes { context ->
                                deleteTeam(
                                    player, context["team_name"]
                                )
                            }
                        }
                    }
                    then("anchor") {
                        then("team_name" to string()) {
                            executes { context ->
                                setTeamAnchor(
                                    player, context["team_name"]
                                )
                            }
                        }
                    }
                    then("player") {
                        then("add") {
                            then("team_name" to string()) {
                                then("player_name" to string()) {
                                    executes { context ->
                                        addPlayerToTeam(
                                            player, context["team_name"], context["player_name"]
                                        )
                                    }
                                }
                            }
                        }
                        then("delete") {
                            then("team_name" to string()) {
                                then("player_name" to string()) {
                                    executes { context ->
                                        removePlayerToTeam(
                                            player, context["team_name"], context["player_name"]
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
            player.sendMessage("players : ${it.playerList.joinToString(", ") { player -> player.name }}")
            player.sendMessage("anchor : ${it.anchorLocation.toPrettyString()}")
        }
    }

    private fun createTeam(player: Player, teamName: String) {
        val visitorConfig = VisitorConfigManager.get()

        if (visitorConfig.teams.find { it.name == teamName } != null) {
            player.sendMessage("이미 존재하는 팀 이름 입니다.")
            return
        }

        visitorConfig.teams.add(VisitorTeam(teamName, emptyLocation()))
        visitorConfig.update()
        player.sendMessage("[$teamName] 팀을 추가하였습니다.")
    }

    private fun deleteTeam(player: Player, teamName: String) {
        val visitorConfig = VisitorConfigManager.get()

        if (visitorConfig.teams.find { it.name == teamName } == null) {
            player.sendMessage("존재하지 않는 팀입니다.")
            return
        }

        visitorConfig.teams.removeIf { it.name == teamName }
        visitorConfig.update()
        player.sendMessage("[$teamName] 팀을 삭제하였습니다.")
    }

    private fun setTeamAnchor(player: Player, teamName: String) {
        val visitorConfig = VisitorConfigManager.get()
        val team = visitorConfig.teams.find { it.name == teamName }

        if (team == null) {
            player.sendMessage("존재하지 않는 팀입니다.")
            return
        }

        team.anchorLocation = player.location.toBlockLocation()
        visitorConfig.update()
        player.sendMessage("[$teamName] 팀의 앵커위치를 수정하였습니다..")
    }

    private fun addPlayerToTeam(player: Player, teamName: String, targetName: String) {
        val target = Bukkit.getPlayer(targetName)

        if (target == null) {
            player.sendMessage("해당 플레이어를 찾을 수 없습니다.")
            return
        }

        val visitorConfig = VisitorConfigManager.get()
        val team = visitorConfig.teams.find { it.name == teamName }

        if (team == null) {
            player.sendMessage("존재하지 않는 팀입니다.")
            return
        }

        team.playerList.add(player)
        player.sendMessage("[$teamName] 팀에 [${player.name} 를 추가하였습니다.]")
    }

    private fun removePlayerToTeam(player: Player, teamName: String, targetName: String) {
        val target = Bukkit.getPlayer(targetName)

        if (target == null) {
            player.sendMessage("해당 플레이어를 찾을 수 없습니다.")
            return
        }

        val visitorConfig = VisitorConfigManager.get()
        val team = visitorConfig.teams.find { it.name == teamName }

        if (team == null) {
            player.sendMessage("존재하지 않는 팀입니다.")
            return
        }

        team.playerList.remove(player)
        player.sendMessage("[$teamName] 팀에 [${player.name} 를 삭제하였습니다.]")
    }
}