package io.github.donghune.tycoon

import io.github.donghune.api.DHEPlugin
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Villager
import org.bukkit.inventory.ItemStack
import java.io.File
import java.util.*


class TycoonPlugin : DHEPlugin() {

    private val villageQueue: Queue<Villager> = LinkedList()
    private val locations: MutableList<Location> = mutableListOf()
    private val config = File(dataFolder, "locations.yml")

    override fun onEnable() {
        super.onEnable()

//        val yaml = YamlConfiguration.loadConfiguration(config)
//        val size = yaml.getInt("size")
//        repeat(size) {
//            yaml.getLocation("locations.$it")?.let { it1 -> locations.add(it1) }
//        }
//
//        val products = Material.values()
//            .map { Product(ItemStack(it), Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)) }
//
//        kommand {
//            register("tycoon") {
//                then("clear") {
//                    executes {
//                        locations.clear()
//                        player.sendMessage("clear locations")
//                    }
//                }
//                then("take") {
//                    executes {
//                        val entity = player.getTargetEntity(100) ?: return@executes
//                        val type = Material.valueOf(entity.getMetadata("type")[0].asString())
//                        player.inventory.setItemInMainHand(ItemStack(type))
//                    }
//                }
//                then("start") {
//                    executes {
//                        // move
//                        HeartbeatScope().launch {
//                            while (true) {
//                                villageQueue.forEachIndexed { index, villager ->
//                                    val entityLiving = (villager as CraftVillager).handle
//                                    val pathEntity =
//                                        entityLiving.D()
//                                            .a(locations[index].x, locations[index].y, locations[index].z, 1)
//                                    entityLiving.D().a(pathEntity, 1.0)
//                                }
//                                delay(100L)
//                            }
//                        }
//                    }
//                }
//
//                then("add") {
//                    executes {
//                        HeartbeatScope().launch {
//                            while(true) {
//                                if (villageQueue.size == locations.size - 1) {
//                                    val villager = villageQueue.poll()
//                                    villager.passengers.forEach { it.remove() }
//                                    villager.remove()
//                                }
//
//                                broadcast("손님이 찾아왔습니다".toComponent())
//
//                                // 소환
//                                val newVillager =
//                                    mainWorld.spawnEntity(locations.first(), EntityType.VILLAGER) as Villager
//                                val armorStand =
//                                    mainWorld.spawnEntity(locations.first(), EntityType.ARMOR_STAND) as ArmorStand
//                                val product = products.random()
//
//                                armorStand.isInvisible = true
//                                armorStand.setItem(EquipmentSlot.HEAD, product.itemStack)
//                                newVillager.addPassenger(armorStand)
//                                newVillager.setMetadata(
//                                    "type",
//                                    FixedMetadataValue(this@TycoonPlugin, product.itemStack.type.toString())
//                                )
//                                newVillager.setMetadata("price", FixedMetadataValue(this@TycoonPlugin, product.price))
//
//                                // 큐 추가
//                                villageQueue.add(newVillager)
//
//                                delay(1000L)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        registerEvents(object : Listener {
//            @EventHandler
//            fun a(event: PlayerInteractEvent) {
//                if (event.action != Action.RIGHT_CLICK_BLOCK) {
//                    return
//                }
//                if (event.hand != EquipmentSlot.HAND) {
//                    return
//                }
//                event.clickedBlock?.let {
//                    if (event.player.inventory.itemInMainHand.type != Material.STICK) {
//                        return
//                    }
//                    val location = event.player.location.toBlockLocation()
//                    locations.add(location)
//                    event.player.sendMessage(location.toString().toComponent())
//                    it.type = Material.DIAMOND_BLOCK
//
//                    event.isCancelled = true
//                }
//            }
//
//            @EventHandler
//            fun onSellItem(event: PlayerInteractEntityEvent) {
//                val entity = event.rightClicked
//                val handItem = event.player.inventory.itemInMainHand
//
//                if (entity !is Villager) {
//                    return
//                }
//
//                if (entity.uniqueId != villageQueue.peek().uniqueId) {
//                    return
//                }
//
//                val type = Material.valueOf(entity.getMetadata("type")[0].asString())
//                val price = entity.getMetadata("price")[0].asInt()
//
//                if (type != handItem.type) {
//                    return
//                }
//
//                event.player.inventory.setItemInMainHand(null)
//                entity.passengers.forEach { it.remove() }
//                entity.remove()
//                villageQueue.poll()
//
//                event.player.sendMessage(
//                    (ItemStack(type).displayName() as TranslatableComponent).append(
//                        Component.text(
//                            " 를 판매하여 $price 만큼 획득하였습니다."
//                        )
//                    )
//                )
//            }
//        })

    }

    override fun onDisable() {
        super.onDisable()
        val yaml = YamlConfiguration.loadConfiguration(config)
        yaml.set("size", locations.size)
        locations.forEachIndexed { index, location ->
            yaml.set("locations.$index", location)
        }
        yaml.save(config)
    }
}

data class Product(
    val itemStack: ItemStack,
    val price: Int
)