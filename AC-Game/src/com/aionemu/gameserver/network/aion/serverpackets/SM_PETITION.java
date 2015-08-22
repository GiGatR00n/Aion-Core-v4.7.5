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

import com.aionemu.gameserver.model.Petition;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.PetitionService;

/**
 * @author zdead
 */
public class SM_PETITION extends AionServerPacket {

    private Petition petition;

    public SM_PETITION() {
        this.petition = null;
    }

    public SM_PETITION(Petition petition) {
        this.petition = petition;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        if (petition == null) {
            writeD(0x00);
            writeD(0x00);
            writeD(0x00);
            writeD(0x00);
            writeH(0x00);
            writeC(0x00);
        } else {
            writeC(0x01); // Action ID ?
            writeD(100); // unk (total online players ?)
            writeH(PetitionService.getInstance().getWaitingPlayers(con.getActivePlayer().getObjectId())); // Users
            // waiting for
            // Support
            writeS(Integer.toString(petition.getPetitionId())); // Ticket ID
            writeH(0x00);
            writeC(50); // Total Petitions
            writeC(49); // Remaining Petitions
            writeH(PetitionService.getInstance().calculateWaitTime(petition.getPlayerObjId())); // Estimated minutes
            // before GM reply
            writeD(0x00);
        }
    }
}
