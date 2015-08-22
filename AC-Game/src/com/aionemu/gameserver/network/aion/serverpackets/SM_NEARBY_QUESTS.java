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

import java.util.HashMap;
import java.util.Map.Entry;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author MrPoke, Rolandas
 */
public class SM_NEARBY_QUESTS extends AionServerPacket {

    private HashMap<Integer, Integer> nearbyQuestList;

    public SM_NEARBY_QUESTS(HashMap<Integer, Integer> nearbyQuestList) {
        this.nearbyQuestList = nearbyQuestList;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        if (nearbyQuestList == null || con.getActivePlayer() == null) {
            return;
        }

        writeC(0);
        writeH(-nearbyQuestList.size() & 0xFFFF);
        for (Entry<Integer, Integer> nearbyQuest : nearbyQuestList.entrySet()) {
            if (nearbyQuest.getValue() > 0) {
                writeH(nearbyQuest.getKey());
                writeH(0x02); // To show grey icons for future quests
            } else {
                // Quests are displayed on map
                writeD(nearbyQuest.getKey());
            }
        }
    }
}
