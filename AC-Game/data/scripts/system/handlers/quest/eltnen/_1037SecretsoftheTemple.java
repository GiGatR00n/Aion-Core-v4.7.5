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
package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/**
 * @author Rhys2002
 * @reworked vlog
 */
public class _1037SecretsoftheTemple extends QuestHandler {

    private final static int questId = 1037;

    public _1037SecretsoftheTemple() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {203965, 203967, 700151, 700154, 700150, 700153, 700152};
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerGetingItem(182201027, questId);
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onDialogEvent(final QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();
        if (qs == null) {
            return false;
        }

        if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            switch (targetId) {
                case 203965: { // Castor
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
                    break;
                }
                case 203967: { // Axelion
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            } else if (var == 2) {
                                return sendQuestDialog(env, 1693);
                            }
                        }
                        case SELECT_ACTION_1694: {
                            if (var == 2 && QuestService.collectItemCheck(env, true)) {
                                return sendQuestDialog(env, 1694);
                            } else {
                                return sendQuestDialog(env, 1779);
                            }
                        }
                        case SETPRO2: {
                            return defaultCloseDialog(env, 1, 2); // 2
                        }
                        case SETPRO3: {
                            giveQuestItem(env, 182201027, 1);
                            return sendQuestSelectionDialog(env);
                        }
                    }
                    break;
                }
                case 700151: { // Flower Wall
                    if (dialog == DialogAction.USE_OBJECT) {
                        changeQuestStep(env, 3, 4, false); // 4
                        return true;
                    }
                    break;
                }
                case 700154: { // Lightning Wall
                    if (dialog == DialogAction.USE_OBJECT) {
                        changeQuestStep(env, 4, 5, false); // 5
                        return true;
                    }
                    break;
                }
                case 700150: { // Wave Wall
                    if (dialog == DialogAction.USE_OBJECT) {
                        changeQuestStep(env, 5, 6, false); // 6
                        return true;
                    }
                    break;
                }
                case 700153: { // Wind Wall
                    if (dialog == DialogAction.USE_OBJECT) {
                        changeQuestStep(env, 6, 7, false); // 7
                        return true;
                    }
                    break;
                }
                case 700152: { // Fire Wall
                    if (dialog == DialogAction.USE_OBJECT) {
                        changeQuestStep(env, 7, 7, true); // reward
                        removeQuestItem(env, 182201027, 1);
                        return true;
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203965) { // Castor
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
        return defaultOnGetItemEvent(env, 2, 3, false); // 3
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 1300, true);
    }
}
