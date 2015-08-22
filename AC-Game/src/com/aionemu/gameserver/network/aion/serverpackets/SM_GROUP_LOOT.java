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

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Rhys2002
 */
public class SM_GROUP_LOOT extends AionServerPacket {

    private int groupId;
    private int index;
    private int unk2;
    private int itemId;
    private int unk3;
    private int lootCorpseId;
    private int distributionId;
    private int playerId;
    private long luck;

    /**
     * @param Player Id must be 0 to start the Roll Options
     */
    public SM_GROUP_LOOT(int groupId, int playerId, int itemId, int lootCorpseId, int distributionId, long luck, int index) {
        this.groupId = groupId;
        this.index = index;
        this.unk2 = 1;
        this.itemId = itemId;
        this.unk3 = 0;
        this.lootCorpseId = lootCorpseId;
        this.distributionId = distributionId;
        this.playerId = playerId;
        this.luck = luck;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(groupId);
        writeD(index);
        writeD(unk2);
        writeD(itemId);
        writeC(unk3);
        writeC(0); // 3.0
        writeC(0); // 3.5
        writeD(lootCorpseId);
        writeC(distributionId);
        writeD(playerId);
        writeD((int) luck);
    }
}
