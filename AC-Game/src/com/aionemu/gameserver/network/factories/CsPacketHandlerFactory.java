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

import com.aionemu.gameserver.network.chatserver.ChatServerConnection.State;
import com.aionemu.gameserver.network.chatserver.CsClientPacket;
import com.aionemu.gameserver.network.chatserver.CsPacketHandler;
import com.aionemu.gameserver.network.chatserver.clientpackets.CM_CS_AUTH_RESPONSE;
import com.aionemu.gameserver.network.chatserver.clientpackets.CM_CS_PLAYER_AUTH_RESPONSE;

/**
 * @author ATracer
 */
public class CsPacketHandlerFactory {

    private CsPacketHandler handler = new CsPacketHandler();

    /**
     * @param injector
     */
    public CsPacketHandlerFactory() {
        addPacket(new CM_CS_AUTH_RESPONSE(0x00), State.CONNECTED);
        addPacket(new CM_CS_PLAYER_AUTH_RESPONSE(0x01), State.AUTHED);
    }

    /**
     * @param prototype
     * @param states
     */
    private void addPacket(CsClientPacket prototype, State... states) {
        handler.addPacketPrototype(prototype, states);
    }

    /**
     * @return handler
     */
    public CsPacketHandler getPacketHandler() {
        return handler;
    }
}
