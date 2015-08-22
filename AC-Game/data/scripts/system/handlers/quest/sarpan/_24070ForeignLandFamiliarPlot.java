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
package quest.sarpan;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * @author pralinka
 */
public class _24070ForeignLandFamiliarPlot extends QuestHandler {

    private final static int questId = 24070;

    public _24070ForeignLandFamiliarPlot() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(205617).addOnTalkEvent(questId);
        qe.registerQuestNpc(205585).addOnTalkEvent(questId);
        qe.registerQuestNpc(205739).addOnTalkEvent(questId);
        qe.registerQuestNpc(730468).addOnTalkEvent(questId);
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 24062, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 205617: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            }
                        }
                        case SETPRO1: {
                            return defaultCloseDialog(env, 0, 1);
                        }
                    }
                    break;
                }
                case 205585: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            } else if (var == 4) {
                                return sendQuestDialog(env, 2375);
                            }
                        }
                        case SETPRO2: {
                            return defaultCloseDialog(env, 1, 2);
                        }
                        case CHECK_USER_HAS_QUEST_ITEM: {
                            return checkQuestItems(env, 4, 4, true, 10000, 10001);
                        }
                    }
                    break;
                }
                case 205739: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 2) {
                                return sendQuestDialog(env, 1693);
                            }
                        }
                        case SETPRO3: {
                            if (var == 2) {
                                return defaultCloseDialog(env, 2, 3);
                            }
                        }
                    }
                    break;
                }
                case 730468: {
                    switch (dialog) {
                        case USE_OBJECT: {
                            if (var == 3) {
                                TeleportService2.teleportTo(player, 600020000, 1, 1302, 1825, 500.48282f, (byte) 0,
                                        TeleportAnimation.BEAM_ANIMATION);
                                changeQuestStep(env, 3, 4, false);
                                return closeDialogWindow(env);
                            }
                        }
                        break;
                    }
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205585) {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 10002);
                    case SELECT_QUEST_REWARD:
                        return sendQuestDialog(env, 5);
                    default:
                        return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }
}
