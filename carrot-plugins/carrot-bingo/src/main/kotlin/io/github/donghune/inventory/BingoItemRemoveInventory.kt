package io.github.donghune.inventory

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.PagingList
import io.github.donghune.api.inventory.GUI
import io.github.donghune.api.plugin
import io.github.donghune.entity.BingoConfigManager
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.math.max
import kotlin.math.min

class BingoItemRemoveInventory : GUI(
    plugin = plugin,
    size = 54,
    title = "삭제 할 아이템을 클릭해주세요"
) {
    private var pagingList = PagingList(45, BingoConfigManager.get().items)

    private var previous = ItemStackFactory(ItemStack(Material.BIRCH_SIGN)).setDisplayName("이전 페이지").build()
    private var next = ItemStackFactory(ItemStack(Material.BIRCH_SIGN)).setDisplayName("다음 페이지").build()

    private var page: Int = 0
        set(value) {
            field = min(max(value, 0), pagingList.lastPageIndex)
        }

    init {
        content {
            pagingList.getPage(page).forEachIndexed { index, itemStack ->
                setItem(index, itemStack) {
                    isCancelled = true
                    BingoConfigManager.get().items.remove(itemStack)
                    BingoConfigManager.get().update()
                    PagingList(45, BingoConfigManager.get().items)
                    refreshContent()
                }
            }
            setItem(46, previous) {
                isCancelled = true
                page -= 1
                refreshContent()
            }
            setItem(52, next) {
                isCancelled = true
                page += 1
                refreshContent()
            }
        }
    }
}