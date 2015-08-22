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

import com.aionemu.gameserver.configs.main.SecurityConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PONG;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.audit.AuditLogger;


public class CM_PING_INGAME extends AionClientPacket {

    /**
     * Constructs new instance of <tt>CM_PING </tt> packet
     *
     * @param opcode
     */
    public CM_PING_INGAME(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        readH(); // unk
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        long lastMS = getConnection().getLastPingTimeMS();

        if (lastMS > 0 && player != null) {
            long pingInterval = System.currentTimeMillis() - lastMS;
            // PingInterval should be 3min (180000ms)
            if (pingInterval < SecurityConfig.PING_INTERVAL * 1000) {// client timer cheat
                AuditLogger.info(player, "Possible client timer cheat kicking player: " + pingInterval + ", ip=" + getConnection().getIP());
                if (SecurityConfig.SECURITY_ENABLE) {
                    PacketSendUtility.sendMessage(player, "You have been triggered Speed Hack detection so you're disconnected.");
                    getConnection().closeNow();
                }
            }
        }
        getConnection().setLastPingTimeMS(System.currentTimeMillis());
        sendPacket(new SM_PONG());
    }
}
