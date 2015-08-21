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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author KID
 */
public abstract class ChatCommand {

    private String alias;
    private Byte level;
    static final String[] EMPTY_PARAMS = new String[]{};
    static final Logger log = LoggerFactory.getLogger(ChatCommand.class);

    public ChatCommand(String alias) {
        this.alias = alias;
    }

    public boolean run(Player player, String... params) {
        try {
            execute(player, params);
            return true;
        } catch (Exception e) {
            log.error("", e);
            onFail(player, e.getMessage());
            return false;
        }
    }

    public final String getAlias() {
        return alias;
    }

    public void setAccessLevel(Byte level) {
        this.level = level;
    }

    public final Byte getLevel() {
        return level;
    }

    abstract boolean checkLevel(Player player);

    abstract boolean process(Player player, String text);

    public abstract void execute(Player player, String... params);

    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, message);
    }
}
