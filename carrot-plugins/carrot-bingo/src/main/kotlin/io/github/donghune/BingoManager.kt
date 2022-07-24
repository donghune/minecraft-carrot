package io.github.donghune

import io.github.donghune.api.extensions.Title
import io.github.donghune.api.extensions.sendTitle
import io.github.donghune.entity.BingoConfigManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

object BingoManager {
    val bingoPlate: MutableMap<UUID, List<BingoItem>> = mutableMapOf()

    fun create(player: Player) {
        bingoPlate[player.uniqueId] = BingoConfigManager.get().items.shuffled().subList(0, 25).map { BingoItem(it, false) }
    }

    fun createAll() {
        Bukkit.getOnlinePlayers().forEach {
            create(it)
        }
    }

    fun reset(player: Player) {
        bingoPlate.remove(player.uniqueId)
    }

    fun resetAll() {
        Bukkit.getOnlinePlayers().forEach {
            reset(it)
        }
    }

    fun getBingoPlate(player: Player): List<BingoItem>? {
        return bingoPlate[player.uniqueId]
    }

    fun calculateBingoCount(player: Player): Int {
        var count = 0
        val array = bingoPlate[player.uniqueId]!!

        // 가로 체크
        if (listOf(array[0], array[1], array[2], array[3], array[4]).find { item -> !item.isChecked } == null) {
            count++
        }
        if (listOf(array[5], array[6], array[7], array[8], array[9]).find { item -> !item.isChecked } == null) {
            count++
        }
        if (listOf(array[10], array[11], array[12], array[13], array[14]).find { item -> !item.isChecked } == null) {
            count++
        }
        if (listOf(array[15], array[16], array[17], array[18], array[19]).find { item -> !item.isChecked } == null) {
            count++
        }
        if (listOf(array[20], array[21], array[22], array[23], array[24]).find { item -> !item.isChecked } == null) {
            count++
        }

        // 세로 체크
        if (listOf(array[0], array[5], array[10], array[15], array[20]).find { item -> !item.isChecked } == null) {
            count++
        }
        if (listOf(array[1], array[6], array[11], array[16], array[21]).find { item -> !item.isChecked } == null) {
            count++
        }
        if (listOf(array[2], array[7], array[12], array[17], array[22]).find { item -> !item.isChecked } == null) {
            count++
        }
        if (listOf(array[3], array[8], array[13], array[18], array[23]).find { item -> !item.isChecked } == null) {
            count++
        }
        if (listOf(array[4], array[9], array[14], array[19], array[24]).find { item -> !item.isChecked } == null) {
            count++
        }

        // 대각선 체크
        if (listOf(array[0], array[6], array[12], array[18], array[24]).find { item -> !item.isChecked } == null) {
            count++
        }
        if (listOf(array[4], array[8], array[12], array[16], array[20]).find { item -> !item.isChecked } == null) {
            count++
        }

        return count
    }

    fun printRank() {
        val player =
            Bukkit.getOnlinePlayers().map { it to calculateBingoCount(it) }.toList().sortedBy { it.second }[0].first

        Bukkit.getOnlinePlayers().sendTitle { Title(player.displayName, "축하드립니다!") }
    }
}

class BingoItem(
    var itemStack: ItemStack,
    var isChecked: Boolean
)