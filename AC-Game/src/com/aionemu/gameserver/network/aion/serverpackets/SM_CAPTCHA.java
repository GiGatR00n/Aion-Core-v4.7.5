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
 * @author Cura
 */
public class SM_CAPTCHA extends AionServerPacket {

    private int type;
    private int count;
    private int size;
    private byte[] data;
    private boolean isCorrect;
    private int banTime;

    /**
     * @param count
     * @param data
     */
    public SM_CAPTCHA(int count, byte[] data) {
        this.type = 1;
        this.count = count;
        this.size = data.length;
        this.data = data;
    }

    /**
     * @param isCorrect
     */
    public SM_CAPTCHA(boolean isCorrect, int banTime) {
        this.type = 3;
        this.isCorrect = isCorrect;
        this.banTime = banTime;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(type);

        switch (type) {
            case 0x01:
                writeC(count);
                writeD(size);
                writeB(data);
                break;
            case 0x03:
                writeH(isCorrect ? 1 : 0);

                // time setting can't be extracted (retail server default value:3000 sec)
                writeD(banTime);
                break;
        }
    }
}
