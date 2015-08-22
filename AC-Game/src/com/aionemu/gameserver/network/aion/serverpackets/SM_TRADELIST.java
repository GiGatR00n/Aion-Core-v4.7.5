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

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.limiteditems.LimitedItem;
import com.aionemu.gameserver.model.limiteditems.LimitedTradeNpc;
import com.aionemu.gameserver.model.templates.tradelist.TradeListTemplate;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.LimitedItemTradeService;

public class SM_TRADELIST extends AionServerPacket {
    private Integer playerObj;
    private int npcObj;
    private int npcId;
    private TradeListTemplate tlist;
    private int buyPriceModifier;

    public SM_TRADELIST(Player player, Npc npc, TradeListTemplate tlist, int buyPriceModifier) {
        this.playerObj = player.getObjectId();
        this.npcObj = npc.getObjectId().intValue();
        this.npcId = npc.getNpcId();
        this.tlist = tlist;
        this.buyPriceModifier = buyPriceModifier;
    }

    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        if ((tlist != null) && (tlist.getNpcId() != 0) && (tlist.getCount() != 0)) {
            writeD(this.npcObj);
            writeC(this.tlist.getTradeNpcType().index());
            writeD(this.buyPriceModifier);
            writeD(this.buyPriceModifier);
            writeC(1);
            writeC(1);
            writeH(this.tlist.getCount());
            for (TradeListTemplate.TradeTab tradeTabl : this.tlist.getTradeTablist()) {
                writeD(tradeTabl.getId());
            }

            int i = 0;
            LimitedTradeNpc limitedTradeNpc = null;
            if (LimitedItemTradeService.getInstance().isLimitedTradeNpc(this.npcId)) {
                limitedTradeNpc = LimitedItemTradeService.getInstance().getLimitedTradeNpc(this.npcId);
                i = limitedTradeNpc.getLimitedItems().size();
            }
            writeH(i);
            if (limitedTradeNpc != null)
                for (LimitedItem limitedItem : limitedTradeNpc.getLimitedItems()) {
                    writeD(limitedItem.getItemId());
                    writeH(limitedItem.getBuyCount().get(this.playerObj.intValue()) == null ? 0 : ((Integer) limitedItem.getBuyCount().get(this.playerObj.intValue())).intValue());
                    writeH(limitedItem.getSellLimit());
                }
        }
    }
}