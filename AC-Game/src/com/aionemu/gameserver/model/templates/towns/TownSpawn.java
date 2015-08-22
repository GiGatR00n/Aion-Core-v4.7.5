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
package com.aionemu.gameserver.model.templates.towns;

import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.List;

/**
 * @author ViAl
 */
@XmlType(name = "town_spawn")
public class TownSpawn {

    @XmlAttribute(name = "town_id")
    private int townId;
    @XmlElement(name = "town_level")
    private List<TownLevel> townLevels;
    private TIntObjectHashMap<TownLevel> townLevelsData = new TIntObjectHashMap<TownLevel>();

    /**
     * @param u
     * @param parent
     */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        townLevelsData.clear();

        for (TownLevel level : townLevels) {
            townLevelsData.put(level.getLevel(), level);
        }
        townLevels.clear();
        townLevels = null;
    }

    /**
     * @return the townId
     */
    public int getTownId() {
        return townId;
    }

    public TownLevel getSpawnsForLevel(int level) {
        return townLevelsData.get(level);
    }

    public Collection<TownLevel> getTownLevels() {
        return this.townLevelsData.valueCollection();
    }
}
