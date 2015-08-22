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
import com.aionemu.gameserver.questEngine.handlers.template.SkillUse;
import javolution.util.FastMap;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author vlog, modified Bobobear
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillUseData")
public class SkillUseData extends XMLQuest {

    @XmlAttribute(name = "start_npc_id")
    protected int startNpc;
    @XmlAttribute(name = "end_npc_id")
    protected int endNpc;
    @XmlElement(name = "skill", required = true)
    protected List<QuestSkillData> skills;

    @Override
    public void register(QuestEngine questEngine) {
        FastMap<List<Integer>, QuestSkillData> questSkills = new FastMap<List<Integer>, QuestSkillData>();
        for (QuestSkillData qsd : skills) {
            questSkills.put(qsd.getSkillIds(), qsd);
        }
        SkillUse questTemplate = new SkillUse(id, startNpc, endNpc, questSkills);
        questEngine.addQuestHandler(questTemplate);
    }
}
