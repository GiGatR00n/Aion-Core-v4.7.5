/**
 * This file is part of Aion Eternity Core <Ver:4.7>.
 *
 * Aion Eternity Core is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * Aion Eternity Core is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Aion Eternity Core. If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.aionemu.gameserver.eventEngine.battleground.model.templates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Maestross
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BattleGroundJoinConditions")
public class BattleGroundJoinConditions {

    @XmlAttribute(name = "required_level", required = true)
    private int requiredLevel;
    @XmlAttribute(name = "required_bg_points", required = true)
    private int requiredBgPoints;
    @XmlAttribute(name = "max_level", required = true)
    private int maxLevel;
    @XmlAttribute(name = "max_bg_points", required = true)
    private int maxBgPoints;

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public int getRequiredBgPoints() {
        return requiredBgPoints;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getMaxBgPoints() {
        return maxBgPoints;
    }
}
