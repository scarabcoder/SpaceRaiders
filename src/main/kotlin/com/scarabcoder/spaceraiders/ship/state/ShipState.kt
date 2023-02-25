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

import com.scarabcoder.spaceraiders.player.SRPlayer
import com.scarabcoder.spaceraiders.ship.Ship

abstract class ShipState(val ship: Ship, members: List<SRPlayer>, val readableName: String) {


    private val _members: MutableList<SRPlayer> = members.toMutableList()
    init {
        //Run the addMember() function for players this state have been initialized with, so that sub-states that override the function can handle players joining a state.
        members.forEach { addMember(it) }
    }

    val members = _members as List<SRPlayer>

    open fun addMember(player: SRPlayer) {
        _members.add(player)
    }
    open fun removeMember(player: SRPlayer) {
        _members.remove(player)
    }

    init {
        members.forEach { it.preStateLocation = it.player.location }
    }

    abstract val stateType: Ship.State

    open fun onSwitch() {
        members.forEach { removeMember(it) }
    }


}