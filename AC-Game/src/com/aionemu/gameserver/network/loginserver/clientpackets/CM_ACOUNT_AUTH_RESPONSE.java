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
package com.aionemu.gameserver.network.loginserver.clientpackets;

import com.aionemu.gameserver.model.account.AccountTime;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;

/**
 * In this packet LoginServer is answering on GameServer request about valid
 * authentication data and also sends account name of user that is
 * authenticating on GameServer.
 *
 * @author -Nemesiss-
 */
public class CM_ACOUNT_AUTH_RESPONSE extends LsClientPacket {

    public CM_ACOUNT_AUTH_RESPONSE(int opCode) {
        super(opCode);
    }

    /**
     * accountId
     */
    private int accountId;
    /**
     * result - true = authed
     */
    private boolean result;
    /**
     * accountName [if response is ok]
     */
    private String accountName;
    /**
     * accountTime
     */
    private AccountTime accountTime;
    /**
     * access level - regular/gm/admin
     */
    private byte accessLevel;
    /**
     * Membership - regular/premium
     */
    private byte membership;
    /**
     * Toll
     */
    private long toll;

    /**
     * {@inheritDoc}
     */
    @Override
    public void readImpl() {
        accountId = readD();
        result = readC() == 1;

        if (result) {
            accountName = readS();
            accountTime = new AccountTime();

            accountTime.setAccumulatedOnlineTime(readQ());
            accountTime.setAccumulatedRestTime(readQ());

            accessLevel = (byte) readC();
            membership = (byte) readC();
            toll = readQ();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void runImpl() {
        LoginServer.getInstance().accountAuthenticationResponse(accountId, accountName, result, accountTime, accessLevel, membership, toll);
    }
}
