package io.github.donghune

import io.github.donghune.api.extensions.chatColor
import io.github.donghune.api.extensions.registerEvents
import io.github.donghune.api.extensions.toComponent
import io.github.donghune.api.getBoolean
import io.github.donghune.api.getInt
import io.github.donghune.api.getObject
import io.github.donghune.api.getString
import io.github.donghune.entity.VisitorItem
import io.github.donghune.entity.team
import io.github.donghune.entity.visitorConfig
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Villager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.ItemMergeEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.inventory.ItemStack

object VisitorListener : Listener {

    fun initialize(plugin: VisitorPlugin) {
        plugin.registerEvents(this)
    }

    @EventHandler
    fun onEntityPickupItemEvent(event: EntityPickupItemEvent) {
        if (event.item.getBoolean("isVisitorItem") == true) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onItemMergeEvent(event: ItemMergeEvent) {
        if (event.target.getBoolean("isVisitorItem") == true) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onEntityDamageEvent(event: EntityDamageEvent) {
        if (event.entity.getObject<VisitorItem>("visitorItem") != null) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onPlayerInteractEntityEvent(event: PlayerInteractEntityEvent) {
        val entity = event.rightClicked

        if (entity !is Villager) {
            return
        }

        event.isCancelled = true

        // parse
        val purposeItem = entity.getObject<VisitorItem>("visitorItem") ?: return
        val wantAmount = entity.getInt("amount") ?: return

        val player = event.player
        val playerItem = player.inventory.itemInMainHand
        val team = player.team()

        if (team == null) {
            player.sendMessage("팀에 속해있지 않습니다.")
            return
        }

        if (playerItem.type == Material.AIR) {
            player.sendMessage("판매 할 수 있는 아이템이 없습니다.")
            return
        }

        val inventoryAmount = player.inventory.contents.filterNotNull()
            .filter { it.isSimilar(purposeItem.itemStack) }.sumOf { it.amount }

        if (inventoryAmount <= wantAmount) {
            player.sendMessage("손님이 원하는 아이템이 아니거나 수량이 부족합니다.")
            player.playSound(player.location, Sound.BLOCK_ANVIL_PLACE, 1f, 1f)
            return
        }

        val isSpecial = entity.getString("isSpecial") == "true"

        player.inventory.removeItem(purposeItem.itemStack.clone().apply { amount = wantAmount })
        team.consecutiveSales += 1

        visitorConfig.comboReward.find { team.consecutiveSales in (it.from..it.to) }?.let {
            player.inventory.addItem(ItemStack(Material.EMERALD).apply { amount = it.count })
            if (it.from == team.consecutiveSales) {
                player.playSound(player.location, Sound.ENTITY_ARROW_SHOOT, 1f, 1f)
            }
        }

        val message = visitorConfig.messages.tradeSuccessMessage.random().chatColor()

        team.playerList.mapNotNull { Bukkit.getPlayer(it) }.forEach {
            it.spigot().sendMessage(
                visitorConfig.messages.tradeSuccessType,
                "$message [${team.consecutiveSales}]".toComponent()
            )
        }

        var totalPrice = purposeItem.price * wantAmount
        if (isSpecial) {
            totalPrice = (totalPrice * (1 + visitorConfig.visitorOption.specialPercent / 100.0)).toInt()
        }

        player.inventory.addItem(ItemStack(Material.EMERALD, totalPrice))
        player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
        VisitorManager.successSellItem(entity)
    }
}