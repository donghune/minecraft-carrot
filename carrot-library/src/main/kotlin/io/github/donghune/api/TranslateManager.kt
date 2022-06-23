package io.github.donghune.api

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.bukkit.inventory.ItemStack
import java.io.File
import java.io.FileReader

object TranslateManager {
    private val translateTable = mutableMapOf<String, String>()

    fun load() {
        val translateFolder = File(plugin.dataFolder.path + "/../../translate")
        translateFolder.mkdir()
        val translateFiles = translateFolder.listFiles() ?: return
        translateFiles.forEach { file ->
            Json.decodeFromString<Map<String, String>>(FileReader(file).readText())
                .forEach { (key, value) -> translateTable[key] = value }
        }
    }

    fun translate(id: String): String? {
        return translateTable[id]
    }
}

val ItemStack.translate: String
    get() = TranslateManager.translate(resourceKey) ?: displayName