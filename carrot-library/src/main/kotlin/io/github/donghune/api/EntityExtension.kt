package io.github.donghune.api

import org.bukkit.entity.Entity
import org.bukkit.metadata.FixedMetadataValue

fun Entity.setTag(tag: String, value: Byte) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Short) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Int) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Long) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Float) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Double) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: String) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: ByteArray) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: IntArray) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: LongArray) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Any) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.getByte(tag: String): Byte? {
    return getMetadata(tag).firstOrNull()?.asByte()
}

fun Entity.getShort(tag: String): Short? {
    return getMetadata(tag).firstOrNull()?.asShort()
}

fun Entity.getInt(tag: String): Int? {
    return getMetadata(tag).firstOrNull()?.asInt()
}

fun Entity.getLong(tag: String): Long? {
    return getMetadata(tag).firstOrNull()?.asLong()
}

fun Entity.getFloat(tag: String): Float? {
    return getMetadata(tag).firstOrNull()?.asFloat()
}

fun Entity.getDouble(tag: String): Double? {
    return getMetadata(tag).firstOrNull()?.asDouble()
}

fun Entity.getString(tag: String): String? {
    return getMetadata(tag).firstOrNull()?.asString()
}

fun Entity.getBoolean(tag: String): Boolean? {
    return getMetadata(tag).firstOrNull()?.asBoolean()
}

inline fun <reified T> Entity.getObject(tag: String): T? {
    return getMetadata(tag).firstOrNull()?.value() as? T
}