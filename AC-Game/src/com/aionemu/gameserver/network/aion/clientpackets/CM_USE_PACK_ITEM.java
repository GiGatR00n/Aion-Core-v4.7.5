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

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INVENTORY_UPDATE_ITEM;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Ever'
 */
public class CM_USE_PACK_ITEM extends AionClientPacket {

    private int uniqueItemId;

    /**
     * Constructs new instance of <tt>CM_UNPACK_ITEM </tt> packet
     *
     * @param opcode
     */
    public CM_USE_PACK_ITEM(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        uniqueItemId = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        if (player == null) {
            return;
        }
        if (uniqueItemId == 0) {
            return;
        }
        if (player.isProtectionActive()) {
            player.getController().stopProtectionActiveTask();
        }
        Item item = player.getInventory().getItemByObjId(uniqueItemId);
        if (item == null) {
            return;
        }

        // check use item multicast delay exploit cast (spam)
        if (player.isCasting()) {
            player.getController().cancelCurrentSkill();
        }
        item.setPacked(false);
        item.setPersistentState(PersistentState.UPDATE_REQUIRED);
        PacketSendUtility.sendPacket(player, new SM_INVENTORY_UPDATE_ITEM(player, item));
    }
}
