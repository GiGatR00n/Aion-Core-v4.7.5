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

import javax.xml.bind.annotation.*;

/**
 * @author MrPoke
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestDrop")
public class QuestDrop {

    @XmlAttribute(name = "npc_id")
    protected Integer npcId;
    @XmlAttribute(name = "item_id")
    protected Integer itemId;
    @XmlAttribute
    protected Integer chance;
    @XmlAttribute(name = "drop_each_member")
    protected int dropEachMember = 0;
    @XmlAttribute(name = "collecting_step")
    protected int collecting_step = 0;
    @XmlTransient
    protected Integer questId;

    /**
     * Gets the value of the npcId property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getNpcId() {
        return npcId;
    }

    /**
     * Gets the value of the itemId property.
     *
     * @return possible object is {@link Integer }
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * Gets the value of the chance property.
     *
     * @return possible object is {@link Integer }
     */
    public int getChance() {
        if (chance == null) {
            return 100;
        }
        return chance;
    }

    public boolean isDropEachMemberGroup() {
        return dropEachMember == 1;
    }

    public boolean isDropEachMemberAlliance() {
        return dropEachMember == 2;
    }

    /**
     * @return the questId
     */
    public Integer getQuestId() {
        return questId;
    }

    public int getCollectingStep() {
        return collecting_step;
    }

    /**
     * @param questId the questId to set
     */
    public void setQuestId(Integer questId) {
        this.questId = questId;
    }
}
