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
package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * Talk with Olenja (203606).<br>
 * Search Grave Robbers Den and find out what the Lepharist Revolutionaries are
 * looking for (700136).<br>
 * Report the result to Olenja.<br>
 * Talk with Hunmir (203633).<br>
 * Search the Lepharist Escort Wagon (700135) on the path to the Grave Robbers
 * Den.<br>
 * Report the result to Nokir (203631).<br>
 *
 * @author Mr. Poke
 * @reworked vlog
 */
public class _2014ScoutitOut extends QuestHandler {

    private final static int questId = 2014;

    public _2014ScoutitOut() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {203606, 700136, 203633, 203631};
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerGetingItem(182203015, questId);
        qe.registerQuestNpc(700135).addOnKillEvent(questId);
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();

        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 203606: { // Olenja
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            } else if (var == 2) {
                                if (player.getInventory().getItemCountByItemId(182203015) == 0) {
                                    return sendQuestDialog(env, 1438);
                                } else {
                                    return sendQuestDialog(env, 1352);
                                }
                            }
                        }
                        case SETPRO1: {
                            return defaultCloseDialog(env, 0, 1); // 1
                        }
                        case SETPRO2: {
                            return defaultCloseDialog(env, 2, 3, 0, 0, 182203015, 1); // 3
                        }
                        case FINISH_DIALOG: {
                            return sendQuestSelectionDialog(env);
                        }
                    }
                    break;
                }
                case 700136: { // Suspicious document
                    if (dialog == DialogAction.USE_OBJECT && var == 1) {
                        if (!player.getInventory().isFullSpecialCube()) {
                            return true; // loot
                        }
                    }
                    break;
                }
                case 203633: { // Hunmir
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 3) {
                                return sendQuestDialog(env, 1693);
                            }
                        }
                        case SETPRO3: {
                            return defaultCloseDialog(env, 3, 4); // 4
                        }
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203631) {
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 2034);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onGetItemEvent(QuestEnv env) {
        return defaultOnGetItemEvent(env, 1, 2, false); // 2
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        return defaultOnKillEvent(env, 700135, 4, true); // reward
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 2200, true);
    }
}
