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
package com.aionemu.gameserver.model.templates.factions;

import com.aionemu.gameserver.model.Race;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author vlog
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NpcFaction")
public class NpcFactionTemplate {

    @XmlAttribute(name = "id", required = true)
    protected int id;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "nameId")
    protected int nameId;
    @XmlAttribute(name = "category")
    protected FactionCategory category;
    @XmlAttribute(name = "minlevel")
    protected Integer minlevel;
    @XmlAttribute(name = "maxlevel")
    protected int maxlevel = 99;
    @XmlAttribute(name = "race")
    protected Race race;
    @XmlAttribute(name = "npcid")
    protected int npcId;
    @XmlAttribute(name = "skill_points")
    protected int skillPoints;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNameId() {
        return nameId;
    }

    public FactionCategory getCategory() {
        return category;
    }

    public int getMinLevel() {
        return minlevel;
    }

    public int getMaxLevel() {
        return maxlevel;
    }

    public Race getRace() {
        return race;
    }

    public boolean isMentor() {
        return category == FactionCategory.MENTOR;
    }

    /**
     * @return the npcId
     */
    public int getNpcId() {
        return npcId;
    }

    public int getSkillPoints() {
        return skillPoints;
    }
}
