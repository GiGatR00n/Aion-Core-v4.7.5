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
package com.aionemu.gameserver.world.zone;

import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Rolandas
 */
public final class ZoneName {

    private final static Logger log = LoggerFactory.getLogger(ZoneName.class);
    private static final FastMap<String, ZoneName> zoneNames = new FastMap<String, ZoneName>();
    public static final String NONE = "NONE";
    public static final String ABYSS_CASTLE = "_ABYSS_CASTLE_AREA_";

    static {
        zoneNames.put(NONE, new ZoneName(NONE));
        zoneNames.put(ABYSS_CASTLE, new ZoneName(ABYSS_CASTLE));
    }

    private String _name;

    private ZoneName(String name) {
        this._name = name;
    }

    public String name() {
        return _name;
    }

    public int id() {
        return _name.hashCode();
    }

    public static final ZoneName createOrGet(String name) {
        name = name.toUpperCase();
        if (zoneNames.containsKey(name)) {
            return zoneNames.get(name);
        }
        ZoneName newZone = new ZoneName(name);
        zoneNames.put(name, newZone);
        return newZone;
    }

    public static final int getId(String name) {
        name = name.toUpperCase();
        if (zoneNames.containsKey(name)) {
            return zoneNames.get(name).id();
        }
        return zoneNames.get(NONE).id();
    }

    public static final ZoneName get(String name) {
        name = name.toUpperCase();
        if (zoneNames.containsKey(name)) {
            return zoneNames.get(name);
        }
        log.warn("Missing zone : " + name);
        return zoneNames.get(NONE);
    }

    @Override
    public String toString() {
        return _name;
    }
}
