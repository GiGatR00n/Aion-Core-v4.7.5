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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;

/**
 * Handler for "/loc" command
 *
 * @author Antraxx
 * @modify Ever
 */
public class CM_CLIENT_COMMAND_LOC extends AionClientPacket {

    private static final Logger log = LoggerFactory.getLogger(CM_CLIENT_COMMAND_LOC.class);

    public CM_CLIENT_COMMAND_LOC(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
    }

    @Override
    protected void runImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        Player player = getConnection().getActivePlayer();
        if (player == null) {
            return;
        }

        log.info("Received \"/loc\" command");

        player.getPosition();
        sendPacket(SM_SYSTEM_MESSAGE.STR_CMD_LOCATION_DESC(player.getPosition().getMapId(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
    }
}
