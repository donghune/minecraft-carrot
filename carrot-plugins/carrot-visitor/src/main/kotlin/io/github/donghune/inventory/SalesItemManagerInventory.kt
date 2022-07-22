package io.github.donghune.inventory

import io.github.donghune.api.inventory.GUI
import io.github.donghune.api.plugin
import io.github.donghune.entity.SalesItem
import io.github.donghune.entity.visitorConfig
import java.util.*
import java.util.UUID.*

class SalesItemManagerInventory : GUI(
    plugin = plugin,
    size = 54,
    title = "판매 물건 관리",
) {
    init {
        content {
            visitorConfig.salesItems.forEachIndexed { index, salesItem ->
                setItem(index, salesItem.toItemStack()) {
                    if (isLeftClick) {
                        SalesItemRegisterInventory(salesItem).open(player)
                    }
                }
            }
        }
        onPlayerInventoryClick {
            currentItem?.let {
                SalesItemRegisterInventory(
                    SalesItem(
                        uuid = randomUUID(),
                        itemStack = it.apply { amount = 1 },
                        amount = it.amount,
                        price = 0
                    )
                ).open(player)
            }
        }
    }
}