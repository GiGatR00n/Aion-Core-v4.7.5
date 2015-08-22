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
package com.aionemu.gameserver.network.aion.serverpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * Sent to update a player's status in a friendlist
 *
 * @author Ben
 */
public class SM_FRIEND_UPDATE extends AionServerPacket {

    private int friendObjId;
    private static Logger log = LoggerFactory.getLogger(SM_FRIEND_UPDATE.class);

    public SM_FRIEND_UPDATE(int friendObjId) {
        this.friendObjId = friendObjId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        Friend f = con.getActivePlayer().getFriendList().getFriend(friendObjId);
        if (f == null) {
            log.debug("Attempted to update friend list status of " + friendObjId + " for " + con.getActivePlayer().getName()
                    + " - object ID not found on friend list");
        } else {
            writeS(f.getName());
            writeD(f.getLevel());
            writeD(f.getPlayerClass().getClassId());
            writeC(f.isOnline() ? 1 : 0); // Online status - No idea why this and f.getStatus are used
            writeD(f.getMapId());
            writeD(f.getLastOnlineTime()); // Date friend was last online as a Unix timestamp.
            writeS(f.getNote());
            writeC(f.getStatus().getId());
        }
    }
}
