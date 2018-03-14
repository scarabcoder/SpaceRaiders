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

package com.scarabcoder.gamecore.messaging

import org.bukkit.ChatColor

enum class Message(val msg: String, vararg val placeholders: String) {

    UNKNOWN_COMMAND("${ChatColor.RED}Command not found!"),
    TYPE_ARGUMENT_CAST_ERROR("${ChatColor.RED}Error with argument %a: %e", "a", "p"),
    INVALID_USAGE("${ChatColor.RED}Invalid command usage. Usage: %p", "p"),
    IN_BATTLE_ERROR("${ChatColor.RED}You cannot teleport while in battle!"),
    IN_SHIP_STATE_ERROR("${ChatColor.RED}You cannot use this command while %s", "s"),
    TELEPORTING_TO("${ChatColor.GREEN}Teleporting to %l...", "l"),
    NOT_IN_REQ_STATE("${ChatColor.RED}You must be %s to use this command!", "s"),
    SHIP_BEING_EDITED("${ChatColor.RED}That ship is already being edited by %p", "p"),
    CROUCH_AGAIN_TO_LEAVE("${ChatColor.GOLD}Press shift again to stop editing the ship."),
    EDITING_PART("${ChatColor.AQUA}Editing part %p", "p");


    fun getConfigPath(): String {
        return this.name.toLowerCase().replace("_", "-")
    }

}