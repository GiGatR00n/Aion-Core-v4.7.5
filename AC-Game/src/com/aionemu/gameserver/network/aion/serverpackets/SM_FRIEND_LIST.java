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

import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.HousingService;

/**
 * Sends a friend list to the client
 *
 * @author Ben
 */
public class SM_FRIEND_LIST extends AionServerPacket {

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        FriendList list = con.getActivePlayer().getFriendList();

        writeH((0 - list.getSize()));
        writeC(0);// unk

        for (Friend friend : list) {
            writeD(friend.getOid());
            writeS(friend.getName());
            writeD(friend.getLevel());
            writeD(friend.getPlayerClass().getClassId());
            writeC(friend.isOnline() ? 1 : 0);
            writeD(friend.getMapId());
            writeD(friend.getLastOnlineTime()); // Date friend was last online as a Unix timestamp.
            writeS(friend.getNote()); // Friend note
            writeC(friend.getStatus().getId());

            int address = HousingService.getInstance().getPlayerAddress(friend.getOid());
            if (address > 0) {
                House house = HousingService.getInstance().getPlayerStudio(friend.getOid());
                if (house == null) {
                    house = HousingService.getInstance().getHouseByAddress(address);
                    writeD(house.getAddress().getId());
                } else {
                    writeD(address);
                }
                writeC(house.getDoorState().getPacketValue());
            } else {
                writeD(0);
                writeC(0);
            }
        }
    }
}
