package io.github.donghune.api.extensions

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun String.chatColor(): String {
    return replace("&", "§")
}

fun CommandSender.sendColorCodeMessage(message: String) {
    sendMessage(message.chatColor())
}

fun CommandSender.sendErrorMessage(message: Any) {
    sendColorCodeMessage("&c$message")
}

fun CommandSender.sendDebugMessage(message: Any) {
    sendColorCodeMessage("&e[Debug] &f$message")
}

fun Player.performCommand(
    command: String,
    withOp: Boolean = false
) {
    if (command.isEmpty()) {
        return
    }

    val isAlreadyOp = isOp

    if (withOp) {
        isOp = true
    }

    performCommand(command)

    if (withOp) {
        if (isAlreadyOp) {
            return
        }

        isOp = false
    }
}

fun ItemStack.isSimilarTo(stack: ItemStack?): Boolean {
    if (stack == null) {
        return false
    }

    if (type != stack.type) {
        return false
    }

    if (hasItemMeta() != stack.hasItemMeta()) {
        return false
    }

    val originalItemMeta = itemMeta ?: return false
    val comparisonItemMeta = stack.itemMeta ?: return false

    if (originalItemMeta.lore != comparisonItemMeta.lore) {
        return false
    }

    if (originalItemMeta.hasCustomModelData()) {
        if (comparisonItemMeta.hasCustomModelData()) {
            if (originalItemMeta.customModelData != originalItemMeta.customModelData) {
                return false
            }
        } else {
            return false
        }
    } else {
        if (comparisonItemMeta.hasCustomModelData()) {
            return false
        }
    }

    if (originalItemMeta.displayName != comparisonItemMeta.displayName) {
        return false
    }

    return true
}

fun Location.toPrettyString(): String = "[X] = $blockX, [Y] = $blockY, [Z] = $blockZ"

fun Pair<Location, Location>.toBlockArray(): List<Block> {
    val blockList = mutableListOf<Block>()

    if (first.world == null) {
        return emptyList()
    }

    if (second.world == null) {
        return emptyList()
    }

    val minX: Int = first.blockX.coerceAtMost(second.blockX)
    val maxX: Int = first.blockX.coerceAtLeast(second.blockX)

    val minY: Int = first.blockY.coerceAtMost(second.blockY)
    val maxY: Int = first.blockY.coerceAtLeast(second.blockY)

    val minZ: Int = first.blockZ.coerceAtMost(second.blockZ)
    val maxZ: Int = first.blockZ.coerceAtLeast(second.blockZ)

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            for (z in minZ..maxZ) {
                blockList.add(first.world!!.getBlockAt(x, y, z))
            }
        }
    }
    return blockList
}
