package io.github.donghune

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.extensions.mainWorld
import io.github.donghune.api.extensions.toComponent
import io.github.monun.kommand.kommand
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.ItemFrame
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta

object MokokoCommand {
    fun init(plugin: MokokoPlugin) {
        plugin.kommand {
            register("mokoko") {
                then("create") {
                    executes {
                        MokokoConfigManager.get().locations.forEach {
                            val itemFrame = mainWorld.spawnEntity(it.toBlockLocation(), EntityType.ITEM_FRAME) as ItemFrame
                            itemFrame.setItem(ItemStackFactory(ItemStack(Material.DIAMOND)).setCustomModelData(1).build())
                            itemFrame.isVisible = false
                        }
                    }
                }
                then("reg") {
                    executes {
                        val mokoko = MokokoConfigManager.get()
                        mokoko.locations.mutableCopyOf().apply { add(player.location) }
                            .run { mokoko.copy(locations = this) }.also { MokokoConfigManager.update(it) }
                        syncMokoko()
                        player.sendMessage("모코코를 좌표를 추가하였습니다.")
                    }
                }
                then("inv") {
                    executes {
//                        book.editMeta(BookMeta::class.java) { bookMeta ->

//                        }
                        val writtenBook = ItemStack(Material.WRITTEN_BOOK)
                        val bookMeta = writtenBook.itemMeta as BookMeta
                        bookMeta.title = "모코코 수집 내역"
                        bookMeta.author = "창조자"
                        PlayerMokokoManager.get(player.uniqueId.toString())?.let {
                            it.data.map { (index, isFind) ->
                                "[${if (isFind) "V" else " "}] $index 번째 모코코".toComponent()
                                    .color(if (isFind) NamedTextColor.GREEN else NamedTextColor.GRAY)
                            }
                        }?.let { bookMeta.addPages(*it.toTypedArray()) }
                        writtenBook.itemMeta = bookMeta
                        player.openBook(writtenBook)
                    }
                }
            }
        }
    }

    private fun syncMokoko() {
        PlayerMokokoManager.getList().forEach {
            val data = it.data.toMutableMap()
            repeat(MokokoConfigManager.get().locations.size) { index ->
                if (!data.containsKey(index)) {
                    data[index] = false
                }
            }
            it.copy(data = data).update()
        }
    }
}