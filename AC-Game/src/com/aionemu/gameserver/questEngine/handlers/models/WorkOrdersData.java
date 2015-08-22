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

import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.template.WorkOrders;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mr. Poke, reworked Bobobear
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkOrdersData", propOrder = {"giveComponent"})
public class WorkOrdersData extends XMLQuest {

    @XmlElement(name = "give_component", required = true)
    protected List<QuestItems> giveComponent;
    @XmlAttribute(name = "start_npc_ids", required = true)
    protected List<Integer> startNpcIds;
    @XmlAttribute(name = "recipe_id", required = true)
    protected int recipeId;

    /**
     * Gets the value of the giveComponent property.
     * <p/>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the giveComponent property.
     * <p/>
     * For example, to add a new item, do as follows:
     * <p/>
     * <pre>
     * getGiveComponent().add(newItem);
     * </pre>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link QuestItems
     * }
     */
    public List<QuestItems> getGiveComponent() {
        if (giveComponent == null) {
            giveComponent = new ArrayList<QuestItems>();
        }
        return this.giveComponent;
    }

    /**
     * Gets the value of the startNpcIds property.
     */
    public List<Integer> getStartNpcIds() {
        return startNpcIds;
    }

    /**
     * Gets the value of the recipeId property.
     */
    public int getRecipeId() {
        return recipeId;
    }

    /*
     * (non-Javadoc)
     * @see com.aionemu.gameserver.questEngine.handlers.models.QuestScriptData#register()
     */
    @Override
    public void register(QuestEngine questEngine) {
        questEngine.addQuestHandler(new WorkOrders(this));
    }
}
