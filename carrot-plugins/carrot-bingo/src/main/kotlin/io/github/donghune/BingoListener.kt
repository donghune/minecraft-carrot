package io.github.donghune

import io.github.donghune.inventory.BingoInventory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class BingoListener : Listener {
    @EventHandler
    fun onPlayerSwapHandItemsEvent(event: PlayerSwapHandItemsEvent) {
        if (event.player.isSneaking) {
            event.isCancelled = true
            if (BingoManager.getBingoPlate(event.player) == null) {
                event.player.sendMessage("아직 게임을 시작하지 않았습니다.")
                return
            }
            BingoInventory().open(event.player)
        }
    }
}