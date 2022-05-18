package io.github.donghune

import io.github.donghune.api.inventory.GUI
import io.github.donghune.api.plugin

object VisitorInventory : GUI(plugin, 54, "판매 물건 설정") {
    init {
        onInventoryOpen {
            inventory.storageContents = VisitorConfigManager.get().items.toTypedArray()
        }
        onInventoryClose {
            VisitorConfigManager.get().apply {
                VisitorConfigManager.get().items = (inventory.contents ?: arrayOf()).filterNotNull()
            }.update()
        }
    }
}