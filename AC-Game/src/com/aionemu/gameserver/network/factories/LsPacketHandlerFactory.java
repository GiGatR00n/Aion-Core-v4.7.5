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
package com.aionemu.gameserver.network.factories;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection.State;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;
import com.aionemu.gameserver.network.loginserver.LsPacketHandler;
import com.aionemu.gameserver.network.loginserver.clientpackets.*;

/**
 * @author Luno
 */
public class LsPacketHandlerFactory {

    private LsPacketHandler handler = new LsPacketHandler();

    public static final LsPacketHandlerFactory getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * @param loginServer
     */
    private LsPacketHandlerFactory() {
        addPacket(new CM_ACCOUNT_RECONNECT_KEY(0x03), State.AUTHED);
        addPacket(new CM_ACOUNT_AUTH_RESPONSE(0x01), State.AUTHED);
        addPacket(new CM_GS_AUTH_RESPONSE(0x00), State.CONNECTED);
        addPacket(new CM_REQUEST_KICK_ACCOUNT(0x02), State.AUTHED);
        addPacket(new CM_LS_CONTROL_RESPONSE(0x04), State.AUTHED);
        addPacket(new CM_BAN_RESPONSE(0x05), State.AUTHED);
        addPacket(new CM_GS_CHARACTER_RESPONSE(0x08), State.AUTHED);
        addPacket(new CM_MACBAN_LIST(9), State.AUTHED);
        addPacket(new CM_PREMIUM_RESPONSE(10), State.AUTHED);
        addPacket(new CM_LS_PING(11), State.AUTHED);
        addPacket(new CM_PTRANSFER_RESPONSE(12), State.AUTHED);
    }

    private void addPacket(LsClientPacket prototype, State... states) {
        handler.addPacketPrototype(prototype, states);
    }

    public LsPacketHandler getPacketHandler() {
        return handler;
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final LsPacketHandlerFactory instance = new LsPacketHandlerFactory();
    }
}
