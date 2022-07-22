package io.github.donghune.inventory

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.inventory.GUI
import io.github.donghune.api.plugin
import io.github.donghune.entity.SalesItem
import io.github.donghune.entity.visitorConfig
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import kotlin.math.max
import kotlin.math.min

class SalesItemRegisterInventory(
    private val salesItem: SalesItem
) : GUI(
    plugin = plugin,
    inventoryType = InventoryType.HOPPER,
    title = "판매 물건 등록",
) {
    private var price = salesItem.price
        set(value) {
            field = max(0, value)
        }
    private var amount = salesItem.amount
        set(value) {
            field = max(1, min(64, value))
        }

    private val PRICE_UP = ItemStackFactory(Material.GREEN_STAINED_GLASS_PANE)
        .setDisplayName("가격 상승")
        .setLore("클릭 하면 가격이 1 Emerald 만큼 상승합니다.")
        .build()
    private val PRICE_DOWN = ItemStackFactory(Material.RED_STAINED_GLASS_PANE)
        .setDisplayName("가격 하락")
        .setLore("클릭 하면 가격이 1 Emerald 만큼 하락합니다.")
        .build()
    private val AMOUNT_UP = ItemStackFactory(Material.GREEN_STAINED_GLASS_PANE)
        .setDisplayName("수량 상승")
        .setLore("클릭 하면 수량이 1 만큼 상승합니다.")
        .build()
    private val AMOUNT_DOWN = ItemStackFactory(Material.RED_STAINED_GLASS_PANE)
        .setDisplayName("수량 하락")
        .setLore("클릭 하면 수량이 1 만큼 하락합니다.")
        .build()
    private val ITEM: ItemStack
        get() = ItemStackFactory(salesItem.itemStack, true)
            .setAmount(amount)
            .addLore("")
            .addLore("&a${price} Emerald")
            .build()

    init {
        onInventoryOpen {
            if (!visitorConfig.salesItems.contains(salesItem)) {
                visitorConfig.salesItems.add(salesItem)
            }
        }
        content {
            setItem(0, PRICE_UP) {
                isCancelled = true
                price++
                refreshContent()
            }
            setItem(1, PRICE_DOWN) {
                isCancelled = true
                price--
                refreshContent()
            }
            setItem(2, ITEM) {
                isCancelled = true
                if (isRightClick) {
                    visitorConfig.salesItems.removeIf { it.uuid == salesItem.uuid }
                    visitorConfig.update()
                    player.closeInventory()
                    return@setItem
                }
                if (isLeftClick) {
                    visitorConfig.salesItems.find { it.uuid == salesItem.uuid }?.let {
                        it.amount = amount
                        it.price = price
                    }
                    visitorConfig.update()
                    SalesItemManagerInventory().openLater(player)
                }
            }
            setItem(3, AMOUNT_UP) {
                isCancelled = true
                amount++
                refreshContent()
            }
            setItem(4, AMOUNT_DOWN) {
                isCancelled = true
                amount--
                refreshContent()
            }
        }
    }
}