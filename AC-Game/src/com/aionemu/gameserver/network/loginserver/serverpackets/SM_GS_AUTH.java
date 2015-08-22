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

import com.aionemu.commons.network.IPRange;
import com.aionemu.gameserver.configs.network.IPConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;

import java.util.List;

/**
 * This is authentication packet that gs will send to login server for
 * registration.
 *
 * @author -Nemesiss-
 */
public class SM_GS_AUTH extends LsServerPacket {

    public SM_GS_AUTH() {
        super(0x00);
    }

    @Override
    protected void writeImpl(LoginServerConnection con) {
        writeC(NetworkConfig.GAMESERVER_ID);
        writeC(IPConfig.getDefaultAddress().length);
        writeB(IPConfig.getDefaultAddress());

        List<IPRange> ranges = IPConfig.getRanges();
        int size = ranges.size();
        writeD(size);
        for (int i = 0; i < size; i++) {
            IPRange ipRange = ranges.get(i);
            byte[] min = ipRange.getMinAsByteArray();
            byte[] max = ipRange.getMaxAsByteArray();
            writeC(min.length);
            writeB(min);
            writeC(max.length);
            writeB(max);
            writeC(ipRange.getAddress().length);
            writeB(ipRange.getAddress());
        }

        writeH(NetworkConfig.GAME_PORT);
        writeD(NetworkConfig.MAX_ONLINE_PLAYERS);
        writeS(NetworkConfig.LOGIN_PASSWORD);
    }
}
