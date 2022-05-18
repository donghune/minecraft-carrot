package io.donghune.machine

import io.github.donghune.api.DHEPlugin
import org.bukkit.Location
import org.bukkit.Material

class MachinePlugin : DHEPlugin() {
    override fun onEnable() {
        super.onEnable()
    }

    override fun onDisable() {
        super.onDisable()
    }
}

/**
 * machine
 *  - fuel
 *  - inventory
 *  - action
 *  - ????
 */

abstract class Machine {
    abstract val name: String
    abstract val size: Int

    abstract fun action(location: Location)
}

class FireMiningDrill : Machine() {

    override val name: String = "화력 채광 드릴"

    override val size: Int = 1

    override fun action(location: Location) {
        val bottomBlock = location.clone().add(0.0, -1.0, 0.0).block

        when (bottomBlock.type) {
            Material.COAL_ORE -> {}
            Material.IRON_ORE -> {}
            Material.COPPER_ORE -> {}
            Material.COBBLESTONE -> {}
            else -> {}
        }
    }

    private fun minding() {
    }

}