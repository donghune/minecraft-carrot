package io.github.donghune.inventory

import io.github.donghune.BingoManager
import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.inventory.GUI
import io.github.donghune.bingoPlugin
import io.github.donghune.scheduler.BingoCoroutineScheduler
import io.github.donghune.scheduler.SpecialTimeScheduler
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.inventory.ItemStack

class BingoInventory : GUI(
    plugin = bingoPlugin,
    size = 54,
    title = "빙고판"
) {
    private val bingoCompleteItem = ItemStackFactory(ItemStack(Material.BARRIER)).setDisplayName("빙고").build()
    private val specialItem: (ItemStack) -> ItemStack = {
        ItemStackFactory(ItemStack(Material.WATER_BUCKET))
            .setDisplayName("특별 이벤트")
            .addLore(
                Component.text("현재 ")
                    .append(SpecialTimeScheduler.specialItem?.displayName() ?: Component.text(""))
                    .append(Component.text(" 을 가져오시면 교환티켓으로 바꿔드립니다!"))
            )
            .build()
    }
    private val guiItem: (Int) -> ItemStack = {
        ItemStackFactory(ItemStack(Material.DIAMOND_AXE))
            .setCustomModelData(it + 1)
            .setDisplayName("")
            .build()
    }

    init {
        content {
            var index = 0
            for (row in 12..48 step 9) {
                repeat(5) {
                    val slot = row + it
                    val bingoItem = BingoManager.getBingoPlate(player)!![index]
                    setItem(slot, if (bingoItem.isChecked) bingoCompleteItem else bingoItem.itemStack) {
                        isCancelled = true

                        if (player.inventory.contents.filterNotNull()
                                .find { it.isSimilar(bingoItem.itemStack) } == null
                        ) {
                            return@setItem
                        }

                        val beforeBingoCount = BingoManager.calculateBingoCount(player)
                        player.inventory.remove(bingoItem.itemStack)
                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                        bingoItem.isChecked = true
                        refreshContent()

                        if (beforeBingoCount != BingoManager.calculateBingoCount(player)) {
                            player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)
                        }

                        if (BingoManager.calculateBingoCount(player) == 5) {
                            BingoCoroutineScheduler.stop()
                        }
                    }
                    index++
                }
            }
            if (SpecialTimeScheduler.isSpecialTime) {
                setItem(28, specialItem(SpecialTimeScheduler.specialItem!!)) {
                    isCancelled = true
                    BingoManager.getBingoPlate(player)?.filter { !it.isChecked }?.random()?.isChecked = true
                    SpecialTimeScheduler.stop()
                    refreshContent()
                    Bukkit.getOnlinePlayers().forEach { it.player?.closeInventory() }
                }
            }
            setItem(53, guiItem(BingoManager.calculateBingoCount(player))) {
                isCancelled = true
            }
        }
    }
}