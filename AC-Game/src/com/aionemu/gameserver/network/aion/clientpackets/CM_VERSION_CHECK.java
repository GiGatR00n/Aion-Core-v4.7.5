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

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_AFTER_TIME_CHECK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_VERSION_CHECK;

/**
 * @author -Nemesiss-
 */
public class CM_VERSION_CHECK extends AionClientPacket {

    /**
     * Aion Client version
     */
    private int version;
    @SuppressWarnings("unused")
    private int subversion;
    @SuppressWarnings("unused")
    private int windowsEncoding;
    @SuppressWarnings("unused")
    private int windowsVersion;
    @SuppressWarnings("unused")
    private int windowsSubVersion;

    /**
     * Constructs new instance of <tt>CM_VERSION_CHECK </tt> packet
     *
     * @param opcode
     */
    public CM_VERSION_CHECK(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        version = readH();
        subversion = readH();
        windowsEncoding = readD();
        windowsVersion = readD();
        windowsSubVersion = readD();
        readC();//always 2?
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        sendPacket(new SM_VERSION_CHECK(version));
        sendPacket(new SM_AFTER_TIME_CHECK());
    }
}
