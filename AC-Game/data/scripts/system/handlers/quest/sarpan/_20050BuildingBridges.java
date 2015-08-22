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

import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * @author vlog
 */
public class _20050BuildingBridges extends QuestHandler {

    private static final int questId = 20050;
    private static int[] mephiticKlaws = {217849, 217852, 217854, 217853, 217851, 217850};

    public _20050BuildingBridges() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcIds = {205617, 205585, 205739, 730468};
        qe.registerOnLevelUp(questId);
        qe.registerOnEnterZoneMissionEnd(questId);
        for (int npcId : npcIds) {
            qe.registerQuestNpc(npcId).addOnTalkEvent(questId);
        }
        for (int klaw : mephiticKlaws) {
            qe.registerQuestNpc(klaw).addOnKillEvent(questId);
        }
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
		Player player = env.getPlayer();
        PlayerClass playerClass = player.getCommonData().getPlayerClass();
            if (playerClass == PlayerClass.RIDER) {
            return false;
            }
        return defaultOnLvlUpEvent(env, 20040, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            switch (targetId) {
                case 205617: { // Aimah
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
                case 205585: { // Rafael
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            }
                        }
                        case SELECT_ACTION_1353: {
                            if (var == 1) {
                                sendQuestDialog(env, 1352);
                                return playQuestMovie(env, 714);
                            }
                        }
                        case SETPRO2: {
                            return defaultCloseDialog(env, 1, 2); // 2
                        }
                    }
                    break;
                }
                case 205739: { // Eyriss
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 2) {
                                return sendQuestDialog(env, 1693);
                            }
                        }
                        case SETPRO3: {
                            return defaultCloseDialog(env, 2, 3); // 3
                        }
                    }
                    break;
                }
                case 730468: { // Mephitic Caverns Sealing Gate
                    switch (dialog) {
                        case USE_OBJECT: {
                            if (var == 3) {
                                return sendQuestDialog(env, 2034);
                            }
                        }
                        case SETPRO4: {
                            if (var == 3) {
                                changeQuestStep(env, 3, 4, false); // 4
                                TeleportService2.teleportTo(player, 600020000, 1, 1302, 1825, 500.48282f, (byte) 0,
                                        TeleportAnimation.BEAM_ANIMATION);
                                return closeDialogWindow(env);
                            }
                        }
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205585) { // Rafael
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var >= 4 && var < 13) {
                return defaultOnKillEvent(env, mephiticKlaws, var, var + 1); // 5 - 13
            } else if (var == 13) {
                return defaultOnKillEvent(env, mephiticKlaws, 13, true); // reward
            }
        }
        return false;
    }
}
