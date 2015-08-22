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
package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Maestros
 */
public class SM_DRAWING_TOOL extends AionServerPacket {

    private int unk2;
    private int action;
    private byte[] data;

    public SM_DRAWING_TOOL(int unk2, int action, byte[] data) {
        this.unk2 = unk2;
        this.action = action;
        this.data = data;
    }

    public SM_DRAWING_TOOL(byte[] data, @SuppressWarnings("unused") int action, int unk2) {
        this.action = 1;
        this.data = data;
        this.unk2 = unk2;
    }

    public SM_DRAWING_TOOL(byte[] data) {
        this.action = 1;
        this.data = data;
    }

    @Override
    protected void writeImpl(AionConnection aionconnection) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(action);
        if (action != 1) {
            writeC(unk2);
        }
        writeD(data.length);
        writeB(data);
    }
}
