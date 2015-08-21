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
package com.aionemu.loginserver.network.aion.serverpackets;

import com.aionemu.loginserver.network.aion.AionServerPacket;
import com.aionemu.loginserver.network.aion.LoginConnection;
import com.aionemu.loginserver.network.aion.SessionKey;

/**
 * @author -Nemesiss-
 */
public class SM_LOGIN_OK extends AionServerPacket {

    /**
     * accountId is part of session key - its used for security purposes
     */
    private final int accountId;
    /**
     * loginOk is part of session key - its used for security purposes
     */
    private final int loginOk;

    /**
     * Constructs new instance of <tt>SM_LOGIN_OK</tt> packet.
     *
     * @param key session key
     */
    public SM_LOGIN_OK(SessionKey key) {
        super(0x03);
        this.accountId = key.accountId;
        this.loginOk = key.loginOk;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(LoginConnection con) {
        writeD(accountId);
        writeD(loginOk);
        writeD(0x00);
        writeD(0x00);
        writeD(0x000003ea);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeD(0x00);
        writeB(new byte[0x13]);
    }
}
