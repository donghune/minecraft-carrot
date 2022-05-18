package io.github.donghune

import io.github.donghune.api.DHEPlugin
import io.github.donghune.api.extensions.mainWorld
import org.bukkit.Difficulty
import org.bukkit.GameRule

class VisitorPlugin : DHEPlugin() {
    override fun onEnable() {
        super.onEnable()

        mainWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        mainWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        mainWorld.difficulty = Difficulty.EASY

        VisitorCommand.initialize(this)
        VisitorListener.initialize(this)
    }

    override fun onDisable() {
        super.onDisable()
        VisitorManager.getVisitors().forEach { VisitorManager.successSellItem(it.first) }
    }
}

