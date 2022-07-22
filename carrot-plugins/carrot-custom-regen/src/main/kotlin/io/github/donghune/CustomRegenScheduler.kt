package io.github.donghune

import io.github.donghune.api.mccoroutine.KBukkitScheduler
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.type.Beehive
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Chicken
import org.bukkit.entity.Cow
import org.bukkit.entity.Sheep
import org.bukkit.inventory.ItemStack

class ChickenRegenScheduler(private val chicken: Chicken) : KBukkitScheduler() {
    init {
        onStart {
            chicken.customName = "%02d:%02d".format(leftSec / 60, leftSec % 60)
            chicken.isCustomNameVisible = true
            RegenConfigManager.chickens.add(chicken)
            chicken.setOnEntityDeathEvent { cancel() }
            chicken.setOnEntityDropItemEvent { it.isCancelled = true }
        }
        onDuringSec {
            chicken.customName = "%02d:%02d".format(leftSec / 60, leftSec % 60)
        }
        onStop {
            chicken.world.dropItem(chicken.location, ItemStack(Material.EGG))
            ChickenRegenScheduler(chicken).start(RegenConfigManager.get().chicken)
        }
    }
}

class HiveRegenScheduler(private val hiveLocation: Location, private val armorStand: ArmorStand) :
    KBukkitScheduler() {
    init {
        onStart {
            armorStand.customName = "%02d:%02d".format(leftSec / 60, leftSec % 60)
            armorStand.isCustomNameVisible = true
        }
        onDuringSec {
            armorStand.customName = "%02d:%02d".format(leftSec / 60, leftSec % 60)
        }
        onStop {
            val hive = hiveLocation.block.blockData as Beehive
            hive.honeyLevel = hive.maximumHoneyLevel
            hiveLocation.block.blockData = hive
            armorStand.remove()
        }
    }
}

class SheepRegenScheduler(private val sheep: Sheep) : KBukkitScheduler() {
    init {
        onStart {
            sheep.customName = "%02d:%02d".format(leftSec / 60, leftSec % 60)
            sheep.isCustomNameVisible = true
            sheep.setOnEntityDeathEvent { cancel() }
            sheep.setOnSheepRegrowWoolEvent { it.isCancelled = true }
        }
        onDuringSec {
            sheep.customName = "%02d:%02d".format(leftSec / 60, leftSec % 60)
        }
        onStop {
            sheep.isSheared = false
            sheep.isCustomNameVisible = false
        }
    }
}

class CowRegenScheduler(private val cow: Cow) : KBukkitScheduler() {
    init {
        onStart {
            RegenConfigManager.cows.add(cow)
            cow.customName = "%02d:%02d".format(leftSec / 60, leftSec % 60)
            cow.isCustomNameVisible = true
            cow.setOnEntityDeathEvent { cancel() }
        }
        onDuringSec {
            cow.customName = "%02d:%02d".format(leftSec / 60, leftSec % 60)
        }
        onStop {
            RegenConfigManager.cows.remove(cow)
            cow.isCustomNameVisible = false
        }
    }
}

