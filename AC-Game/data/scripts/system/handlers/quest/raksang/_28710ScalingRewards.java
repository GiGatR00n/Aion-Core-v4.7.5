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
package quest.raksang;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author zhkchi
 *
 */
public class _28710ScalingRewards extends QuestHandler {

    private static final int questId = 28710;

    public _28710ScalingRewards() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(799436).addOnQuestStart(questId);
        qe.registerQuestNpc(799436).addOnTalkEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
            if (targetId == 799436) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        QuestService.startQuest(env);
                        return sendQuestDialog(env, 1011);
                    }
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
            if (targetId == 799436) {
                switch (dialog) {
                    case USE_OBJECT:
                        return sendQuestDialog(env, 1011);
                    case SELECT_ACTION_1011:
                        if (player.getInventory().getItemCountByItemId(182006427) >= 4) {
                            removeQuestItem(env, 182006427, 4);
                            qs.setQuestVar(1);
                            qs.setStatus(QuestStatus.REWARD);
                            qs.setCompleteCount(0);
                            updateQuestStatus(env);
                            return sendQuestDialog(env, 5);
                        } else {
                            return sendQuestDialog(env, 1009);
                        }
                    case SELECT_ACTION_1352:
                        if (player.getInventory().getItemCountByItemId(182006427) >= 7) {
                            removeQuestItem(env, 182006427, 7);
                            qs.setQuestVar(2);
                            qs.setStatus(QuestStatus.REWARD);
                            qs.setCompleteCount(0);
                            updateQuestStatus(env);
                            return sendQuestDialog(env, 6);
                        } else {
                            return sendQuestDialog(env, 1009);
                        }
                    case SELECT_ACTION_1693:
                        if (player.getInventory().getItemCountByItemId(182006427) >= 10) {
                            removeQuestItem(env, 182006427, 10);
                            qs.setQuestVar(3);
                            qs.setStatus(QuestStatus.REWARD);
                            qs.setCompleteCount(0);
                            updateQuestStatus(env);
                            return sendQuestDialog(env, 7);
                        } else {
                            return sendQuestDialog(env, 1009);
                        }
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799436) {
                int var = qs.getQuestVarById(0);
                switch (dialog) {
                    case USE_OBJECT:
                        if (var == 1) {
                            return sendQuestDialog(env, 5);
                        } else if (var == 2) {
                            return sendQuestDialog(env, 6);
                        } else if (var == 3) {
                            return sendQuestDialog(env, 7);
                        } else if (var == 4) {
                            return sendQuestDialog(env, 8);
                        }
                    case SELECTED_QUEST_NOREWARD:
                        QuestService.finishQuest(env, qs.getQuestVars().getQuestVars() - 1);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                }
            }
        }
        return false;
    }
}
