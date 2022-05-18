package io.github.donghune

import io.github.donghune.api.extensions.registerEvents
import io.github.donghune.api.getObject
import org.bukkit.Material
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack

object VisitorListener : Listener {

    fun initialize(plugin: VisitorPlugin) {
        plugin.registerEvents(this)
    }

    @EventHandler
    fun onPlayerInteractEntityEvent(event: PlayerInteractEntityEvent) {
        val entity = event.rightClicked

        if (entity !is Villager) {
            return
        }

        event.isCancelled = true

        val purposeItem = entity.getObject<ItemStack>("item") ?: return

        val player = event.player
        val playerItem = player.inventory.itemInMainHand

        if (playerItem.type == Material.AIR) {
            player.sendMessage("판매 할 수 있는 아이템이 없습니다.")
            return
        }

        if (!(playerItem.type == purposeItem.type && playerItem.amount >= purposeItem.amount)) {
            player.sendMessage("손님이 원하는 아이템이 아니거나 수량이 부족합니다.")
            return
        }

        playerItem.amount -= purposeItem.amount
        // TODO
        VisitorManager.successSellItem(entity)
        player.sendMessage("물건을 판매하였습니다.")
    }
}