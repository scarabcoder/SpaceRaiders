/*
 * Copyright 2018 Nicholas Harris (ScarabCoder)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 *  to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *   OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *   OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.scarabcoder

import com.boydti.fawe.`object`.schematic.Schematic
import com.scarabcoder.spaceraiders.SpaceRaiders
import com.sk89q.worldedit.Vector
import org.bukkit.scheduler.BukkitRunnable

fun async(runnable: () -> Unit) {
    object: BukkitRunnable() {
        override fun run() {
            runnable()
        }

    }.runTaskAsynchronously(SpaceRaiders.getPlugin())
}

fun doLater(delay: Long, func: () -> Unit) {
    object: BukkitRunnable() {
        override fun run() {
            func()
        }
    }.runTaskLater(SpaceRaiders.getPlugin(), delay)
}

fun <E> Collection<E>.nextElement(current: E): Pair<Int, E> {
    if(!this.contains(current)) throw IllegalArgumentException("Collection does not contain element $current!")
    return Pair(indexOf(current) % size, elementAt((indexOf(current) + 1) % size))
}

fun <E> Collection<E>.previousElement(current: E): Pair<Int, E> {
    if(!this.contains(current)) throw IllegalArgumentException("Collection does not contain element $current!")
    return Pair(indexOf(current) % size, elementAt((indexOf(current) - 1) % size))
}

fun Vector.toBukkitVector(): org.bukkit.util.Vector = org.bukkit.util.Vector(this.x, this.y, this.z)

fun org.bukkit.util.Vector.fawe(): Vector = Vector(this.x, this.y, this.z)

fun Vector.iterate(max: Vector): Iterator<Vector> {
    val items = ArrayList<Vector>()
    for(x in this.x.toInt()..max.x.toInt()){
        for(y in this.y.toInt()..max.y.toInt()){
            for(z in this.z.toInt()..max.z.toInt()){
                items.add(Vector(x, y, z))
            }
        }
    }
    return items.iterator()
}

fun Schematic.mergeWith(other: Schematic, thisPoint: Vector = this.clipboard!!.origin, otherPoint: Vector = other.clipboard!!.origin): Schematic {
    val schem1 = this.clipboard!!
    val schem2 = other.clipboard!!

    val offset = schem1.origin!!

    for(bPos in schem2.minimumPoint.iterate(schem2.maximumPoint)){
        val b = schem2.getBlock(bPos)
        schem1.setBlock(bPos + offset, b)
    }
    return Schematic(schem1)
}

private operator fun Vector.plus(other: Vector): Vector {
    return this.add(other)
}
