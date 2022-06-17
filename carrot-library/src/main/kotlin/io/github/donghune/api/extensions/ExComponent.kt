package io.github.donghune.api.extensions

import net.md_5.bungee.api.chat.TextComponent


fun String.toComponent() : TextComponent {
    return TextComponent(this)
}