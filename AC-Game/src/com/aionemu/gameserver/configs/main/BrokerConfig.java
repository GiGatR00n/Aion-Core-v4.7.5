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
package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

/**
 * @author GiGatR00n v4.7.5.x
 */
public class BrokerConfig {

    /**
     * Time in seconds for saving broker data into the DB
     * Default....: 6-seconds
     * Standard...: 8-seconds
     * Pro........: 10-seconds
     */
    @Property(key = "gameserver.broker.savemanager.interval", defaultValue = "6")
    public static int SAVEMANAGER_INTERVAL;
    /**
     * Time in seconds for checking broker expired items and sending back to user's mail box
     * Default....: 60-seconds
     * Standard...: 35-seconds
     * Pro........: 20-seconds
     */
    @Property(key = "gameserver.broker.time.checkexpireditems.interval", defaultValue = "60")
    public static int CHECK_EXPIREDITEMS_INTERVAL;
    /**
     * Punishment
	 * 0 - add log record
	 * 1 - log and kick player from game
     */
    @Property(key = "gameserver.broker.antihack.punishment", defaultValue = "0")
    public static int ANTIHACK_PUNISHMENT;
    /**
     * How many days should pass for broker items to mark as expired items
     * Default....: 8-days
     */
    @Property(key = "gameserver.broker.items.expiretime", defaultValue = "8")
    public static int ITEMS_EXPIRETIME;
}
