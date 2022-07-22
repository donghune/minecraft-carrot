package io.github.donghune.entity

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.manager.Config
import io.github.donghune.api.manager.ConfigManager
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.util.*

val visitorConfig = VisitorConfigManager.get()

@Serializable
data class VisitorConfig(
    var salesItems: MutableList<SalesItem> = mutableListOf(),
    var openingMessage: String = "영업 시작",
    var closingMessage: String = "영업 종료",
    var specialGuestChance: Int = 10,
    var guestRetentionTime: Int = 10,
    var guestAppearanceTime: Int = 10,
    var playTime: Int = 60,
    var team: MutableList<VisitorTeam> = mutableListOf(),
    var markers: MutableList<@Contextual Location> = mutableListOf()
) : Config("VisitorConfig") {
    override fun update() {
        VisitorConfigManager.update(this)
    }
}

object VisitorConfigManager : ConfigManager<VisitorConfig>(
    "", VisitorConfig.serializer(), VisitorConfig()
)

@Serializable
data class SalesItem(
    var uuid: @Contextual UUID,
    var itemStack: @Contextual ItemStack,
    var amount: Int,
    var price: Int
) {
    fun toItemStack(): ItemStack {
        return ItemStackFactory(itemStack, true)
            .setAmount(amount)
            .addLore("해당가격: $price 에메랄드")
            .addLore("좌클릭 시 판매갯수 및 변동")
            .build()
    }
}

@Serializable
data class VisitorTeam(
    var name: String
)