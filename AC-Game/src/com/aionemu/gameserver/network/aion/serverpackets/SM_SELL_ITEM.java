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

import com.aionemu.gameserver.model.templates.tradelist.TradeListTemplate;
import com.aionemu.gameserver.model.templates.tradelist.TradeNpcType;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author orz, Sarynth, modified by Artur
 */
public class SM_SELL_ITEM extends AionServerPacket {

    private int targetObjectId;
    private TradeListTemplate plist;
    private int sellPercentage;
    private byte action = 0x01;

    public SM_SELL_ITEM(int targetObjectId, int sellPercentage) {
        this.sellPercentage = sellPercentage;
        this.targetObjectId = targetObjectId;
    }

    public SM_SELL_ITEM(int targetObjectId, TradeListTemplate plist, int sellPercentage) {

        this.targetObjectId = targetObjectId;
        this.plist = plist;
        this.sellPercentage = sellPercentage;
        if (plist.getTradeNpcType() == TradeNpcType.ABYSS) {
            this.action = 0x02;
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        if ((this.plist != null) && (this.plist.getNpcId() != 0) && (this.plist.getCount() != 0)) {
            writeD(this.targetObjectId);
            writeC(this.plist.getTradeNpcType().index());
            writeD(this.sellPercentage);
            writeH(256);
            writeH(this.plist.getCount());
            for (TradeListTemplate.TradeTab tradeTabl : this.plist.getTradeTablist())
                writeD(tradeTabl.getId());
        } else {
            writeD(this.targetObjectId);
            writeD(5121);
            writeD(65792);
            writeC(0);
        }
    }
}
