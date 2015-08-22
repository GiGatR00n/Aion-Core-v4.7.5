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
import com.aionemu.gameserver.services.antihack.AntiHackService;


public class CM_GAMEGUARD extends AionClientPacket {

    private int size;
    @SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CM_GAMEGUARD.class);

    /**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_GAMEGUARD(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /* (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#readImpl()
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        size = readD();
        readB(size);

    }

    /* (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#runImpl()
     */
    @Override
    protected void runImpl() {
    	//log.info("AION Bin size from client: " + size);
        Player player = getConnection().getActivePlayer();
        AntiHackService.checkAionBin(size, player);
    }

}
