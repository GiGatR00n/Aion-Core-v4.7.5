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
import com.aionemu.gameserver.questEngine.handlers.template.ItemCollecting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * @author MrPoke
 * @modified Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemCollectingData")
public class ItemCollectingData extends XMLQuest {

    @XmlAttribute(name = "start_npc_ids", required = true)
    protected List<Integer> startNpcIds;
    @XmlAttribute(name = "action_item_ids")
    protected List<Integer> actionItemIds;
    @XmlAttribute(name = "end_npc_ids")
    protected List<Integer> endNpcIds;
    @XmlAttribute(name = "next_npc_id", required = true)
    protected int nextNpcId;
    @XmlAttribute(name = "HACTION_QUEST_SELECT_id")
    protected int startDialogId;
    @XmlAttribute(name = "HACTION_QUEST_SELECT_id2")
    protected int startDialogId2;
    @XmlAttribute(name = "item_id")
    protected int itemId;

    @Override
    public void register(QuestEngine questEngine) {
        ItemCollecting template = new ItemCollecting(id, startNpcIds, nextNpcId, actionItemIds, endNpcIds, questMovie, startDialogId, startDialogId2, itemId);
        questEngine.addQuestHandler(template);
    }
}
