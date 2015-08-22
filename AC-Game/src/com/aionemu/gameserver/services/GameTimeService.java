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
package com.aionemu.gameserver.services;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GAME_TIME;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.gametime.GameTimeManager;
import com.aionemu.gameserver.world.World;

/**
 * @author ATracer
 */
public class GameTimeService {

    private static Logger log = LoggerFactory.getLogger(GameTimeService.class);

    public static final GameTimeService getInstance() {
        return SingletonHolder.instance;
    }

    private final static int GAMETIME_UPDATE = 3 * 60000;

    private GameTimeService() {
        /**
         * Update players with current game time
         */
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                log.info("Sending current game time to all players");
                Iterator<Player> iterator = World.getInstance().getPlayersIterator();
                while (iterator.hasNext()) {
                    Player next = iterator.next();
                    PacketSendUtility.sendPacket(next, new SM_GAME_TIME());
                }
                // Save game time.
                GameTimeManager.saveTime();
            }
        }, GAMETIME_UPDATE, GAMETIME_UPDATE);

        log.info("GameTimeService started. Update interval:" + GAMETIME_UPDATE);
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {

        protected static final GameTimeService instance = new GameTimeService();
    }
}
