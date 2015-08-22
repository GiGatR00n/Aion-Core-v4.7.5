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

import com.aionemu.gameserver.model.gameobjects.player.AbyssRank;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;

/**
 * @author Nemiroff Date: 25.01.2010
 * @author GiGatR00n v4.7.5.x 
 */
public class SM_ABYSS_RANK extends AionServerPacket {

    private AbyssRank rank;
    private int currentRankId;

    public SM_ABYSS_RANK(AbyssRank rank) {
        this.rank = rank;
        this.currentRankId = rank.getRank().getId();
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeQ(rank.getAp()); // curAP
        writeD(rank.getGp()); // curGP
        writeD(currentRankId); // curRank
        writeD(rank.getTopRanking()); // curRating

        if (currentRankId <= 9) {
            int nextRankId = currentRankId < AbyssRankEnum.values().length ? currentRankId + 1 : currentRankId;
            writeD(100 * rank.getAp() / AbyssRankEnum.getRankById(nextRankId).getRequired());
        } else if (currentRankId > 9 && currentRankId <= 18) {
            int nextGpRankId = currentRankId < AbyssRankEnum.values().length ? currentRankId + 1 : currentRankId;
            writeD(100 * rank.getGp() / AbyssRankEnum.getRankById(nextGpRankId).getRequired());
        }

        writeD(rank.getAllKill()); // allKill
        writeD(rank.getMaxRank()); // maxRank

        writeD(rank.getDailyKill()); // dayKill
        writeQ(rank.getDailyAP()); // dayAP
        writeD(rank.getDailyGP()); // dayGP

        writeD(rank.getWeeklyKill()); // weekKill
        writeQ(rank.getWeeklyAP()); // weekAP
        writeD(rank.getWeeklyGP()); // weekGP

        writeD(rank.getLastKill()); // laterKill
        writeQ(rank.getLastAP()); // laterAP
        writeD(rank.getLastGP()); // laterGP
    }
}
