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

public class EnchantsConfig {

    /**
     * Supplement Additional Rates
     */
    @Property(key = "gameserver.supplement.lesser", defaultValue = "1.0")
    public static float LESSER_SUP;
    @Property(key = "gameserver.supplement.regular", defaultValue = "1.0")
    public static float REGULAR_SUP;
    @Property(key = "gameserver.supplement.greater", defaultValue = "1.0")
    public static float GREATER_SUP;
    @Property(key = "gameserver.supplement.mythic", defaultValue = "1.0")
    public static float MYTHIC_SUP;
    /**
     * Max enchant level
     */
    @Property(key = "gameserver.enchant.type1", defaultValue = "10")
    public static int ENCHANT_MAX_LEVEL_TYPE1;
    @Property(key = "gameserver.enchant.type2", defaultValue = "15")
    public static int ENCHANT_MAX_LEVEL_TYPE2;
    /**
     * ManaStone Rates
     */
    @Property(key = "gameserver.base.manastone", defaultValue = "50")
    public static float MANA_STONE;
    @Property(key = "gameserver.base.enchant", defaultValue = "60")
    public static float ENCHANT_STONE;
    @Property(key = "gameserver.manastone.clean", defaultValue = "false")
    public static boolean CLEAN_STONE;
    @Property(key = "gameserver.manastone.enchant_cast_delay", defaultValue = "5000")
    public static int ENCHANT_CAST_DELAY;    
}
