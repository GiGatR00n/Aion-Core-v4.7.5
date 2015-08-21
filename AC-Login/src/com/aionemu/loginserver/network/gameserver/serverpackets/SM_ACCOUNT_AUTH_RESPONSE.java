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
package com.aionemu.loginserver.network.gameserver.serverpackets;

import com.aionemu.loginserver.model.AccountTime;
import com.aionemu.loginserver.network.gameserver.GsConnection;
import com.aionemu.loginserver.network.gameserver.GsServerPacket;

/**
 * In this packet LoginServer is answering on GameServer request about valid
 * authentication data and also sends account name of user that is
 * authenticating on GameServer.
 *
 * @author -Nemesiss-
 */
public class SM_ACCOUNT_AUTH_RESPONSE extends GsServerPacket {

    /**
     * Account id
     */
    private final int accountId;
    /**
     * True if account is authenticated.
     */
    private final boolean ok;
    /**
     * account name
     */
    private final String accountName;
    /**
     * Access level
     */
    private final byte accessLevel;
    /**
     * Membership
     */
    private final byte membership;
    /**
     * TOLL
     */
    private final long toll;

    /**
     * Constructor.
     *
     * @param accountId
     * @param ok
     * @param accountName
     * @param accessLevel
     * @param membership
     * @param toll
     */
    public SM_ACCOUNT_AUTH_RESPONSE(int accountId, boolean ok, String accountName, byte accessLevel, byte membership, long toll) {
        this.accountId = accountId;
        this.ok = ok;
        this.accountName = accountName;
        this.accessLevel = accessLevel;
        this.membership = membership;
        this.toll = toll;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(GsConnection con) {
        writeC(1);
        writeD(accountId);
        writeC(ok ? 1 : 0);

        if (ok) {
            writeS(accountName);

            AccountTime accountTime = con.getGameServerInfo().getAccountFromGameServer(accountId).getAccountTime();

            writeQ(accountTime.getAccumulatedOnlineTime());
            writeQ(accountTime.getAccumulatedRestTime());
            writeC(accessLevel);
            writeC(membership);
            writeQ(toll);
        }
    }
}
