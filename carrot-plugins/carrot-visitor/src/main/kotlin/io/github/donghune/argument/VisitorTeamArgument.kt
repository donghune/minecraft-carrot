package io.github.donghune.argument

import io.github.donghune.entity.VisitorConfigManager
import io.github.donghune.entity.VisitorTeam
import io.github.donghune.api.kommand.KommandContext
import io.github.donghune.api.kommand.argument.KommandArgument

class VisitorTeamArgument : KommandArgument<VisitorTeam> {
    override val parseFailMessage: String
        get() = "해당 팀을 찾지 못하였습니다"

    override fun parse(context: KommandContext, param: String): VisitorTeam? {
        return VisitorConfigManager.get().teams.find { it.name == param }
    }

    override fun listSuggestion(context: KommandContext, target: String): Collection<String> {
        return VisitorConfigManager.get().teams.map { it.name }
    }
}

fun visitorTeam() = VisitorTeamArgument()