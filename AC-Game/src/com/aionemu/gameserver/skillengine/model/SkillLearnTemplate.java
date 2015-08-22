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
package com.aionemu.gameserver.skillengine.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "skill")
public class SkillLearnTemplate {

    @XmlAttribute(name = "classId", required = true)
    private PlayerClass classId = PlayerClass.ALL;
    @XmlAttribute(name = "skillId", required = true)
    private int skillId;
    @XmlAttribute(name = "skillLevel", required = true)
    private int skillLevel;
    @XmlAttribute(name = "name", required = true)
    private String name;
    @XmlAttribute(name = "race", required = true)
    private Race race;
    @XmlAttribute(name = "minLevel", required = true)
    private int minLevel;
    @XmlAttribute
    private boolean autolearn;
    @XmlAttribute
    private boolean stigma = false;

    /**
     * @return the classId
     */
    public PlayerClass getClassId() {
        return classId;
    }

    /**
     * @return the skillId
     */
    public int getSkillId() {
        return skillId;
    }

    /**
     * @return the skillLevel
     */
    public int getSkillLevel() {
        return skillLevel;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the minLevel
     */
    public int getMinLevel() {
        return minLevel;
    }

    /**
     * @return the race
     */
    public Race getRace() {
        return race;
    }

    /**
     * @return the autolearn
     */
    public boolean isAutolearn() {
        return autolearn;
    }

    /**
     * @return the stigma
     */
    public boolean isStigma() {
        return stigma;
    }
}
