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

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DRAWING_TOOL;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Maestros
 */
public class CM_DRAWING_TOOL extends AionClientPacket {

    private int action;
    private int unk2;
    @SuppressWarnings("unused")
    private int unk3;
    private int dataSize;
    private byte[] data;

    public CM_DRAWING_TOOL(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        action = readC();
        switch (action) {
            case 1:
                dataSize = readD();
                break;
            default:
                unk3 = readC();
                unk2 = readC();
                dataSize = readD();
        }
        if ((dataSize > 0) && (dataSize <= 5086)) {
            data = readB(dataSize);
        }
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        if ((player == null) || (data == null)) {
            return;
        }
        if (action == 1) {
            PacketSendUtility.sendPacket(player, new SM_DRAWING_TOOL(data));
            return;
        }
        switch (action) {
            case 0:
                PacketSendUtility.broadcastPacketToGroup(player, new SM_DRAWING_TOOL(data, action, unk2), true);
                break;
            case 1:
                PacketSendUtility.broadcastPacketToAlliance(player, new SM_DRAWING_TOOL(data, action, unk2), true);
        }
    }
}
