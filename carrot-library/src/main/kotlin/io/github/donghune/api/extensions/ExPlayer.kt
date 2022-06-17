package io.github.donghune.api.extensions

import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.inventory.PlayerInventory

fun PlayerInventory.clearArmor() {
    armorContents!!.filterNotNull().forEach {
        it.type = Material.AIR
    }
}

fun PlayerInventory.clearAll() {
    clear()
    clearArmor()
}

val Player.hasItemInHand get() = inventory.itemInMainHand.type != Material.AIR

fun Player.playSound(sound: Sound, volume: Float, pitch: Float) = playSound(location, sound, volume, pitch)
fun Player.playNote(instrument: Instrument, note: Note) = playNote(location, instrument, note)
fun <T> Player.playEffect(effect: Effect, data: T? = null) = playEffect(location, effect, data)

//fun CommandSender.msg(message: List<String>) = message.forEach { msg(it) }

fun Player.resetWalkSpeed() {
    this.walkSpeed = 0.2f
}

fun Player.resetFlySpeed() {
    this.flySpeed = 0.1f
}