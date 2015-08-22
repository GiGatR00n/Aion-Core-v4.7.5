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
package quest.theobomos;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/**
 * @author vlog
 */
public class _1094ProjectDrakanhammer extends QuestHandler {

    private final static int questId = 1094;

    public _1094ProjectDrakanhammer() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npc_ids = {203834, 798155, 700411, 730153};
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        for (int npc_id : npc_ids) {
            qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env, 1093);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        int[] quests = {1091, 1093};
        return defaultOnLvlUpEvent(env, quests, true);
    }

    @Override
    public boolean onDialogEvent(final QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();

        if (qs.getStatus() == QuestStatus.START) {
            switch (var) {
                case 0: {
                    if (targetId == 203834) { // Nestor
                        switch (dialog) {
                            case QUEST_SELECT: {
                                return sendQuestDialog(env, 1011);
                            }
                            case SETPRO1: {
                                return defaultCloseDialog(env, 0, 1); // 1
                            }
                        }
                    }
                }
                case 1: {
                    if (targetId == 798155) { // Atropos
                        switch (dialog) {
                            case QUEST_SELECT: {
                                return sendQuestDialog(env, 1352);
                            }
                            case SELECT_ACTION_1353: {
                                playQuestMovie(env, 367);
                                break;
                            }
                            case SETPRO2: {
                                return defaultCloseDialog(env, 1, 2); // 2
                            }
                        }
                    }
                }
                case 2: {
                    if (targetId == 700411) { // Research Diary
                        if (dialog == DialogAction.USE_OBJECT) {
                            if (giveQuestItem(env, 182208017, 1)) {
                                closeDialogWindow(env);
                                changeQuestStep(env, 2, 3, false); // 3
                                return true;
                            }
                        }
                    }
                }
                case 3: {
                    if (targetId == 730153) { // Assistant's Journal
                        if (dialog == DialogAction.USE_OBJECT) {
                            QuestService.collectItemCheck(env, true);
                            removeQuestItem(env, 182208017, 1);
                            qs.setQuestVar(4); // 4
                            qs.setStatus(QuestStatus.REWARD); // reward
                            updateQuestStatus(env);
                            return true;
                        }
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203834) { // Nestor
                if (env.getDialog() == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }
}
