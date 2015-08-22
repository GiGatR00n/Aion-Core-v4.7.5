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

import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Sarynth 0xB0 for 1.5.1.10 and 1.5.1.15
 */
public class SM_KISK_UPDATE extends AionServerPacket {

    // useMask values determine who can bind to the kisk.
    // 1 ~ race
    // 2 ~ legion
    // 3 ~ solo
    // 4 ~ group
    // 5 ~ alliance
    // of course, we must programmatically check as well.
    private int objId;
    private int useMask;
    private int currentMembers;
    private int maxMembers;
    private int remainingRessurects;
    private int maxRessurects;
    private int remainingLifetime;

    public SM_KISK_UPDATE(Kisk kisk) {
        this.objId = kisk.getObjectId();
        this.useMask = kisk.getUseMask();
        this.currentMembers = kisk.getCurrentMemberCount();
        this.maxMembers = kisk.getMaxMembers();
        this.remainingRessurects = kisk.getRemainingResurrects();
        this.maxRessurects = kisk.getMaxRessurects();
        this.remainingLifetime = kisk.getRemainingLifetime();
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(objId);
        writeD(useMask);
        writeD(currentMembers);
        writeD(maxMembers);
        writeD(remainingRessurects);
        writeD(maxRessurects);
        writeD(remainingLifetime);
    }
}
