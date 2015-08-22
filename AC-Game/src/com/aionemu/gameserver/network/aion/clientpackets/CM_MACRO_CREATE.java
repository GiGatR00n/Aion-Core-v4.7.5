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

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MACRO_RESULT;
import com.aionemu.gameserver.services.player.PlayerService;

/**
 * Request to create
 *
 * @author SoulKeeper
 */
public class CM_MACRO_CREATE extends AionClientPacket {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(CM_MACRO_CREATE.class);
    /**
     * Macro number. Fist is 1, second is 2. Starting from 1, not from 0
     */
    private int macroPosition;
    /**
     * XML that represents the macro
     */
    private String macroXML;

    /**
     * Constructs new client packet instance.
     *
     * @param opcode
     */
    public CM_MACRO_CREATE(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * Read macro data
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        macroPosition = readC();
        macroXML = readS();
    }

    /**
     * Logging
     */
    @Override
    protected void runImpl() {
        log.debug(String.format("Created Macro #%d: %s", macroPosition, macroXML));

        PlayerService.addMacro(getConnection().getActivePlayer(), macroPosition, macroXML);

        sendPacket(SM_MACRO_RESULT.SM_MACRO_CREATED);
    }
}
