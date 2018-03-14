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

package com.scarabcoder.spaceraiders.command
import com.scarabcoder.commandapi2.Command
import com.scarabcoder.commandapi2.CommandSection
import com.scarabcoder.gamecore.messaging.Message
import com.scarabcoder.gamecore.messaging.Messages
import com.scarabcoder.spaceraiders.InHangarValidator
import com.scarabcoder.spaceraiders.NotInActiveState
import com.scarabcoder.spaceraiders.player.SRPlayer
import java.util.*

class HangarCommand: CommandSection("hangar") {

    override val aliases: MutableList<String> = Arrays.asList("h", "hg")
    
    @Command(aliases = ["h"], validators = [NotInActiveState::class])
    fun home(sender: SRPlayer) {
        sender.sendMessage(Messages.msg(Message.TELEPORTING_TO, "your private hangar"))
        sender.player.teleport(sender.privateHangar.center)
    }

    @Command(aliases = ["l"], validators = [InHangarValidator::class])
    fun leave(sender: SRPlayer) {
        sender.currentShipState!!.removeMember(sender)
    }

}