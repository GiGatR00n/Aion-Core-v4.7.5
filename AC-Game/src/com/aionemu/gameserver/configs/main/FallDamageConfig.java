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

public class FallDamageConfig {

    /**
     * Fall damage enable
     */
    @Property(key = "gameserver.falldamage.enable", defaultValue = "true")
    public static boolean ACTIVE_FALL_DAMAGE;
    /**
     * Percentage of damage per meter.
     */
    @Property(key = "gameserver.falldamage.percentage", defaultValue = "1.0")
    public static float FALL_DAMAGE_PERCENTAGE;
    /**
     * Minimum fall damage range
     */
    @Property(key = "gameserver.falldamage.distance.minimum", defaultValue = "10")
    public static int MINIMUM_DISTANCE_DAMAGE;
    /**
     * Maximum fall distance after which you will die after hitting the ground.
     */
    @Property(key = "gameserver.falldamage.distance.maximum", defaultValue = "50")
    public static int MAXIMUM_DISTANCE_DAMAGE;
    /**
     * Maximum fall distance after which you will die in mid air.
     */
    @Property(key = "gameserver.falldamage.distance.midair", defaultValue = "200")
    public static int MAXIMUM_DISTANCE_MIDAIR;
}
