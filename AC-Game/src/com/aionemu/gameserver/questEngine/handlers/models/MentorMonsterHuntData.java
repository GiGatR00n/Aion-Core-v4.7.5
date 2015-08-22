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
package com.aionemu.gameserver.questEngine.handlers.models;

import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.template.MentorMonsterHunt;
import javolution.util.FastMap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.HashSet;
import java.util.Set;

/**
 * @author MrPoke reworked Bobobear
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MentorMonsterHuntData")
public class MentorMonsterHuntData extends MonsterHuntData {

    @XmlAttribute(name = "min_mente_level")
    protected int minMenteLevel = 1;
    @XmlAttribute(name = "max_mente_level")
    protected int maxMenteLevel = 99;

    public int getMinMenteLevel() {
        return minMenteLevel;
    }

    public int getMaxMenteLevel() {
        return maxMenteLevel;
    }

    @Override
    public void register(QuestEngine questEngine) {
        FastMap<Monster, Set<Integer>> monsterNpcs = new FastMap<Monster, Set<Integer>>();
        for (Monster m : monster) {
            monsterNpcs.put(m, new HashSet<Integer>(m.getNpcIds()));
        }
        MentorMonsterHunt template = new MentorMonsterHunt(id, startNpcIds, endNpcIds, monsterNpcs, minMenteLevel,
                maxMenteLevel);
        questEngine.addQuestHandler(template);
    }
}
