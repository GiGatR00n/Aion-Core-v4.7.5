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
package com.aionemu.gameserver.skillengine.action;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MpUseAction")
public class MpUseAction extends Action {

    @XmlAttribute(required = true)
    protected int value;
    @XmlAttribute
    protected int delta;
    @XmlAttribute
    protected boolean ratio;

    @Override
    public boolean act(Skill skill) {
        Creature effector = skill.getEffector();
        int currentMp = effector.getLifeStats().getCurrentMp();
        int valueWithDelta = value + delta * skill.getSkillLevel();
        if (ratio) {
            valueWithDelta = (int) ((skill.getEffector().getLifeStats().getMaxMp() * valueWithDelta) / 100);
        }
        int changeMpPercent = skill.getBoostSkillCost();
        if (changeMpPercent != 0) {
            // changeMpPercent is negative
            valueWithDelta = valueWithDelta - ((valueWithDelta / ((100 / changeMpPercent))));
        }

        if (effector instanceof Player) {
            if (currentMp <= 0 || currentMp < valueWithDelta) {
                PacketSendUtility.sendPacket((Player) effector, SM_SYSTEM_MESSAGE.STR_SKILL_NOT_ENOUGH_MP);
                return false;
            }
        }

        effector.getLifeStats().reduceMp(valueWithDelta);
        return true;
    }
}
