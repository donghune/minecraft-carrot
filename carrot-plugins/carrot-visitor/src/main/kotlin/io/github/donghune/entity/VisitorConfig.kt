package io.github.donghune.entity

import io.github.donghune.api.extensions.emptyLocation
import io.github.donghune.api.manager.Config
import io.github.donghune.api.manager.ConfigManager
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.md_5.bungee.api.ChatMessageType
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.math.max
import kotlin.math.min

val visitorConfig = VisitorConfigManager.get()

object VisitorConfigManager : ConfigManager<VisitorConfig>(
    "", VisitorConfig.serializer(), VisitorConfig()
)

@Serializable
data class VisitorConfig(
    var locations: VisitorLocation = VisitorLocation(),
    var gameTime: Int = 20,
    var visitorOption: VisitorOption = VisitorOption(),
    var bossBarTimer: BossBarTimer = BossBarTimer(),
    var messages: Message = Message(),
    var comboReward: MutableList<ComboReward> = mutableListOf(
        ComboReward(5, 9, 1),
        ComboReward(10, 14, 2),
        ComboReward(15, 16, 3)
    ),
    var teams: MutableList<VisitorTeam> = mutableListOf()
) : Config("VisitorConfig") {
    override fun update() {
        VisitorConfigManager.update(this)
    }
}

@Serializable
data class VisitorLocation(
    var startLocation: @Contextual Location = emptyLocation(),
    var endLocation: @Contextual Location = emptyLocation(),
)

@Serializable
data class VisitorOption(
    var spawnDelay: Int = 20,
    var waitDelay: Int = 10,
    var walkSpeed: Double = 0.1,
    var specialChance: Int = 10,
    var specialPercent: Int = 10,
    var items: MutableList<VisitorItem> = mutableListOf(),
)

@Serializable
data class VisitorItem(
    val itemStack: @Contextual ItemStack,
    val maxAmount: Int,
    val price: Int
)

@Serializable
data class Message(
    var tradeSuccessMessage: MutableList<String> = mutableListOf("&a물건을 팔아줘서 고맙습니다."),
    var tradeSuccessType: ChatMessageType = ChatMessageType.ACTION_BAR,
    var tradeFailMessage: String = "&c이건 내가 원하는 물건이 아냐",
    var tradeFailType: ChatMessageType = ChatMessageType.ACTION_BAR,
    var hitAroundMessage: MutableList<String> = mutableListOf("&c떼잉.. 얼마나 기다려야 하나!!!"),
    var hitAroundType: ChatMessageType = ChatMessageType.ACTION_BAR,
    var startMessage: String = "&a영업 시작",
    var endMessage: String = "&c영업 종료"
)

@Serializable
data class BossBarTimer(
    var isVisible: Boolean = true,
    var barColor: BarColor = BarColor.GREEN,
    var barStyle: BarStyle = BarStyle.SOLID
)

@Serializable
data class ComboReward(
    var from: Int,
    var to: Int,
    var count: Int
)

@Serializable
data class VisitorTeam(
    var name: String = "",
    var anchorLocations: MutableList<@Contextual Location> = mutableListOf()
) {
    var playerList: MutableList<@Contextual UUID> = mutableListOf()
    var consecutiveSales: Int = 0
        set(value) {
            field = max(0, min(value, 15))
            playerList.forEach { Bukkit.getPlayer(it)?.level = field }
        }
}

fun Player.team(): VisitorTeam? {
    return VisitorConfigManager.get().teams.find { it.playerList.contains(uniqueId) }
}

