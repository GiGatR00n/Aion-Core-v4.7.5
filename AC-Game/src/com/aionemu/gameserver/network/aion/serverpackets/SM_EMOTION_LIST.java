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

import java.util.Collection;

import com.aionemu.gameserver.configs.main.MembershipConfig;
import com.aionemu.gameserver.model.gameobjects.player.emotion.Emotion;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class SM_EMOTION_LIST extends AionServerPacket {

    byte action;
    Collection<Emotion> emotions;

    /**
     * @param action
     * @modified Kill3r
     */
    public SM_EMOTION_LIST(byte action, Collection<Emotion> emotions) {
        this.action = action;
        this.emotions = emotions;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(action);
        if (con.getActivePlayer().havePermission(MembershipConfig.EMOTIONS_ALL)) {
            writeH(92);
            for (int i = 0; i < 92; i++) {
                writeH(64 + i);
                writeD(0x00);
            }
        } else if (emotions == null || emotions.isEmpty()) {
            writeH(0);
        } else {
            writeH(emotions.size());
            for (Emotion emotion : emotions) {
                writeH(emotion.getId());
                writeD(emotion.getRemainingTime());//remaining time
            }
        }
    }
}
