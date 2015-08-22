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
package com.aionemu.gameserver.network.loginserver.serverpackets;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;

/**
 * The universal packet for account/IP bans
 *
 * @author Watson
 */
public class SM_BAN extends LsServerPacket {

    /**
     * Ban type 1 = account 2 = IP 3 = Full ban (account and IP)
     */
    private final byte type;
    /**
     * Account to ban
     */
    private final int accountId;
    /**
     * IP or mask to ban
     */
    private final String ip;
    /**
     * Time in minutes. 0 = infinity; If time < 0 then it's unban command
     */
    private final int time;
    /**
     * Object ID of Admin, who request the ban
     */
    private final int adminObjId;

    public SM_BAN(byte type, int accountId, String ip, int time, int adminObjId) {
        super(0x06);
        this.type = type;
        this.accountId = accountId;
        this.ip = ip;
        this.time = time;
        this.adminObjId = adminObjId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(LoginServerConnection con) {
        writeC(type);
        writeD(accountId);
        writeS(ip);
        writeD(time);
        writeD(adminObjId);
    }
}
