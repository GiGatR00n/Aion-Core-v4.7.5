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
package com.aionemu.gameserver.model.templates.quest;

import com.aionemu.gameserver.model.templates.rewards.BonusType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rolandas
 *
 */

/**
 * <p/>
 * Java class for QuestBonuses complex type.
 * <p/>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p/>
 * <pre>
 * &lt;complexType name="QuestBonuses">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="type" use="required" type="{}BonusType" />
 *       &lt;attribute name="level" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="skill" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestBonuses")
public class QuestBonuses {

    @XmlAttribute(required = true)
    protected BonusType type;
    @XmlAttribute
    protected Integer level;
    @XmlAttribute
    protected Integer skill;

    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link BonusType }
     */
    public BonusType getType() {
        return type;
    }

    /**
     * Gets the value of the level property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * Gets the value of the skill property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getSkill() {
        return skill;
    }
}
