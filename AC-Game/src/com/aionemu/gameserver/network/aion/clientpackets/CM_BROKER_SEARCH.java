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

import java.util.ArrayList;
import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.BrokerService;

/**
 * @author GiGatR00n v4.7.5.x
 */
public class CM_BROKER_SEARCH extends AionClientPacket {

    @SuppressWarnings("unused")
    private int brokerId;
    private int sortType;
    private int page;
    private int mask;
    private int itemCount;
    private List<Integer> itemList;

    public CM_BROKER_SEARCH(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        this.brokerId = readD();
        this.sortType = readC(); // 1 - name; 2 - level; 4 - totalPrice; 6 - price for piece
        this.page = readH();
        this.mask = readH();
        this.itemCount = readH();
        this.itemList = new ArrayList<Integer>();

        for (int index = 0; index < this.itemCount; index++) {
            this.itemList.add(readD());
        }
    }

    @Override
    protected void runImpl() {
    	Player player = getConnection().getActivePlayer();
    	if (player == null) return;
        BrokerService.getInstance().showRequestedItems(player, mask, sortType, page, itemList);
    }
}
