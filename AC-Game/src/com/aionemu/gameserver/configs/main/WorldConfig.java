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
public class WorldConfig {

    /**
     * World region size
     */
    @Property(key = "gameserver.world.region.size", defaultValue = "128")
    public static int WORLD_REGION_SIZE;
    /**
     * Trace active regions and deactivate inactive
     */
    @Property(key = "gameserver.world.region.active.trace", defaultValue = "true")
    public static boolean WORLD_ACTIVE_TRACE;
    @Property(key = "gameserver.world.max.twincount.usual", defaultValue = "0")
    public static int WORLD_MAX_TWINS_USUAL;
    @Property(key = "gameserver.world.max.twincount.beginner", defaultValue = "-1")
    public static int WORLD_MAX_TWINS_BEGINNER;
    @Property(key = "gameserver.world.emulate.fasttrack", defaultValue = "true")
    public static boolean WORLD_EMULATE_FASTTRACK;
    @Property(key = "gameserver.world.specialzone.shownames", defaultValue = "true")
    public static boolean ENABLE_SHOW_ZONEENTER;
}
