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

import javax.xml.bind.annotation.*;

/**
 * @author MrPoke, Hilgert
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestScriptData")
@XmlSeeAlso({ReportToData.class, RelicRewardsData.class, CraftingRewardsData.class, ReportToManyData.class, MonsterHuntData.class,
        ItemCollectingData.class, WorkOrdersData.class, XmlQuestData.class, MentorMonsterHuntData.class, ItemOrdersData.class,
        FountainRewardsData.class, SkillUseData.class})
public abstract class XMLQuest {

    @XmlAttribute(name = "id", required = true)
    protected int id;
    @XmlAttribute(name = "movie", required = false)
    protected int questMovie;
    @XmlAttribute(name = "mission", required = false)
    protected boolean mission;

    /**
     * Gets the value of the id property.
     */
    public int getId() {
        return id;
    }

    public int getQuestMovie() {
        return questMovie;
    }

    /**
     * @return the mission
     */
    public boolean isMission() {
        return mission;
    }

    /**
     * @param mission the mission to set
     */
    public void setMission(boolean mission) {
        this.mission = mission;
    }

    public abstract void register(QuestEngine questEngine);
}
