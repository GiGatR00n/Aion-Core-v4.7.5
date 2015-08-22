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

import com.aionemu.gameserver.model.siege.Influence;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.SiegeService;

/**
 * @author Nemiroff
 */
public class SM_INFLUENCE_RATIO extends AionServerPacket {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        Influence inf = Influence.getInstance();

        writeD(SiegeService.getInstance().getSecondsBeforeHourEnd());
        writeF(inf.getGlobalElyosInfluence());
        writeF(inf.getGlobalAsmodiansInfluence());
        writeF(inf.getGlobalBalaursInfluence());
        writeH(4); // maps count
        writeD(210050000);
        writeF(inf.getInggisonElyosInfluence());
        writeF(inf.getInggisonAsmodiansInfluence());
        writeF(inf.getInggisonBalaursInfluence());
        writeD(220070000);
        writeF(inf.getGelkmarosElyosInfluence());
        writeF(inf.getGelkmarosAsmodiansInfluence());
        writeF(inf.getGelkmarosBalaursInfluence());
        writeD(400010000);
        writeF(inf.getAbyssElyosInfluence());
        writeF(inf.getAbyssAsmodiansInfluence());
        writeF(inf.getAbyssBalaursInfluence());
        writeD(600030000);
        writeF(inf.getTiamarantaElyosInfluence());
        writeF(inf.getTiamarantaAsmodiansInfluence());
        writeF(inf.getTiamarantaBalaursInfluence());
    }
}
