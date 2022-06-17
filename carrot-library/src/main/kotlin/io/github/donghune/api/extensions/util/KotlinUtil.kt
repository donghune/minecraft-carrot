package io.github.donghune.api.extensions.util

import io.github.donghune.api.ItemStackFactory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

fun randomID(size: Int = 16): String = List(size) {
    (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
}.joinToString("")

fun randomItem(): ItemStack = ItemStackFactory()
    .setType(Material.values().filter { it.isItem }.random())
    .setDisplayName(randomID())
    .setLore(randomID(32), randomID(32), randomID(32))
    .build()