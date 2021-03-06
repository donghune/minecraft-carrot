package io.github.donghune

import org.bukkit.Material
import org.bukkit.block.data.type.Beehive
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Cow
import org.bukkit.entity.Sheep
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDropItemEvent
import org.bukkit.event.entity.EntityEnterBlockEvent
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

            if (!(player.inventory.itemInMainHand.type == Material.GLASS_BOTTLE || player.inventory.itemInMainHand.type == Material.SHEARS)) {
                return
            }

            HiveRegenScheduler(
                hiveLocation = block.location,
                armorStand = block.location.world!!.spawn(
                    block.location.clone().add(0.5, -1.0, 0.5),
                    ArmorStand::class.java
                ).apply {
                    isInvisible = true
                }
            ).start(regenConfig.hive)
        }
    }

    @EventHandler
    fun onPlayerShearEntityEvent(event: PlayerShearEntityEvent) {
        if (event.entity is Sheep) {
            val sheep = event.entity as Sheep

            if (!sheep.isSheared) {
                SheepRegenScheduler(
                    sheep = sheep
                ).start(regenConfig.sheep)
            }
        }
    }

    @EventHandler
    fun onEntityEnterBlockEvent(event : EntityEnterBlockEvent) {
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
                    ).start(regenConfig.cow)
                }
            }
        }
    }
}