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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.aionemu.gameserver.model.AbyssRankingResult;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author zdead, LokiReborn
 */
public class SM_ABYSS_RANKING_LEGIONS extends AionServerPacket {

    private List<AbyssRankingResult> data;
    private Race race;
    private int updateTime;
    private int sendData = 0;

    public SM_ABYSS_RANKING_LEGIONS(int updateTime, ArrayList<AbyssRankingResult> data, Race race) {
        this.updateTime = updateTime;
        this.data = data;
        this.race = race;
        this.sendData = 1;
    }

    public SM_ABYSS_RANKING_LEGIONS(int updateTime, Race race) {
        this.updateTime = updateTime;
        this.data = Collections.emptyList();
        this.race = race;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(race.getRaceId());// 0:Elyos 1:Asmo
        writeD(updateTime);// Date
        writeD(sendData);// 0:Nothing 1:Update Table
        writeD(sendData);// 0:Nothing 1:Update Table
        writeH(data.size());// list size
        for (AbyssRankingResult rs : data) {
            writeD(rs.getRankPos());// Current Rank
            writeD((rs.getOldRankPos() == 0) ? 76 : rs.getOldRankPos());// Old Rank
            writeD(rs.getLegionId());// Legion Id
            writeD(race.getRaceId());// 0:Elyos 1:Asmo
            writeC(rs.getLegionLevel());// Legion Level
            writeD(rs.getLegionMembers());// Legion Members
            writeQ(rs.getLegionCP());// Contribution Points
            writeS(rs.getLegionName(), 82);// Legion Name
        }
    }
}
