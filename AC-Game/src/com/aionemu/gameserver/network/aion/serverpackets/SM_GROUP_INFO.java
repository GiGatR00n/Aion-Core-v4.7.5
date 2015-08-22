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

import org.apache.commons.lang.StringUtils;

import com.aionemu.gameserver.model.team2.TeamType;
import com.aionemu.gameserver.model.team2.common.legacy.LootGroupRules;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Lyahim, ATracer, xTz
 */
public class SM_GROUP_INFO extends AionServerPacket {

    private LootGroupRules lootRules;
    private int groupId;
    private int leaderId;
    private int groupmapid;
    private TeamType type;

    public SM_GROUP_INFO(PlayerGroup group) {
        groupId = group.getObjectId();
        leaderId = group.getLeader().getObjectId();
        groupmapid = group.getLeaderObject().getWorldId();
        lootRules = group.getLootGroupRules();
        type = group.getTeamType();
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(groupId);
        writeD(leaderId);
        writeD(groupmapid);
        writeD(lootRules.getLootRule().getId());
        writeD(lootRules.getMisc());
        writeD(lootRules.getCommonItemAbove());
        writeD(lootRules.getSuperiorItemAbove());
        writeD(lootRules.getHeroicItemAbove());
        writeD(lootRules.getFabledItemAbove());
        writeD(lootRules.getEthernalItemAbove());
        writeD(lootRules.getAutodistribution().getId());
        writeD(2);
        writeC(0);
        writeD(type.getType());
        writeD(type.getSubType());
        writeH(0); //unk
        writeH(0); // message id
        writeS(StringUtils.EMPTY); // name
    }
}
