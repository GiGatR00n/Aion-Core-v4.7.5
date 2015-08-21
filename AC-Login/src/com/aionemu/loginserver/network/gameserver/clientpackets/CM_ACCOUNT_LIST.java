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
package com.aionemu.loginserver.network.gameserver.clientpackets;

import com.aionemu.loginserver.GameServerTable;
import com.aionemu.loginserver.controller.AccountController;
import com.aionemu.loginserver.model.Account;
import com.aionemu.loginserver.network.gameserver.GsClientPacket;
import com.aionemu.loginserver.network.gameserver.serverpackets.SM_REQUEST_KICK_ACCOUNT;

/**
 * Reads the list of accoutn id's that are logged to game server
 *
 * @author SoulKeeper
 */
public class CM_ACCOUNT_LIST extends GsClientPacket {

    /**
     * Array with accounts that are logged in
     */
    private String[] accountNames;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        accountNames = new String[readD()];
        for (int i = 0; i < accountNames.length; i++) {
            accountNames[i] = readS();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        for (String s : accountNames) {
            Account a = AccountController.loadAccount(s);
            if (GameServerTable.isAccountOnAnyGameServer(a)) {
                this.getConnection().sendPacket(new SM_REQUEST_KICK_ACCOUNT(a.getId()));
                continue;
            }
            getConnection().getGameServerInfo().addAccountToGameServer(a);
        }
    }
}
