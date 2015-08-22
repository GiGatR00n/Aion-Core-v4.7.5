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

import com.aionemu.commons.network.packet.BaseClientPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author -Nemesiss-
 */
public abstract class LsClientPacket extends BaseClientPacket<LoginServerConnection> implements Cloneable {

    /**
     * Logger for this class.
     */
    private static final Logger log = LoggerFactory.getLogger(LsClientPacket.class);

    /**
     * Constructs new client packet with specified opcode. If using this
     * constructor, user must later manually set buffer and connection.
     *
     * @param opcode packet id
     */
    protected LsClientPacket(int opcode) {
        super(opcode);
    }

    /**
     * run runImpl catching and logging Throwable.
     */
    @Override
    public final void run() {
        try {
            runImpl();
        } catch (Throwable e) {
            log.warn("error handling ls (" + getConnection().getIP() + ") message " + this, e);
        }
    }

    /**
     * Send new LsServerPacket to connection that is owner of this packet. This
     * method is equivalent to: getConnection().sendPacket(msg);
     *
     * @param msg
     */
    protected void sendPacket(LsServerPacket msg) {
        getConnection().sendPacket(msg);
    }

    /**
     * Clones this packet object.
     *
     * @return LsClientPacket
     */
    public LsClientPacket clonePacket() {
        try {
            return (LsClientPacket) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
