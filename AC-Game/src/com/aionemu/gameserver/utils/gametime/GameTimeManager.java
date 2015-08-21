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
package com.aionemu.gameserver.utils.gametime;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.ServerVariablesDAO;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages ingame time
 *
 * @author Ben
 */
public class GameTimeManager {

    private static final Logger log = LoggerFactory.getLogger(GameTimeManager.class);
    private static GameTime instance;
    private static GameTimeUpdater updater;
    private static boolean clockStarted = false;

    static {
        ServerVariablesDAO dao = DAOManager.getDAO(ServerVariablesDAO.class);
        instance = new GameTime(dao.load("time"));
    }

    /**
     * Gets the current GameTime
     *
     * @return GameTime
     */
    public static GameTime getGameTime() {
        return instance;
    }

    /**
     * Starts the counter that increases the clock every tick
     *
     * @throws IllegalStateException If called twice
     */
    public static void startClock() {
        if (clockStarted) {
            throw new IllegalStateException("Clock is already started");
        }

        updater = new GameTimeUpdater(getGameTime());
        ThreadPoolManager.getInstance().scheduleAtFixedRate(updater, 0, 5000);

        clockStarted = true;
    }

    /**
     * Saves the current time to the database
     *
     * @return Success
     */
    public static boolean saveTime() {
        log.info("Game time saved...");
        return DAOManager.getDAO(ServerVariablesDAO.class).store("time", getGameTime().getTime());
    }

    /**
     * Clean scheduled queues, set a new GameTime, then restart the clock
     */
    public static void reloadTime(int time) {
        ThreadPoolManager.getInstance().purge();
        instance = new GameTime(time);

        clockStarted = false;

        startClock();
        log.info("Game time changed by admin and clock restarted...");
    }
}
