package io.github.donghune.api.inventory

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

val playerByGUI = mutableMapOf<Player, GUI>()

fun Player.updateGUI() {
    playerByGUI[this]?.refreshContent()
}

abstract class GUI private constructor(
    private val plugin: JavaPlugin,
    private val title: String,
) : Listener {

    constructor(
        plugin: JavaPlugin,
        inventoryType: InventoryType,
        title: String,
    ) : this(plugin, title) {
        inventory = Bukkit.createInventory(null, inventoryType, title)
    }

    constructor(
        plugin: JavaPlugin,
        size: Int,
        title: String,
    ) : this(plugin, title) {
        inventory = Bukkit.createInventory(null, size, title)
    }

    private val clickEvents: MutableMap<Int, (InventoryClickEvent) -> Unit> = mutableMapOf()
    protected lateinit var inventory: Inventory
    lateinit var player: Player

    fun content(block: () -> Unit) {
        content = block
    }

    fun onInventoryClose(block: InventoryCloseEvent.() -> Unit) {
        onInventoryClose = block
    }

    fun onInventoryOpen(block: InventoryOpenEvent.() -> Unit) {
        onInventoryOpen = block
    }

    fun onPlayerInventoryClick(isCancelled: Boolean = false, block: InventoryClickEvent.() -> Unit) {
        onPlayerInventoryClick = isCancelled to block
    }


    private var content: () -> Unit = {}
    private var onInventoryClose: InventoryCloseEvent.() -> Unit = {}
    private var onInventoryOpen: InventoryOpenEvent.() -> Unit = {}
    private var onPlayerInventoryClick: Pair<Boolean, InventoryClickEvent.() -> Unit> = false to {}

    @EventHandler
    fun onInventoryOpenEvent(event: InventoryOpenEvent) {
        if (event.inventory != inventory) {
            return
        }

        playerByGUI[event.player as Player] = this
        onInventoryOpen(event)
    }

    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {
        if (event.clickedInventory == player.inventory && player.openInventory.topInventory == inventory) {
            event.isCancelled = onPlayerInventoryClick.first
            onPlayerInventoryClick.second(event)
            return
        }

        if (event.clickedInventory != inventory) {
            return
        }

        clickEvents[event.rawSlot]?.invoke(event)
    }

    @EventHandler
    fun onInventoryCloseEvent(event: InventoryCloseEvent) {
        if (event.inventory != inventory) {
            return
        }

        playerByGUI.remove(event.player as Player)
        onInventoryClose(event)
        InventoryCloseEvent.getHandlerList().unregister(this)
        InventoryClickEvent.getHandlerList().unregister(this)
        InventoryOpenEvent.getHandlerList().unregister(this)
    }

    fun refreshContent() {
        inventory.clear()
        InventoryClickEvent.getHandlerList().unregister(this@GUI)
        Bukkit.getPluginManager().registerEvents(this@GUI, plugin)
        content()
    }

    fun open(player: Player) {
        Bukkit.getPluginManager().registerEvents(this@GUI, plugin)
        this@GUI.player = player
        player.openInventory(inventory)
        content()
    }

    fun openLater(player: Player) {
        open(player)
    }

    fun setItem(index: Int, itemStack: ItemStack, onClick: InventoryClickEvent.() -> Unit = {}) {
        inventory.setItem(index, itemStack)
        clickEvents[index] = onClick
    }

}