package io.github.donghune

import io.github.donghune.api.extensions.dropItem
import io.github.donghune.api.extensions.toComponent
import io.github.donghune.api.mccoroutine.CoroutineTask
import io.github.donghune.api.mccoroutine.schedule
import io.github.donghune.api.plugin
import net.md_5.bungee.api.ChatMessageType
import org.apache.commons.lang.StringEscapeUtils
import org.bukkit.Sound
import org.bukkit.entity.FishHook
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random
import kotlin.random.nextInt


internal val playerByFishGameStatus = mutableMapOf<Player, FishGame>()

var Player.fishGame: FishGame
    get() {
        if (playerByFishGameStatus[this] == null) {
            playerByFishGameStatus[this] = FishGame(this)
        }
        return playerByFishGameStatus[this]!!
    }
    set(value) {
        playerByFishGameStatus[this] = value
    }

class FishGame(
    private val player: Player,
    val status: Status = Status()
) {

    companion object {
        private const val emptyText = "\uF80B\uF80B\uF806\uF809"
        private val clickPointText: (Int) -> String = {
            "\\uF${"%03d".format(it)}"
        }
        private val fishPositionText: (Int) -> String = {
            "\\uA${"%03d".format(it)}"
        }
        private val fishStewText: (Int) -> String = {
            "\\uE${"%03d".format(it)}"
        }
    }

    data class Status(
        var clickPosition: Int = Random.nextInt(1..10),
        var fishStew: Int = 1,
        var fishStewSpeed: Int = 2,
        var fishPosition: Int = 1,
        var failCount: Int = 0,
        var isPlaying: Boolean = false,
        var task: CoroutineTask? = null,
        var resultItem: ItemStack? = null,
        var hook: FishHook? = null,
    ) {
        fun reset() {
            clickPosition = Random.nextInt(1..10)
            fishStew = 1
            fishStewSpeed = 2
            fishPosition = 1
            failCount = 0
            isPlaying = false
            task = null
            resultItem = null
            hook = null
        }
    }

    // TODO: 시작
    fun start(resultItem: ItemStack, hook: FishHook) {
        status.resultItem = resultItem
        status.hook = hook
        status.isPlaying = true
        status.task = plugin.schedule {
            var isReversed = false
            while (true) {
                if (isReversed) {
                    status.fishStew = max(1, status.fishStew - status.fishStewSpeed)
                }
                else {
                    status.fishStew = min(100, status.fishStew + status.fishStewSpeed)
                }
                sendActionBarMessage()
                waitFor(1L)
                if (status.fishStew == 1 || status.fishStew == 100) {
                    isReversed = !isReversed
                }
            }
        }
    }

    fun stop() {
        player.spigot().sendMessage(
            ChatMessageType.ACTION_BAR,
            "".toComponent()
        )
        status.hook?.remove()
        status.task?.cancel()
        status.reset()
    }

    fun catch() {
        val fishGameStatus = status
        if (fishGameStatus.clickPosition == fishGameStatus.fishStew / 10 + 1) {
            success()
        }
        else {
            failure()
        }
    }

    private fun success() {
        // 타점 변경
        status.clickPosition = Random.nextInt(1..10)
        status.fishPosition = min(99, status.fishPosition + Random.nextInt(10..40))
        status.fishStewSpeed = Random.nextInt(2..5)

        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)

        if (status.fishPosition == 99) {
            player.inventory.addItem(status.resultItem)
            player.updateInventory()
            val itemLocation = status.hook?.location
            if (itemLocation != null) {
                val item: Item = itemLocation.dropItem(status.resultItem!!)
                val direction: Vector =
                    player.location.clone().apply { y += 2 }.toVector().subtract(item.location.toVector()).normalize()
                item.velocity = direction
            }
            stop()
        }
    }

    private fun failure() {
        status.failCount++
        player.playSound(player.location, Sound.BLOCK_ANVIL_PLACE, 1f, 1f)

        if (status.failCount >= 3) {
            stop()
        }
    }

    private fun sendActionBarMessage() {
        player.spigot().sendMessage(
            ChatMessageType.ACTION_BAR,
            StringEscapeUtils.unescapeJava(
                clickPointText(status.clickPosition) +
                        emptyText +
                        fishPositionText(status.fishPosition) +
                        emptyText +
                        fishStewText(status.fishStew)
            ).toComponent()
        )
    }
}