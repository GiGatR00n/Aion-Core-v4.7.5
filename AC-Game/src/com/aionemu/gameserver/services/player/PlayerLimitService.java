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
package com.aionemu.gameserver.services.player;

import javolution.util.FastMap;

import com.aionemu.commons.services.CronService;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.SellLimit;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Source
 */
public class PlayerLimitService {

    private static FastMap<Integer, Long> sellLimit = new FastMap<Integer, Long>().shared();

    public static boolean updateSellLimit(Player player, long reward) {
        if (!CustomConfig.LIMITS_ENABLED) {
            return true;
        }

        int accoutnId = player.getPlayerAccount().getId();
        Long limit = sellLimit.get(accoutnId);
        if (limit == null) {
            limit = (long) (SellLimit.getSellLimit(player.getPlayerAccount().getMaxPlayerLevel()) * player.getRates().getSellLimitRate());
            sellLimit.put(accoutnId, limit);
        }

        if (limit < reward) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_DAY_CANNOT_SELL_NPC(limit));
            return false;
        } else {
            limit -= reward;
            sellLimit.putEntry(accoutnId, limit);
            return true;
        }
    }

    public void scheduleUpdate() {
        CronService.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                sellLimit.clear();
            }
        }, CustomConfig.LIMITS_UPDATE, true);
    }

    public static PlayerLimitService getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {

        protected static final PlayerLimitService instance = new PlayerLimitService();
    }
}
