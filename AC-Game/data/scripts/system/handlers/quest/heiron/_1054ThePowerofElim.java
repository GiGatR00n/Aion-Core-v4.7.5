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
package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Rhys2002
 * @reworked vlog
 */
public class _1054ThePowerofElim extends QuestHandler {

    private final static int questId = 1054;

    public _1054ThePowerofElim() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {730024, 204647, 730008, 730019};
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        if (qs == null) {
            return false;
        }
        int targetId = env.getTargetId();

        if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (targetId == 730024) { // Trajanus
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        }
                    }
                    case SETPRO1: {
                        return defaultCloseDialog(env, 0, 1); // 1
                    }
                }
            } else if (targetId == 204647) { // Voice of Arbolu
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 1) {
                            return sendQuestDialog(env, 1352);
                        } else if (var == 4) {
                            return sendQuestDialog(env, 2375);
                        } else if (var == 5) {
                            return sendQuestDialog(env, 2716);
                        }
                    }
                    case SETPRO2: {
                        return defaultCloseDialog(env, 1, 2); // 2
                    }
                    case SELECT_ACTION_2376: {
                        if (player.getInventory().getItemCountByItemId(182201606) > 0
                                && player.getInventory().getItemCountByItemId(182201607) > 0) {
                            return sendQuestDialog(env, 2376);
                        } else {
                            return sendQuestDialog(env, 2461);
                        }
                    }
                    case SELECT_ACTION_2377: {
                        return playQuestMovie(env, 187);
                    }
                    case SETPRO5: {
                        removeQuestItem(env, 182201606, 1);
                        removeQuestItem(env, 182201607, 1);
                        return defaultCloseDialog(env, 4, 5); // 5
                    }
                    case CHECK_USER_HAS_QUEST_ITEM: {
                        return checkQuestItems(env, 5, 5, true, 5, 10001); // reward
                    }
                    case FINISH_DIALOG: {
                        return sendQuestSelectionDialog(env);
                    }
                }
            } else if (targetId == 730008) { // Daminu
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 2) {
                            return sendQuestDialog(env, 1693);
                        }
                    }
                    case SETPRO3: {
                        return defaultCloseDialog(env, 2, 3, 182201606, 1, 0, 0); // 3
                    }
                }
            } else if (targetId == 730019) { // Lodas
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 3) {
                            return sendQuestDialog(env, 2034);
                        }
                    }
                    case SETPRO4: {
                        return defaultCloseDialog(env, 3, 4, 182201607, 1, 0, 0); // 4
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 204647) { // Voice of Arbolu
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 1500, true);
    }
}
