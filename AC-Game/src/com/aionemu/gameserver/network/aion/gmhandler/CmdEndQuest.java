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
package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.QuestStateList;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_COMPLETED_LIST;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;


public class CmdEndQuest extends AbstractGMHandler {

    public CmdEndQuest(Player admin, String params) {
        super(admin, params);
        run();
    }

    private void run() {
        Player t = target != null ? target : admin;

        Integer questID = Integer.parseInt(params);
        if (questID <= 0) {
            return;
        }

        DataManager.getInstance();
        QuestTemplate qt = DataManager.QUEST_DATA.getQuestById(questID);
        if (qt == null) {
            PacketSendUtility.sendMessage(admin, "Quest with ID: " + questID + " was not found");
            return;
        }

        QuestStateList list = t.getQuestStateList();
        if (list == null || list.getQuestState(questID) == null) {
            PacketSendUtility.sendMessage(admin, "Quest not founded for target " + t.getName());
            return;
        }
        if (list.getQuestState(questID).getStatus() == QuestStatus.COMPLETE) {
            PacketSendUtility.sendMessage(admin, "Quest allready finished");
            return;
        }
        list.getQuestState(questID).setStatus(QuestStatus.REWARD);
        t.getController().updateNearbyQuests();
        QuestEnv env = new QuestEnv(null, t, questID, 0);
        QuestService.finishQuest(env);
        PacketSendUtility.sendPacket(t, new SM_QUEST_COMPLETED_LIST(t.getQuestStateList().getAllFinishedQuests()));
        t.getController().updateNearbyQuests();
    }

}