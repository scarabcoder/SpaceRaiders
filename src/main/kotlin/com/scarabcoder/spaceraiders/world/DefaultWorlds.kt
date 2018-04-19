/*
 * Copyright 2018 Nicholas Harris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 *  to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *  and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *  OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.scarabcoder.spaceraiders.world

import net.minecraft.server.v1_12_R1.WorldServer
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.craftbukkit.v1_12_R1.CraftChunk
import org.bukkit.craftbukkit.v1_12_R1.CraftServer
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld
import org.bukkit.event.EventHandler
import org.bukkit.event.world.ChunkLoadEvent
import sun.plugin.dom.exception.InvalidAccessException

enum class DefaultWorlds(val worldName: String) {

    AUTOGEN_HANGARS("generatedhangars");

    val isLoaded:Boolean
        get() {
            return Bukkit.getWorld(worldName) != null
        }

    val world: World
        get() {
            if(!isLoaded) throw Exception("World is not loaded!")
            return Bukkit.getWorld(worldName)
        }

    fun generateEmptyWorld(): World {
        val wc = WorldCreator(worldName)
        wc.type(WorldType.FLAT)
        wc.generatorSettings("2;0;1;")

        return wc.createWorld()
    }

    @EventHandler
    fun chunkLoadEvent(e: ChunkLoadEvent) {
        val clazz = CraftWorld::class.java
        val f = clazz.getField("world")
        f.isAccessible = true
        val ws = f.get(e.world) as WorldServer
       //ws.playerChunkMap.
    }
}