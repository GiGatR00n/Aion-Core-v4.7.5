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
package com.aionemu.gameserver.skillengine.properties;

import java.util.SortedMap;
import java.util.TreeMap;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.MathUtil;

/**
 * @author MrPoke
 */
public class MaxCountProperty {

    public static final boolean set(final Skill skill, Properties properties) {
        TargetRangeAttribute value = properties.getTargetType();
        int maxcount = properties.getTargetMaxCount();

        switch (value) {
            case AREA:
                int areaCounter = 0;
                final Creature firstTarget = skill.getFirstTarget();
                if (firstTarget == null) {
                    return false;
                }
                SortedMap<Double, Creature> sortedMap = new TreeMap<Double, Creature>();
                for (Creature creature : skill.getEffectedList()) {
                    sortedMap.put(MathUtil.getDistance(firstTarget, creature), creature);
                }
                skill.getEffectedList().clear();
                for (Creature creature : sortedMap.values()) {
                    if (areaCounter >= maxcount) {
                        break;
                    }
                    skill.getEffectedList().add(creature);
                    areaCounter++;
                }
        }
        return true;
    }
}
