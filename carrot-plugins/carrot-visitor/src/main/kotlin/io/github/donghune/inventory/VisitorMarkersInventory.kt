package io.github.donghune.inventory

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.inventory.GUI
import io.github.donghune.api.plugin
import io.github.donghune.entity.visitorConfig
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class VisitorMarkersInventory : GUI(
    plugin = plugin,
    size = 54,
    title = "마커 목록",
) {
    private val marker: (Int, Location) -> ItemStack = { index, location ->
        ItemStackFactory(Material.COMPASS)
            .setDisplayName("$index 번째 마커")
            .setLore(
                "&f[X] : &6${location.blockX}",
                "&f[Y] : &6${location.blockY}",
                "&f[Z] : &6${location.blockZ}",
                "",
                "&a클릭 시 해당 위치로 이동 합니다."
            )
            .build()
    }

    init {
        content {
            visitorConfig.markers.mapIndexed { index, location ->
                setItem(index, marker(index, location)) {
                    whoClicked.teleport(location)
                }
            }
        }
    }
}