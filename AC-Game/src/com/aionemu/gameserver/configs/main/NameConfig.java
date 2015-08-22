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

import java.util.regex.Pattern;

/**
 * @author nrg
 */
public class NameConfig {

    /**
     * Enables custom names usage.
     */
    @Property(key = "gameserver.name.allow.custom", defaultValue = "false")
    public static boolean ALLOW_CUSTOM_NAMES;
    /**
     * Character name pattern (checked when character is being created)
     */
    @Property(key = "gameserver.name.characterpattern", defaultValue = "[a-zA-Z]{2,16}")
    public static Pattern CHAR_NAME_PATTERN;
    /**
     * Forbidden word sequences Filters charname, miol, legion, chat
     */
    @Property(key = "gameserver.name.forbidden.sequences", defaultValue = "")
    public static String NAME_SEQUENCE_FORBIDDEN;
    /**
     * Enable client filter Filters charname, miol, legion, chat
     */
    @Property(key = "gameserver.name.forbidden.enable.client", defaultValue = "true")
    public static boolean NAME_FORBIDDEN_ENABLE;
    /**
     * Forbidden Charnames NOTE: Parsed out of aion 3.0 client Filters charname,
     * miol, legion, chat
     */
    @Property(key = "gameserver.name.forbidden.client", defaultValue = "")
    public static String NAME_FORBIDDEN_CLIENT;
}
