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

package com.scarabcoder.spaceraiders.ship.state

import com.scarabcoder.doLater
import com.scarabcoder.gamecore.messaging.Message
import com.scarabcoder.gamecore.messaging.Messages
import com.scarabcoder.input.InputManager
import com.scarabcoder.input.InputType
import com.scarabcoder.input.PlayerInputEvent
import com.scarabcoder.nextElement
import com.scarabcoder.previousElement
import com.scarabcoder.spaceraiders.SpaceRaiders
import com.scarabcoder.spaceraiders.player.SRPlayer
import com.scarabcoder.spaceraiders.ship.Ship
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.bukkit.BukkitWorld
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

class HangarState(ship: Ship, members: MutableList<SRPlayer>): ShipState(ship, members, "In Hangar"), Listener {

    init {
        Bukkit.getPluginManager().registerEvents(this, SpaceRaiders.getPlugin())
    }

    private var leaveNextCrouch = false

    private var editingPart = Ship.PartType.HULL
        set(value) {
            if(members.isNotEmpty()){
                members[0].sendActionBar(Messages.msg(Message.EDITING_PART, value.name.toLowerCase().capitalize()))
                field = value
            }
        }

    @EventHandler
    private fun playerInputEvent(e: PlayerInputEvent){
        if(e.isKeyUp) return
        val p = SRPlayer.from(e.player)
        if(e.key == InputType.W || e.key == InputType.S){
            editingPart = if(editingPart == Ship.PartType.HULL) Ship.PartType.ENGINE else Ship.PartType.HULL
            p.sendMessage(Messages.msg(Message.EDITING_PART, editingPart.name.toLowerCase().capitalize()))
            return
        }
        if(e.key == InputType.D || e.key == InputType.A){
            if(editingPart == Ship.PartType.HULL){
                val (_, next) = if(e.key == InputType.D) ship.unlockedHulls.nextElement(ship.hull) else ship.unlockedHulls.previousElement(ship.hull)
                ship.hull = next
                p.sendActionBar("${ChatColor.GREEN}${next.displayName}")
            } else {
                val (_, next) = if(e.key == InputType.D) ship.unlockedEngines.nextElement(ship.engine) else ship.unlockedEngines.previousElement(ship.engine)
                ship.engine = next
                p.sendActionBar("${ChatColor.GREEN}${next.displayName}")
            }
            val l = ship.hangar.shipCenter
            ship.buildSchematic().paste(BukkitWorld(l.world), Vector(l.x, l.y, l.z))
            return
        }
        if(e.key == InputType.SHIFT){
            if(leaveNextCrouch){
                removeMember(members[0])
                return
            }
            leaveNextCrouch = true
            p.sendMessage(Messages.msg(Message.CROUCH_AGAIN_TO_LEAVE))
            doLater(5 * 20) { leaveNextCrouch = false }
            return
        }
        //TODO Open GUI on Spacebar


    }

    override val stateType = Ship.State.HANGAR

    override fun removeMember(player: SRPlayer) {
        InputManager.detach(player.player)
        player.player.teleport(player.preStateLocation)
        return super.removeMember(player)
    }

    override fun addMember(player: SRPlayer) {

        if(members.isEmpty()) {
            player.player.teleport(ship.hangar.editingLocation)
            InputManager.attach(player.player)
        }else {
            player.sendMessage(Messages.msg(Message.SHIP_BEING_EDITED, members[0].username))
            return
        }
        return super.addMember(player)
    }

    override fun onSwitch() {
        HandlerList.unregisterAll(this)
        super.onSwitch()
    }

}