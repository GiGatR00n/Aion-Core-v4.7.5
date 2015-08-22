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
package com.aionemu.gameserver.model.templates.spawns.siegespawns;

import com.aionemu.gameserver.model.siege.SiegeModType;
import com.aionemu.gameserver.model.siege.SiegeRace;
import com.aionemu.gameserver.model.templates.spawns.Spawn;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SiegeSpawn")
public class SiegeSpawn {

    @XmlElement(name = "siege_race")
    private List<SiegeRaceTemplate> siegeRaceTemplates;
    @XmlAttribute(name = "siege_id")
    private int siegeId;

    public int getSiegeId() {
        return siegeId;
    }

    public List<SiegeRaceTemplate> getSiegeRaceTemplates() {
        return siegeRaceTemplates;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "SiegeRaceTemplate")
    public static class SiegeRaceTemplate {

        @XmlElement(name = "siege_mod")
        private List<SiegeModTemplate> SiegeModTemplates;
        @XmlAttribute(name = "race")
        private SiegeRace race;

        public SiegeRace getSiegeRace() {
            return race;
        }

        public List<SiegeModTemplate> getSiegeModTemplates() {
            return SiegeModTemplates;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "SiegeModTemplate")
        public static class SiegeModTemplate {

            @XmlElement(name = "spawn")
            private List<Spawn> spawns;
            @XmlAttribute(name = "mod")
            private SiegeModType siegeMod;

            public List<Spawn> getSpawns() {
                return spawns;
            }

            public SiegeModType getSiegeModType() {
                return siegeMod;
            }
        }
    }
}
