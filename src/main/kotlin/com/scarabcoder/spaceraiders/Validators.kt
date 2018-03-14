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

package com.scarabcoder.spaceraiders

import com.scarabcoder.commandapi2.CommandValidator
import com.scarabcoder.gamecore.messaging.Message
import com.scarabcoder.gamecore.messaging.Messages
import com.scarabcoder.spaceraiders.data.DataManager
import com.scarabcoder.spaceraiders.player.SRPlayer
import com.scarabcoder.spaceraiders.ship.Ship

object NotInActiveState: CommandValidator<SRPlayer> {
    override fun validate(sender: SRPlayer): Boolean {
        if(sender.currentShipState != null){
            sender.sendMessage(Messages.msg(Message.IN_SHIP_STATE_ERROR, sender.currentShipState!!.readableName.toLowerCase()))
            return false
        }
        return true
    }
}

object InHangarValidator: CommandValidator<SRPlayer> {

    override fun validate(sender: SRPlayer): Boolean {
        if(sender.currentShipState != null && sender.currentShipState!!.stateType == Ship.State.HANGAR) return true
        sender.sendMessage(Messages.msg(Message.NOT_IN_REQ_STATE, "in hangar"))
        return false
    }
}

fun registerValidators(){
    CommandValidator.registerValidator(NotInActiveState)
}