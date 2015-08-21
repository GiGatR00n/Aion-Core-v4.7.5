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

import com.aionemu.loginserver.GameServerInfo;
import com.aionemu.loginserver.GameServerTable;
import com.aionemu.loginserver.controller.AccountController;
import com.aionemu.loginserver.network.aion.AionServerPacket;
import com.aionemu.loginserver.network.aion.LoginConnection;

import java.util.Collection;
import java.util.Map;

/**
 * @author -Nemesiss-
 * @modified cura
 */
public class SM_SERVER_LIST extends AionServerPacket {

    public SM_SERVER_LIST() {
        super(0x04);
    }

    @Override
    protected void writeImpl(LoginConnection con) {
        Collection<GameServerInfo> servers = GameServerTable.getGameServers();
        Map<Integer, Integer> charactersCountOnServer = null;

        int accountId = con.getAccount().getId();
        int maxId = 0;

        charactersCountOnServer = AccountController.getGSCharacterCountsFor(accountId);

        writeC(servers.size());// servers
        writeC(con.getAccount().getLastServer());// last server
        for (GameServerInfo gsi : servers) {
            if (gsi.getId() > maxId) {
                maxId = gsi.getId();
            }

            writeC(gsi.getId());// server id
            writeB(gsi.getIPAddressForPlayer(con.getIP())); // server IP
            writeD(gsi.getPort());// port
            writeC(0x00); // age limit
            writeC(0x01);// pvp=1
            writeH(gsi.getCurrentPlayers());// currentPlayers
            writeH(gsi.getMaxPlayers());// maxPlayers
            writeC(gsi.isOnline() ? 1 : 0);// ServerStatus, up=1
            writeD(1);// bits);
            writeC(0);// server.brackets ? 0x01 : 0x00);
        }

        writeH(maxId + 1);
        writeC(0x01);

        for (int i = 1; i <= maxId; i++) {
            if (charactersCountOnServer.containsKey(i)) {
                writeC(charactersCountOnServer.get(i));
            } else {
                writeC(0);
            }
        }
    }
}
