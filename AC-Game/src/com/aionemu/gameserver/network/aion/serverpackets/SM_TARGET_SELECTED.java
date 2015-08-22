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

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Sweetkr
 * @modified -Enomine- 4.0
 */
public class SM_TARGET_SELECTED extends AionServerPacket {

    private int level;
    private int maxHp;
    private int currentHp;
    private int maxMp;
    private int currentMp;
    private int targetObjId;

    public SM_TARGET_SELECTED(Player player) {
        if (player != null) {
            if (player.getTarget() instanceof Creature) {
                Creature target = (Creature) player.getTarget();
                this.level = target.getLevel();
                this.maxHp = target.getLifeStats().getMaxHp();
                this.currentHp = target.getLifeStats().getCurrentHp();
                this.maxMp = target.getLifeStats().getMaxMp();
                this.currentMp = target.getLifeStats().getCurrentMp();
            } else {
                this.level = 0;
                this.maxHp = 0;
                this.currentHp = 0;
                this.maxMp = 0;
                this.currentMp = 0;
            }

            if (player.getTarget() != null) {
                targetObjId = player.getTarget().getObjectId();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(targetObjId);
        writeH(level);
        writeD(maxHp);
        writeD(currentHp);
        writeD(maxMp);//new 4.0
        writeD(currentMp);//new 4.0
    }
}
