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
 * @author Tiger0319
 * @rework Blackfire
 */
public class DropConfig {
   /**
    * Enable announce when a player get Epic item / Mythic item from chest.
    */
    @Property(key = "gameserver.unique.chest.drop.announce.enable", defaultValue = "false")
    public static boolean ENABLE_UNIQUE_CHEST_DROP_ANNOUNCE;
	
    /**
     * Disable drop rate reduction based on level diference between players and
     * mobs
     */
    @Property(key = "gameserver.drop.reduction.disable", defaultValue = "false")
    public static boolean DISABLE_DROP_REDUCTION;
    /**
     * Enable announce when a player drops Unique / Epic item
     */
    @Property(key = "gameserver.unique.drop.announce.enable", defaultValue = "true")
    public static boolean ENABLE_UNIQUE_DROP_ANNOUNCE;
    /**
     * Disable drop rate reduction based on level difference in zone
     */
    @Property(key = "gameserver.drop.noreduction", defaultValue = "0")
    public static String DISABLE_DROP_REDUCTION_IN_ZONES;
}
