package io.github.donghune

import io.github.donghune.api.extensions.*
import io.github.donghune.api.plugin
import io.github.donghune.api.setTag
import io.github.donghune.api.translate
import io.github.donghune.entity.SalesItem
import io.github.donghune.entity.VisitorMeta
import io.github.donghune.entity.visitorConfig
import io.github.donghune.scheudler.GameTimer
import io.github.donghune.scheudler.VisitorTimer
import io.github.donghune.util.isCapableDamaged
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Rabbit
import org.bukkit.entity.Villager
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import kotlin.random.Random
import kotlin.random.nextInt

object VisitorManager {

    private var isPlaying = false
    private val villagerByArmorStand = mutableMapOf<Villager, ArmorStand>()
    private val villagerByVisitorTimer = mutableMapOf<Villager, VisitorTimer>()

    fun getVisitors() = villagerByVisitorTimer.keys

    fun spawn(location: Location) {
        // spawn villager entity with by location
        val villager = location.spawn<Villager>()
        val rabbit = location.spawn<Rabbit>()
        val armorStand = location.clone().apply { y += 0.5 }.spawn<ArmorStand>()
        val item = location.dropItem(ItemStack(Material.STONE))
        val salesItem = visitorConfig.salesItems.random()
        val visitorMeta = VisitorMeta(
            isSpecial = Random.nextInt(1..100) <= visitorConfig.specialGuestChance,
            salesItem = SalesItem(
                salesItem.uuid,
                salesItem.itemStack,
                Random.nextInt(1..salesItem.amount),
                salesItem.price
            )
        )

        armorStand.isCapableDamaged = false
        armorStand.setGravity(false)
        armorStand.isVisible = false
        armorStand.customName = "&c00:00".chatColor()
        armorStand.isCustomNameVisible = true

        villagerByVisitorTimer[villager] = VisitorTimer(
            armorStand = armorStand,
            onFinish = {
                remove(villager)
            }
        )
        villagerByVisitorTimer[villager]?.start(visitorConfig.guestRetentionTime)

        item.isCapableDamaged = false
        item.itemStack = visitorMeta.salesItem.itemStack.clone().apply { amount = 1 }
        item.customName = item.itemStack.translate + " " + visitorMeta.salesItem.amount + "개"
        item.pickupDelay = Int.MAX_VALUE
        item.isCustomNameVisible = true

        rabbit.isCapableDamaged = false
        rabbit.isInvisible = true
        rabbit.addPassenger(item)

        villager.isCapableDamaged = false
        villager.setTag("VisitorMeta", visitorMeta)
        villager.addPassenger(rabbit)
        villager.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.baseValue = 0.0
        villager.villagerType = Villager.Type.values().random()

        if (visitorMeta.isSpecial) {
            villager.addPotionEffect(
                PotionEffect(
                    PotionEffectType.GLOWING,
                    Int.MAX_VALUE,
                    Int.MAX_VALUE,
                    false,
                    false,
                    false
                )
            )
        }

        villagerByArmorStand[villager] = armorStand
    }

    fun remove(villager: Villager) {
        val armorStand = villagerByArmorStand[villager]
        armorStand?.remove()

        villagerByVisitorTimer[villager]?.cancel()
        villagerByArmorStand.remove(villager)
        villagerByVisitorTimer.remove(villager)

        villager.passengers.forEach { rabbit ->
            rabbit.passengers.forEach { item -> item.remove() }
            rabbit.remove()
        }
        villager.remove()

        if (isPlaying) {
            Bukkit.getScheduler().runTaskLater(
                plugin,
                Runnable { spawn(villager.location) },
                visitorConfig.guestAppearanceTime * 20L
            )
        }
    }

    private lateinit var gameTimer: GameTimer

    fun start() {
        isPlaying = true
        gameTimer = GameTimer()
        gameTimer.start(visitorConfig.playTime)
    }

    fun stop() {
        isPlaying = false
        gameTimer.stop()
    }
}