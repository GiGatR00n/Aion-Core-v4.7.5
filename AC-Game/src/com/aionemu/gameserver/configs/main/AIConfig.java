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
 * @author ATracer
 */
public class AIConfig {

    /**
     * Debug (for developers)
     */
    @Property(key = "gameserver.ai.move.debug", defaultValue = "true")
    public static boolean MOVE_DEBUG;
    @Property(key = "gameserver.ai.event.debug", defaultValue = "false")
    public static boolean EVENT_DEBUG;
    @Property(key = "gameserver.ai.oncreate.debug", defaultValue = "false")
    public static boolean ONCREATE_DEBUG;
    /**
     * Enable NPC movement
     */
    @Property(key = "gameserver.npcmovement.enable", defaultValue = "true")
    public static boolean ACTIVE_NPC_MOVEMENT;
    /**
     * Enable NPC looking
     */
    @Property(key = "gameserver.npclooking.enable", defaultValue = "false")
    public static boolean ACTIVE_NPC_LOOKING;
    /**
     * Minimum movement delay
     */
    @Property(key = "gameserver.npcmovement.delay.minimum", defaultValue = "3")
    public static int MINIMIMUM_DELAY;
    /**
     * Maximum movement delay
     */
    @Property(key = "gameserver.npcmovement.delay.maximum", defaultValue = "15")
    public static int MAXIMUM_DELAY;
    /**
     * Npc Shouts activator
     */
    @Property(key = "gameserver.npcshouts.enable", defaultValue = "false")
    public static boolean SHOUTS_ENABLE;
}
