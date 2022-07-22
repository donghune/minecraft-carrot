package io.github.donghune.util

import io.github.donghune.api.getBoolean
import io.github.donghune.api.setTag
import org.bukkit.entity.Entity

var Entity.isCapableDamaged: Boolean
    get() = getBoolean("isCapableDamaged") ?: true
    set(value) = setTag("isCapableDamaged", value)