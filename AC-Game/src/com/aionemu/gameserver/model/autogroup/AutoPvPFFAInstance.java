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
package com.aionemu.gameserver.model.autogroup;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.instancereward.PvPArenaReward;
import com.aionemu.gameserver.network.aion.serverpackets.SM_AUTO_GROUP;
import com.aionemu.gameserver.services.AutoGroupService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author xTz
 */
public class AutoPvPFFAInstance extends AutoInstance {

    @Override
    public AGQuestion addPlayer(Player player, SearchInstance searchInstance) {
        super.writeLock();
        try {
            if (!satisfyTime(searchInstance) || (players.size() >= agt.getPlayerSize())) {
                return AGQuestion.FAILED;
            }
            players.put(player.getObjectId(), new AGPlayer(player));
            return instance != null ? AGQuestion.ADDED : (players.size() == agt.getPlayerSize() ? AGQuestion.READY : AGQuestion.ADDED);
        } finally {
            super.writeUnlock();
        }
    }

    @Override
    public void onPressEnter(Player player) {
        super.onPressEnter(player);
        if (agt.isPvPFFAArena() || agt.isPvPSoloArena() || agt.isGloryArena()) {
            long size = 1;
            int itemId = 186000135;
            if (agt.isGloryArena()) {
                size = 3;
                itemId = 186000185;
            }
            if (!decrease(player, itemId, size)) {
                players.remove(player.getObjectId());
                PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(instanceMaskId, 5));
                if (players.isEmpty()) {
                    AutoGroupService.getInstance().unRegisterInstance(instance.getInstanceId());
                }
                return;
            }
        }
        ((PvPArenaReward) instance.getInstanceHandler().getInstanceReward()).portToPosition(player);
        instance.register(player.getObjectId());
    }

    @Override
    public void onLeaveInstance(Player player) {
        super.unregister(player);
    }
}
