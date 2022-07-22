package io.github.donghune

import org.bukkit.entity.Item
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerFishEvent
import org.bukkit.event.player.PlayerInteractEvent

class FishGameListener : Listener {
    @EventHandler
    fun onPlayerFishEvent(event: PlayerFishEvent) {
        if (event.state != PlayerFishEvent.State.CAUGHT_FISH) {
            return
        }

        if (event.player.fishGame.status.isPlaying) {
            return
        }

        event.isCancelled = true
        (event.caught as? Item)?.itemStack?.let { event.player.fishGame.start(it, event.hook) }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
            if (event.player.fishGame.status.isPlaying) {
                event.isCancelled = true
                return
            }
        }

        if (!event.player.fishGame.status.isPlaying) {
            return
        }

        event.player.fishGame.catch()
    }
}