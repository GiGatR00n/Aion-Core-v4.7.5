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
 * @author lord_rex
 */
public class HTMLConfig {

    /**
     * Enable HTML Welcome Message
     */
    @Property(key = "gameserver.html.welcome.enable", defaultValue = "false")
    public static boolean ENABLE_HTML_WELCOME;
    /**
     * Enable HTML Guide Message
     */
    @Property(key = "gameserver.html.guides.enable", defaultValue = "false")
    public static boolean ENABLE_GUIDES;
    /**
     * Html files directory
     */
    @Property(key = "gameserver.html.root", defaultValue = "./data/static_data/HTML/")
    public static String HTML_ROOT;
    /**
     * Html cache directory
     */
    @Property(key = "gameserver.html.cache.file", defaultValue = "./cache/html.cache")
    public static String HTML_CACHE_FILE;
    /**
     * Encoding
     */
    @Property(key = "gameserver.html.encoding", defaultValue = "UTF-8")
    public static String HTML_ENCODING;
}
