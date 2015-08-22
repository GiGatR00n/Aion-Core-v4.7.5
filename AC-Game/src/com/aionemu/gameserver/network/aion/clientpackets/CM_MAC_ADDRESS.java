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

import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.network.BannedMacManager;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;

/**
 * In this packet client is sending Mac Address - haha.
 *
 * @author -Nemesiss-, KID
 */
public class CM_MAC_ADDRESS extends AionClientPacket {

	private static final Logger log = LoggerFactory.getLogger(CM_MAC_ADDRESS.class);
	
    /**
     * Mac Address send by client in the same format as:
     * ipconfig /all [ie:xx-xx-xx-xx-xx-xx]
     */
    private String ClientMacAddress;
    private String ClientHddSerial;
    private int ClientLocalIp;

    /**
     * Constructs new instance of <tt>CM_MAC_ADDRESS </tt> packet
     *
     * @param opcode
     */
    public CM_MAC_ADDRESS(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        readC();
        short counter = (short) readH();
        for (short i = 0; i < counter; i++) {
            readD();
        }
        ClientMacAddress = readS();
        ClientHddSerial = readS();
        ClientLocalIp = readD();
    }

    @Override
    protected void runImpl() {
        if (BannedMacManager.getInstance().isBanned(ClientMacAddress)) {
            //TODO some information packets
            this.getConnection().closeNow();
            log.info("[MAC_AUDIT] " + ClientMacAddress + " (" + this.getConnection().getIP() + ") was kicked due to Mac Ban");
        } else {
            this.getConnection().setMacAddress(ClientMacAddress);
        }
    }
}
