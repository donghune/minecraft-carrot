package io.github.donghune.api.extensions

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

val Inventory.hasSpace: Boolean
    get() = contents.any { it == null || it.type == Material.AIR }

fun Inventory.hasItem(itemStack: ItemStack, amount: Int = 1): Boolean {
    return contents.filterNotNull().filter { it.type != Material.AIR }
        .map { it.clone().apply { setAmount(1) } to it.amount }
        .filter { it.first.isSimilar(itemStack) }
        .sumOf { it.second } >= amount
}

fun Inventory.hasSpace(amount: Int): Boolean {
    return contents.count { it == null || it.type == Material.AIR } >= amount
}

fun Inventory.hasSpace(
    item: ItemStack,
    amount: Int = item.amount
) = spaceOf(item) >= amount

fun Inventory.spaceOf(
    item: ItemStack
): Int {
    return contents.filterNotNull().map {
        if (it.amount < it.maxStackSize && it.isSimilar(item))
            it.maxStackSize - it.amount
        else 0
    }.count()
}