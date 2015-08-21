/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, ragarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning 
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package com.aionemu.gameserver.utils.chathandlers;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.ChatCommand;

/**
 * @author synchro2
 */
public abstract class WeddingCommand extends ChatCommand {

    public WeddingCommand(String alias) {
        super(alias);
    }

    @Override
    public boolean checkLevel(Player player) {
        return player.havePermission(getLevel());
    }

    @Override
    boolean process(Player player, String text) {
        if (!player.isMarried()) {
            return false;
        }
        String alias = this.getAlias();

        if (!checkLevel(player)) {
            PacketSendUtility.sendMessage(player, "You have no permission to use this command.");
            return true;
        }

        boolean success = false;
        if (text.length() == alias.length()) {
            success = this.run(player, EMPTY_PARAMS);
        } else {
            success = this.run(player, text.substring(alias.length() + 1).split(" "));
        }

        return success;
    }
}
