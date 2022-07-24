package io.github.donghune

import io.github.donghune.api.extensions.hasItem
import io.github.donghune.api.extensions.registerEvents
import io.github.donghune.api.getObject
import io.github.donghune.entity.VisitorMeta
import io.github.donghune.util.isCapableDamaged
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack

object VisitorListener : Listener {

    fun initialize(plugin: VisitorPlugin) {
        plugin.registerEvents(this)
    }

    @EventHandler
    fun onEntityDamageEvent(event: EntityDamageEvent) {
        if (!event.entity.isCapableDamaged) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerInteractEntityEvent(event: PlayerInteractEntityEvent) {
        val visitorMeta = event.rightClicked.getObject<VisitorMeta>("VisitorMeta") ?: return

        val requiredItem = visitorMeta.salesItem.itemStack.clone().apply { amount = visitorMeta.salesItem.amount }

        if (!event.player.inventory.hasItem(requiredItem, requiredItem.amount)) {
            return
        }

        event.player.inventory.removeItem(requiredItem)

        val price = visitorMeta.salesItem.price
        val amount = visitorMeta.salesItem.amount
        val isDouble = visitorMeta.isSpecial

        val totalReward = price * amount * (if (isDouble) 2 else 1)

        event.player.inventory.addItem(ItemStack(Material.EMERALD).apply { setAmount(totalReward) })
        event.player.playSound(event.player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        VisitorManager.remove(event.rightClicked as Villager)
    }
}