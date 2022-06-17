package io.github.donghune.api.extensions

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.util.BoundingBox
import org.bukkit.util.Vector

fun BoundingBox.contains2(x: Double, y: Double, z: Double): Boolean {
    return x >= this.minX && x <= this.maxX && y >= this.minY && y <= this.maxY && z >= this.minZ && z <= this.maxZ
}

fun BoundingBox.contains2(position: Vector): Boolean {
    return this.contains2(position.x, position.y, position.z)
}

fun BoundingBox.toLocationList(world: World): List<Location> {
    val locationList = mutableListOf<Location>()
    for (x in minX.toInt()..maxX.toInt()) {
        for (y in minY.toInt()..maxY.toInt()) {
            for (z in minZ.toInt()..maxZ.toInt()) {
                locationList.add(Location(world, x.toDouble(), y.toDouble(), z.toDouble()))
            }
        }
    }
    return locationList
}