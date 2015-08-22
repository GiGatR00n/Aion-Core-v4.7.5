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
 * This packet is used to update current exp / recoverable exp / max exp values.
 *
 * @author Luno
 * @updated by alexa026
 */
public class SM_STATUPDATE_EXP extends AionServerPacket {

    private long currentExp;
    private long recoverableExp;
    private long maxExp;
    private long curBoostExp = 0;
    private long maxBoostExp = 0;
    private long eventExp = 0;

    /**
     * @param currentExp
     * @param recoverableExp
     * @param maxExp
     */
    public SM_STATUPDATE_EXP(long currentExp, long recoverableExp, long maxExp, long rep1, long rep2, long rep3) {
        this.currentExp = currentExp;
        this.recoverableExp = recoverableExp;
        this.maxExp = maxExp;
        curBoostExp = rep1;
        maxBoostExp = rep2;
        eventExp = rep3;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeQ(currentExp);
        writeQ(recoverableExp);
        writeQ(maxExp);
        writeQ(curBoostExp);
        writeQ(maxBoostExp);
        writeQ(eventExp);
    }
}
