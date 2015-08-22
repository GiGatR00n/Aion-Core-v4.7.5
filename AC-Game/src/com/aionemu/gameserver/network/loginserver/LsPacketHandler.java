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
package com.aionemu.gameserver.network.loginserver;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author -Nemesiss-
 * @author Luno
 */
public class LsPacketHandler {

    /**
     * logger for this class
     */
    private static final Logger log = LoggerFactory.getLogger(LsPacketHandler.class);
    private static Map<State, Map<Integer, LsClientPacket>> packetPrototypes = new HashMap<State, Map<Integer, LsClientPacket>>();

    /**
     * Reads one packet from given ByteBuffer
     *
     * @param data
     * @param client
     * @return GsClientPacket object from binary data
     */
    public LsClientPacket handle(ByteBuffer data, LoginServerConnection client) {
        State state = client.getState();
        int id = data.get() & 0xff;

        return getPacket(state, id, data, client);
    }

    public void addPacketPrototype(LsClientPacket packetPrototype, State... states) {
        for (State state : states) {
            Map<Integer, LsClientPacket> pm = packetPrototypes.get(state);
            if (pm == null) {
                pm = new HashMap<Integer, LsClientPacket>();
                packetPrototypes.put(state, pm);
            }
            pm.put(packetPrototype.getOpcode(), packetPrototype);
        }
    }

    private LsClientPacket getPacket(State state, int id, ByteBuffer buf, LoginServerConnection con) {
        LsClientPacket prototype = null;

        Map<Integer, LsClientPacket> pm = packetPrototypes.get(state);
        if (pm != null) {
            prototype = pm.get(id);
        }

        if (prototype == null) {
            unknownPacket(state, id);
            return null;
        }

        LsClientPacket res = prototype.clonePacket();
        res.setBuffer(buf);
        res.setConnection(con);

        return res;
    }

    /**
     * Logs unknown packet.
     *
     * @param state
     * @param id
     */
    private void unknownPacket(State state, int id) {
        log.warn(String.format("Unknown packet recived from Login Server: 0x%02X state=%s", id, state.toString()));
    }
}
