package io.github.donghune

import io.github.donghune.api.setTag
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.*
import java.util.*
import kotlin.random.Random

object VisitorManager {
    private val navigation = mutableMapOf<UUID, Location>()

    fun getVisitors(): List<Pair<Villager, Location>> {
        return navigation
            .filter { (uuid, _) -> Bukkit.getEntity(uuid) != null }
            .map { (uuid, location) ->
                Bukkit.getEntity(uuid) as Villager to location
            }
    }

    /**
     * 배치 가능한 팀을 찾아 주민의 위치를 해당 위치로 지정하는 함수
     */
    fun assignVillagerToTeam(config: VisitorConfig) {
        // 배치 가능한 팀 찾기
        val team = config.teams.find { team -> getVillagerByLocation(team.anchorLocation) == null } ?: return

        //  해당 주민 팀의 앵처 위치로 배치
        val villagerUUID = getVillagerByLocation(config.startLocation) ?: return
        navigation[villagerUUID] = team.anchorLocation
    }

    /**
     * 시작 장소에 주민을 소환하는 함수
     * 시작 장소에 이미 주민이 대기하고 있다면 작동하지 않음
     */
    fun spawnVillagerOnStartLocation(config: VisitorConfig, world: World) {
        // 주민 체크
        if (getVillagerByLocation(config.startLocation) != null) {
            return
        }

        // 주민 소환
        val sellItem = config.items.random().clone()

        val itemEntity = world.spawnEntity(config.startLocation, EntityType.DROPPED_ITEM) as Item
        itemEntity.itemStack = sellItem.apply { amount = Random.nextInt(1, sellItem.amount) }
        itemEntity.setCanMobPickup(false)
        itemEntity.setCanPlayerPickup(false)

        val emptyEntity = world.spawnEntity(config.startLocation, EntityType.BAT) as Bat
        emptyEntity.addPassenger(itemEntity)
        emptyEntity.isInvisible = true

        val villager = world.spawnEntity(config.startLocation, EntityType.VILLAGER)
        villager.addPassenger(emptyEntity)

        villager.setTag("type", sellItem.type.key.key)
        villager.setTag("amount", sellItem.amount)
        villager.setTag("item", itemEntity.itemStack)

        navigation[villager.uniqueId] = config.startLocation
    }

    /**
     * 해당 위치에 배정되어 있는 주민의 UUID 를 반환
     */
    private fun getVillagerByLocation(location: Location): UUID? {
        return navigation.firstNotNullOfOrNull {
            if (it.value == location) {
                return it.key
            } else null
        }
    }

    fun successSellItem(villager: Villager) {
        navigation.remove(villager.uniqueId)
        villager.removeWithPassengers()
    }

    private fun Entity.removeWithPassengers() {
        passengers.forEach { it.removeWithPassengers() }
        remove()
    }

}