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

import com.aionemu.gameserver.model.team.legion.LegionEmblemType;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Simple modified cura
 */
public class SM_LEGION_SEND_EMBLEM extends AionServerPacket {

    /**
     * Legion information *
     */
    private int legionId;
    private int emblemId;
    private int color_r;
    private int color_g;
    private int color_b;
    private String legionName;
    private LegionEmblemType emblemType;
    private int emblemDataSize;

    /**
     * This constructor will handle legion emblem info
     *
     * @param legionId
     * @param emblemId
     * @param color_r
     * @param color_g
     * @param color_b
     * @param legionName
     * @param emblemType
     * @param emblemDataSize
     */
    public SM_LEGION_SEND_EMBLEM(int legionId, int emblemId, int color_r, int color_g, int color_b, String legionName,
                                 LegionEmblemType emblemType, int emblemDataSize) {
        this.legionId = legionId;
        this.emblemId = emblemId;
        this.color_r = color_r;
        this.color_g = color_g;
        this.color_b = color_b;
        this.legionName = legionName;
        this.emblemType = emblemType;
        this.emblemDataSize = emblemDataSize;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(legionId);
        writeC(emblemId);
        writeC(emblemType.getValue());
        writeD(emblemDataSize);
        writeC(emblemType.equals(LegionEmblemType.DEFAULT) ? 0x00 : 0xFF);
        writeC(color_r);
        writeC(color_g);
        writeC(color_b);
        writeS(legionName);
        writeC(0x01);
    }
}
