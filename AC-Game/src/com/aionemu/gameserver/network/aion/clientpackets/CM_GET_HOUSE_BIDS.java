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
package com.aionemu.gameserver.network.aion.clientpackets;

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.HouseBidEntry;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_HOUSE_BIDS;
import com.aionemu.gameserver.services.HousingBidService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.collections.ListSplitter;

/**
 * @author Rolandas
 */
public class CM_GET_HOUSE_BIDS extends AionClientPacket {

    public CM_GET_HOUSE_BIDS(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        // No data
    }

    @Override
    protected void runImpl() {
    	boolean isFirst = true;
        Player player = getConnection().getActivePlayer();
        HouseBidEntry playerBid = HousingBidService.getInstance().getLastPlayerBid(player.getObjectId());

        List<HouseBidEntry> houseBids = HousingBidService.getInstance().getHouseBidEntries(player.getRace());
        ListSplitter<HouseBidEntry> splitter = new ListSplitter<HouseBidEntry>(houseBids, 181);
        while (!splitter.isLast()) {
            List<HouseBidEntry> packetBids = splitter.getNext();
            HouseBidEntry playerData = splitter.isLast() ? playerBid : null;
            PacketSendUtility.sendPacket(player, new SM_HOUSE_BIDS(isFirst, splitter.isLast(), playerData, packetBids));
            isFirst = false;
        }
    }
}
