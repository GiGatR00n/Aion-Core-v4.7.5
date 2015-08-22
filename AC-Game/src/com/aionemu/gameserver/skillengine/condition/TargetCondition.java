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
package com.aionemu.gameserver.skillengine.condition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.skillengine.properties.FirstTargetAttribute;
import com.aionemu.gameserver.skillengine.properties.TargetRangeAttribute;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer, kecimis
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetCondition")
public class TargetCondition extends Condition {

    @XmlAttribute(required = true)
    protected TargetAttribute value;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link TargetAttribute }
     */
    public TargetAttribute getValue() {
        return value;
    }

    @Override
    public boolean validate(Skill skill) {
        if (value == TargetAttribute.NONE || value == TargetAttribute.ALL) {
            return true;
        }
        if (skill.getSkillTemplate().getProperties().getTargetType().equals(TargetRangeAttribute.AREA)) {
            return true;
        }
        if (skill.getSkillTemplate().getProperties().getFirstTarget() != FirstTargetAttribute.TARGET
                && skill.getSkillTemplate().getProperties().getFirstTarget() != FirstTargetAttribute.TARGETORME) {
            return true;
        }
        if (skill.getSkillTemplate().getProperties().getFirstTarget() == FirstTargetAttribute.TARGETORME
                && skill.getEffector() == skill.getFirstTarget()) {
            return true;
        }

        boolean result = false;
        switch (value) {
            case NPC:
                result = (skill.getFirstTarget() instanceof Npc);
                break;
            case PC:
                result = (skill.getFirstTarget() instanceof Player);
                break;
        }

        if (!result && skill.getEffector() instanceof Player) {
            PacketSendUtility.sendPacket((Player) skill.getEffector(), SM_SYSTEM_MESSAGE.STR_SKILL_TARGET_IS_NOT_VALID);
        }

        return result;
    }
}
