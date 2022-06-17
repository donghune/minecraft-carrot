package io.github.donghune

import org.bukkit.Material
import org.bukkit.block.data.type.Beehive
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Chicken
import org.bukkit.entity.Cow
import org.bukkit.entity.Sheep
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDropItemEvent
import org.bukkit.event.entity.SheepRegrowWoolEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerShearEntityEvent

class CustomRegenListener : Listener {

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        val player = event.player
        val block = event.clickedBlock ?: return

        if (block.type == Material.BEE_NEST) {

            val hiveData = (block.blockData as Beehive)

            if (hiveData.honeyLevel != hiveData.maximumHoneyLevel) {
                return
            }

            if (player.inventory.itemInMainHand.type != Material.GLASS_BOTTLE) {
                return
            }

            HiveRegenScheduler(
                hiveLocation = block.location,
                armorStand = block.location.world.spawn(
                    block.location.clone().add(0.5, -1.0, 0.0),
                    ArmorStand::class.java
                ).apply {
                    isInvisible = true
                }
            ).start(RegenConfigManager.get().hive)
        }
    }

    @EventHandler
    fun onPlayerShearEntityEvent(event: PlayerShearEntityEvent) {
        if (event.entity is Sheep) {
            val sheep = event.entity as Sheep

            if (!sheep.isSheared) {
                SheepRegenScheduler(
                    sheep = sheep
                ).start(RegenConfigManager.get().sheep)
            }
        }
    }

    @EventHandler // 치킨이 알을 싸는 경우
    fun onEntityDropItemEvent(event: EntityDropItemEvent) {
        if (event.entity is Chicken) {
            event.isCancelled = true
        }
    }

    @EventHandler // 양 양털이 다시 자랐을때 이벤트
    fun onSheepRegrowWoolEvent(event: SheepRegrowWoolEvent) {
        event.isCancelled = true
    }

    @EventHandler
    fun onPlayerBucketEntityEvent(event: PlayerInteractEntityEvent) {
        val entity = event.rightClicked
        val player = event.player

        if (entity is Cow) {
            if (player.inventory.itemInMainHand.type == Material.BUCKET) {
                if (RegenConfigManager.cows.contains(entity)) {
                    event.isCancelled = true
                    player.updateInventory()
                }
                else {
                    CowRegenScheduler(
                        cow = entity
                    ).start(RegenConfigManager.get().cow)
                }
            }
        }
    }
}