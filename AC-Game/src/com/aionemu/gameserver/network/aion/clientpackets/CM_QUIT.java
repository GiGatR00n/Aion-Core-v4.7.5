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
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.services.player.PlayerLeaveWorldService;

/**
 * In this packets aion client is asking if may quit.
 *
 * @author -Nemesiss-
 */
public class CM_QUIT extends AionClientPacket {

    /**
     * Logout - if true player is wanted to go to character selection.
     */
    private boolean logout;

    /**
     * Constructs new instance of <tt>CM_QUIT </tt> packet
     *
     * @param opcode
     */
    public CM_QUIT(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        logout = readC() == 1;
    }

    @Override
    protected void runImpl() {
        AionConnection client = getConnection();

        Player player = null;
        if (client.getState() == State.IN_GAME) {
            player = client.getActivePlayer();
            // TODO! check if may quit
            if (!logout) {
                LoginServer.getInstance().aionClientDisconnected(client.getAccount().getId());
            }

            PlayerLeaveWorldService.startLeaveWorld(player);
            client.setActivePlayer(null);
        }

        if (logout) {
            if (player != null && player.isInEditMode()) {
                sendPacket(new SM_QUIT_RESPONSE(true));
                player.setEditMode(false);
            } else {
                sendPacket(new SM_QUIT_RESPONSE());
            }
        } else {
            client.close(new SM_QUIT_RESPONSE(), false);
        }
    }
}
