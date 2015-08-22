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
package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.questEngine.handlers.models.*;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author MrPoke
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "quest_scripts")
public class XMLQuests {

    @XmlElements({
            @XmlElement(name = "report_to", type = ReportToData.class),
            @XmlElement(name = "monster_hunt", type = MonsterHuntData.class),
            @XmlElement(name = "xml_quest", type = XmlQuestData.class),
            @XmlElement(name = "item_collecting", type = ItemCollectingData.class),
            @XmlElement(name = "relic_rewards", type = RelicRewardsData.class),
            @XmlElement(name = "crafting_rewards", type = CraftingRewardsData.class),
            @XmlElement(name = "report_to_many", type = ReportToManyData.class),
            @XmlElement(name = "kill_in_world", type = KillInWorldData.class),
            @XmlElement(name = "skill_use", type = SkillUseData.class),
            @XmlElement(name = "kill_spawned", type = KillSpawnedData.class),
            @XmlElement(name = "mentor_monster_hunt", type = MentorMonsterHuntData.class),
            @XmlElement(name = "fountain_rewards", type = FountainRewardsData.class),
            @XmlElement(name = "item_order", type = ItemOrdersData.class),
            @XmlElement(name = "work_order", type = WorkOrdersData.class)})
    protected List<XMLQuest> data;

    /**
     * @return the data
     */
    public List<XMLQuest> getQuest() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<XMLQuest> data) {
        this.data = data;
    }
}
