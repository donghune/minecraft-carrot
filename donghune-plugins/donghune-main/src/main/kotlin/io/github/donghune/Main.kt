package io.github.donghune

import com.destroystokyo.paper.event.entity.ProjectileCollideEvent
import eu.endercentral.crazy_advancements.advancement.AdvancementDisplay
import eu.endercentral.crazy_advancements.advancement.ToastNotification
import io.github.donghune.api.DHEPlugin
import io.github.donghune.api.extensions.*
import io.github.donghune.api.extensions.util.randomID
import io.github.monun.heartbeat.coroutines.HeartbeatScope
import io.github.monun.kommand.kommand
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.*
import org.bukkit.Bukkit.broadcast
import org.bukkit.block.Chest
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.server.ServerListPingEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector
import org.spigotmc.event.entity.EntityMountEvent

class Main : DHEPlugin() {
    override fun onEnable() {
        super.onEnable()

        mainWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        mainWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        mainWorld.difficulty = Difficulty.EASY

        HeartbeatScope().launch {
            while (true) {
                delay(100L)
                mainWorld.entities
                    .filterIsInstance<Item>()
                    .forEach {
                        it.customName(
                            it.itemStack.displayName().append(" ".toComponent())
                                .append(it.itemStack.amount.toString().toComponent()).append("개".toComponent())
                        )
                        it.isCustomNameVisible = true
                    }
            }
        }

        registerEvents(object : Listener {

            @EventHandler
            fun on(event: ServerListPingEvent) {
                event.motd(Component.text("모코코를 좋아하는 개발자가 운영중인 서버").color(NamedTextColor.GREEN))
            }

            @EventHandler
            fun join(event: PlayerJoinEvent) {
                Component.text()
                    .color(NamedTextColor.GREEN)
                    .append("기분 좋은 향기가 솔솔 피어나는 ".toComponent())
                    .append(event.player.displayName())
                    .append(" 님이 접속하셨어요~!".toComponent())
                    .build()
                    .also { broadcast(it) }

                event.player.setResourcePack(
                    "https://download.mc-packs.net/pack/6813bedcb5a3ed35137d916b948f7241acee24b0.zip",
                    "6813bedcb5a3ed35137d916b948f7241acee24b0"
                )
            }

            @EventHandler
            fun a(event: PlayerInteractEvent) {
                val block = event.player.getTargetBlock(10)
                if (block?.type == Material.CHISELED_STONE_BRICKS) {
                    if (event.player.inventory.itemInMainHand.type == Material.SEA_PICKLE) {
                        block.location.playEffect(Effect.DOOR_TOGGLE, 1)
                        BoundingBox.of(
                            block.location.clone().add(1.0, 1.0, 1.0),
                            block.location.clone().add(-1.0, -1.0, -1.0)
                        ).toLocationList(mainWorld)
                            .forEach { it.block.type = Material.AIR }
                        event.player.inventory.setItemInMainHand(null)
                    }
                } else if (block?.type == Material.CHEST) {
                    if (block.location.getNearbyEntities(7.0, 7.0, 7.0).filterIsInstance<Husk>().isNotEmpty()) {
                        event.isCancelled = true
                        event.player.sendMessage("주변의 좀비를 모두 죽여야 상자를 열 수 있습니다!")
                    }
                }
            }

//            @EventHandler
//            fun b(event: PlayerInteractEntityEvent) {
//                event.rightClicked.addPassenger(event.player)
//            }

            @EventHandler
            fun d(event: ProjectileCollideEvent) {
                event.entity.location.multiply(-1.0)
            }

            @EventHandler
            fun e(event: EntityMountEvent) {
            }

            @EventHandler
            fun c(event: ProjectileLaunchEvent) {
//                HeartbeatScope().launch {
//                    while (event.entity.doesBounce().not()) {
//                        event.location.clone().also { location ->
//                            task(10L) {
//                                location.block.type = Material.values().filter { it.name.endsWith("WOOL") }.random()
//                            }
//                        }
//                        delay(10L)
//                    }
//                }
            }

            @EventHandler
            fun Damage(e: EntityDamageByEntityEvent) {
                val le = e.entity
                if (le.type != EntityType.PLAYER) {
                    object : BukkitRunnable() {
                        override fun run() {
                            le.velocity = Vector()
                        }
                    }.runTaskLater(this@Main, 1L)
                }
            }
        })

        kommand {
            register("screenshot") {
                executes {

                }
            }
            register("fish") {
                executes {
                    val entity = mainWorld.spawnEntity(player.location, EntityType.DOLPHIN)
                    entity.addPassenger(
                        mainWorld.spawnEntity(player.location, EntityType.DOLPHIN).apply {
                            addPassenger(
                                mainWorld.spawnEntity(
                                    player.location,
                                    EntityType.DOLPHIN
                                )
                            ).apply {
                                addPassenger(
                                    mainWorld.spawnEntity(
                                        player.location,
                                        EntityType.DOLPHIN
                                    )
                                ).apply {
                                    addPassenger(
                                        mainWorld.spawnEntity(
                                            player.location,
                                            EntityType.DOLPHIN
                                        )
                                    ).apply {
                                        addPassenger(
                                            mainWorld.spawnEntity(
                                                player.location,
                                                EntityType.DOLPHIN
                                            )
                                        ).apply {
                                            addPassenger(
                                                mainWorld.spawnEntity(
                                                    player.location,
                                                    EntityType.DOLPHIN
                                                )
                                            ).apply {
                                                addPassenger(
                                                    mainWorld.spawnEntity(
                                                        player.location,
                                                        EntityType.DOLPHIN
                                                    )
                                                ).apply {
                                                    addPassenger(
                                                        mainWorld.spawnEntity(
                                                            player.location,
                                                            EntityType.DOLPHIN
                                                        )
                                                    ).apply {
                                                        addPassenger(
                                                            mainWorld.spawnEntity(
                                                                player.location,
                                                                EntityType.DOLPHIN
                                                            )
                                                        ).apply {
                                                            addPassenger(
                                                                mainWorld.spawnEntity(
                                                                    player.location,
                                                                    EntityType.DOLPHIN
                                                                )
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        })
                }
            }
            register("hmm") {
                executes {
                    player.openInventory(
                        Bukkit.createInventory(
                            null,
                            54,
                            "".toComponent().color(NamedTextColor.WHITE).append("\uE001".toComponent())
                        )
                    )

                }
            }
            register("b") {
                executes {
                    ToastNotification(
                        ItemStack(Material.POTATO),
                        "퀘스트 달성\n대홍단 왕감자를 찾아라!",
                        AdvancementDisplay.AdvancementFrame.GOAL
                    ).send(player)
                }
            }
            register("a") {
                executes {
                    AdvancementDisplay.AdvancementFrame.values().forEach {
                        ToastNotification(
                            ItemStack(Material.values().random()),
                            randomID(32),
                            it
                        ).send(player)
                    }
                }
            }
            register("dungeon") {
                executes {
                    val dungeon = Json.decodeFromString<Dungeon>(data)
                    val baseY = (player.location.blockY - 20)

                    val chunkX = player.location.chunk.x
                    val chunkZ = player.location.chunk.z

                    val height = dungeon.size?.height ?: 0
                    val width = dungeon.size?.width ?: 0

                    val wools = Material.values().filter { it.name.endsWith("WOOL") }

                    for (x in chunkX..(chunkX + width)) {
                        for (z in chunkZ..(chunkZ + height)) {
                            val chunk = world.getChunkAt(x, z)
                            chunk.toBoundingBox().toLocationList(world).forEach { it.block.type = Material.AIR }
                        }
                    }

                    dungeon.rooms?.forEachIndexed { index, room ->
                        val chunk = world.getChunkAt(chunkX + (room.x ?: 0), chunkZ + (room.y ?: 0))

                        if (room.entrance == true) {
                            chunk.toBoundingBox().toLocationList(world).filter { it.blockY == baseY + 1 }
                                .forEach { it.block.type = Material.BEDROCK }
                            player.teleport(chunk.toBoundingBox().center.toLocation(world))
                            player.showTitle(
                                Title.title(
                                    "좀비던전".toComponent(),
                                    "살아남으세요!".toComponent()
                                )
                            )
                        } else if (room.exit == true) {
                            chunk.toBoundingBox().toLocationList(world).filter { it.blockY == baseY + 1 }
                                .forEach { it.block.type = Material.BEDROCK }
                        } else {
                            chunk.toBoundingBox().toLocationList(world).filter { it.blockY == baseY + 1 }
                                .forEach { it.block.type = Material.STONE }
                        }

                        // make wall
                        val box = chunk.toBoundingBox()
                        box.toLocationList(world).filter {
                            (it.blockX == box.maxX.toInt() || it.blockX == box.minX.toInt() || it.blockZ == box.maxZ.toInt() || it.blockZ == box.minZ.toInt()) && (it.blockY in (baseY + 2)..(baseY + 10))
                        }.forEach {
                            it.block.type = Material.STONE
                        }

                        val centerBox = BoundingBox(
                            chunk.toBoundingBox().minX,
                            (baseY + 2).toDouble(),
                            chunk.toBoundingBox().minZ,
                            chunk.toBoundingBox().maxX,
                            (baseY + 2).toDouble(),
                            chunk.toBoundingBox().maxZ,
                        )

                        centerBox.center.toLocation(world).block.type = Material.CHEST
                        val chest = centerBox.center.toLocation(world).block.state as Chest

                        chest.inventory.addItem(ItemStack(Material.SEA_PICKLE))

                        world.spawnEntity(
                            centerBox.center.toLocation(world).clone().add(0.0, 1.0, 0.0),
                            EntityType.HUSK
                        )

                        if (room.doors?.n != null) { // 북
                            centerBox.center.toLocation(world).clone().add(1.0, 1.0, -8.0).block.type =
                                Material.CHISELED_STONE_BRICKS
                        }
                        if (room.doors?.s != null) { // 남
                            centerBox.center.toLocation(world).clone().add(0.0, 1.0, 8.0).block.type =
                                Material.CHISELED_STONE_BRICKS

                        }
                        if (room.doors?.w != null) { // 서
                            centerBox.center.toLocation(world).clone().add(-8.0, 1.0, 0.0).block.type =
                                Material.CHISELED_STONE_BRICKS
                        }
                        if (room.doors?.e != null) { // 동
                            centerBox.center.toLocation(world).clone().add(8.0, 1.0, 0.0).block.type =
                                Material.CHISELED_STONE_BRICKS
                        }
                    }

                    dungeon.keys?.forEachIndexed { index, key ->
                        val room = dungeon.rooms?.find { it.id == key.location } ?: return@executes
                        val chunk = world.getChunkAt(chunkX + (room.x ?: 0), chunkZ + (room.y ?: 0))

                        chunk.toBoundingBox().toLocationList(world).filter { it.blockY == baseY + 1 }
                            .forEach { it.block.type = wools[index] }
                    }
                }
            }
        }
    }
}

fun Chunk.toBoundingBox(): BoundingBox {
    val bx: Int = x shl 4
    val bz: Int = z shl 4

    return BoundingBox(
        (bx).toDouble(), (0).toDouble(), (bz).toDouble(), (bx + 16).toDouble(), (256).toDouble(), (bz + 16).toDouble()
    )
}


val data = """
{"size":{"width":4,"height":2},"terminals":{"entrance":0,"exit":3,"deadends":[3,4]},"rooms":[{"id":0,"x":1,"y":0,"keyInRoom":null,"template":"C2","distance":0,"exit":false,"entrance":true,"doors":{"n":null,"e":0,"s":null,"w":1}},{"id":1,"x":2,"y":0,"keyInRoom":1,"template":"C2","distance":1,"exit":false,"entrance":false,"doors":{"n":null,"e":3,"s":null,"w":0}},{"id":2,"x":0,"y":0,"keyInRoom":null,"template":"B2","distance":1,"exit":false,"entrance":false,"doors":{"n":null,"e":1,"s":2,"w":null}},{"id":3,"x":0,"y":1,"keyInRoom":null,"template":"D1","distance":2,"exit":true,"entrance":false,"doors":{"n":2,"e":null,"s":null,"w":null}},{"id":4,"x":3,"y":0,"keyInRoom":0,"template":"D4","distance":2,"exit":false,"entrance":false,"doors":{"n":null,"e":null,"s":null,"w":3}}],"doors":[{"id":0,"key":null,"orientation":"h","exit":false,"rooms":[0,1]},{"id":1,"key":null,"orientation":"h","exit":false,"rooms":[0,2]},{"id":2,"key":0,"orientation":"v","exit":true,"rooms":[2,3]},{"id":3,"key":1,"orientation":"h","exit":false,"rooms":[1,4]}],"keys":[{"id":0,"location":4,"door":2},{"id":1,"location":1,"door":3}]}
""".trimIndent()