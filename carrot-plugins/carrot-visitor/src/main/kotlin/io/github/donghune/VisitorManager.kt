package io.github.donghune

import io.github.donghune.api.extensions.chatColor
import io.github.donghune.api.extensions.toComponent
import io.github.donghune.api.getString
import io.github.donghune.api.plugin
import io.github.donghune.api.setTag
import io.github.donghune.api.translate
import io.github.donghune.entity.VisitorConfig
import io.github.donghune.entity.visitorConfig
import io.github.donghune.scheduler.VisitorWaitingScheduler
import org.bukkit.Bukkit
import org.bukkit.EntityEffect
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Rabbit
import org.bukkit.entity.Villager
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random

object VisitorManager {
    private val navigation = mutableMapOf<Villager, Location>()
    private val waiting = mutableMapOf<Villager, VisitorWaitingScheduler>()

    fun reset() {
        navigation.clear()
        waiting.clear()
    }

    fun getVisitors(): List<Pair<Villager, Location>> {
        return navigation.map { (villager, location) ->
            villager to location
        }
    }

    /**
     * 배치 가능한 팀을 찾아 주민의 위치를 해당 위치로 지정하는 함수
     */
    fun assignVillagerToTeam(config: VisitorConfig) {
        // 배치 가능한 팀 찾기
        val team = config.teams.map {
            it to it.anchorLocations.count { location -> getVillagerByLocation(location) != null }
        }.filter { it.second != it.first.anchorLocations.size }.minByOrNull { it.second } ?: return

        //  해당 주민 팀의 앵처 위치로 배치
        val villager = getVillagerByLocation(config.locations.startLocation) ?: return
        villager.setTag("teamName", team.first.name)
        navigation[villager] = team.first.anchorLocations.firstOrNull { getVillagerByLocation(it) == null } ?: return
    }

    /**
     * 시작 장소에 주민을 소환하는 함수
     * 시작 장소에 이미 주민이 대기하고 있다면 작동하지 않음
     */
    fun spawnVillagerOnStartLocation(config: VisitorConfig, world: World) {
        // 주민 체크
        if (getVillagerByLocation(config.locations.startLocation) != null) {
            return
        }

        // 주민 소환
        val visitorItem = config.visitorOption.items.random()
        val entityItem = visitorItem.itemStack.clone().apply { amount = Random.nextInt(1, visitorItem.maxAmount) }
        val startLocation = config.locations.startLocation

        val itemEntity = world.dropItem(startLocation, entityItem.clone().apply { amount = 1 }).apply {
            setTag("isVisitorItem", true)
            customName = "${entityItem.translate} ${entityItem.amount}개"
            isCustomNameVisible = true
        }

        val emptyEntity = (world.spawnEntity(startLocation, EntityType.RABBIT) as Rabbit).apply {
            addPassenger(itemEntity)
            isInvisible = true
        }

        val villagerEntity = (world.spawnEntity(startLocation, EntityType.VILLAGER) as Villager).apply {
            villagerType = Villager.Type.values().random()
            addPassenger(emptyEntity)
            isCollidable = false
            setTag("visitorItem", visitorItem)
            setTag("amount", entityItem.amount)

            if (Random.nextInt(1, 100) < config.visitorOption.specialChance) {
                setTag("isSpecial", "true")
                addPotionEffect(
                    PotionEffect(
                        PotionEffectType.GLOWING, Int.MAX_VALUE, Int.MAX_VALUE
                    )
                )
            }
        }

        navigation[villagerEntity] = config.locations.startLocation
    }

    /**
     * 해당 위치에 배정되어 있는 주민의 UUID 를 반환
     */
    private fun getVillagerByLocation(location: Location): Villager? {
        return navigation.firstNotNullOfOrNull {
            if (it.value == location) {
                return it.key
            } else null
        }
    }

    fun successSellItem(villager: Villager) {
        villager.playEffect(EntityEffect.VILLAGER_HEART)
        navigation[villager] = visitorConfig.locations.endLocation
        villager.passengers.forEach { it.removeWithPassengers() }
        waiting[villager]?.stop()
    }

    fun Entity.removeWithPassengers() {
        passengers.forEach { it.removeWithPassengers() }
        remove()
    }

    fun hitTheLoad(villager: Villager) {
        val team = visitorConfig.teams.find { it.name == villager.getString("teamName") } ?: throw Exception("팀이 없숴?!")
        val message = visitorConfig.messages.hitAroundMessage.random().chatColor()
        team.consecutiveSales -= 1
        team.playerList.mapNotNull { Bukkit.getPlayer(it) }.forEach {
            it.spigot().sendMessage(
                visitorConfig.messages.hitAroundType, "$message [${team.consecutiveSales}]".toComponent()
            )
        }
        navigation[villager] = visitorConfig.locations.endLocation
        villager.playEffect(EntityEffect.VILLAGER_ANGRY)
        waiting[villager]?.stop()
    }

    fun onArrival(visitor: Villager, location: Location) {
        when (location) {
            visitorConfig.locations.startLocation -> {

            }
            visitorConfig.locations.endLocation -> {
                visitor.removeWithPassengers()
                waiting.remove(visitor)
                navigation.remove(visitor)
            }
            else -> {
                visitorConfig.teams.forEach {
                    if (it.anchorLocations.contains(location)) {
                        if (!waiting.containsKey(visitor)) {
                            waiting[visitor] = VisitorWaitingScheduler(plugin, visitor)
                            waiting[visitor]?.start()
                        }
                    }
                }
            }
        }
    }

}