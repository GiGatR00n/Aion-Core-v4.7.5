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
package quest.gelkmaros;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * @author pralinka
 */
public class _24060MarchToBalaurea extends QuestHandler {

    private final static int questId = 24060;

    public _24060MarchToBalaurea() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerOnEnterWorld(questId);
        qe.registerOnMovieEndQuest(551, questId);
        qe.registerQuestNpc(204052).addOnTalkEvent(questId); // vidar
        qe.registerQuestNpc(798800).addOnTalkEvent(questId); // agehia
        qe.registerQuestNpc(798409).addOnTalkEvent(questId); // tigrina
        qe.registerQuestNpc(799225).addOnTalkEvent(questId); // richelle
        qe.registerQuestNpc(799364).addOnTalkEvent(questId); // merhen
        qe.registerQuestNpc(799365).addOnTalkEvent(questId); // hogidin
        qe.registerQuestNpc(799226).addOnTalkEvent(questId); // valetta
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        Player player = env.getPlayer();
        PlayerClass playerClass = player.getCommonData().getPlayerClass();
            if (playerClass != PlayerClass.RIDER) {
            return false;
            }
        return defaultOnLvlUpEvent(env);
    }

    @Override
    public boolean onMovieEndEvent(QuestEnv env, int movieId) {
        if (movieId != 551) {
            return false;
        }
        Player player = env.getPlayer();
        if (player.getCommonData().getRace() != Race.ASMODIANS) {
            return false;
        }
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }
        changeQuestStep(env, 3, 3, false);
        updateQuestStatus(env);
        return true;
    }

    @Override
    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVars().getQuestVars();
            if (var == 3) {
                if (player.getWorldId() == 220070000) {
                    return playQuestMovie(env, 551);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 204052: {
                    switch (env.getDialog()) {
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
                case 798800: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT: {
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            }
                        }
                        case SETPRO2: {
                            return defaultCloseDialog(env, 1, 2);
                        }
                    }
                    break;
                }
                case 798409: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT: {
                            if (var == 2) {
                                return sendQuestDialog(env, 1693);
                            }
                        }
                        case SETPRO3: {
                            TeleportService2.teleportTo(player, 220070000, 1, 1868, 2746, 531, (byte) 20);
                            return defaultCloseDialog(env, 2, 3);
                        }
                    }
                    break;
                }
                case 799225: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT: {
                            if (var == 3) {
                                return sendQuestDialog(env, 2034);
                            }
                        }
                        case SETPRO4: {
                            return defaultCloseDialog(env, 3, 4);
                        }
                    }
                    break;
                }
                case 799364: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT: {
                            if (var == 4) {
                                return sendQuestDialog(env, 2375);
                            }
                        }
                        case SETPRO5: {
                            return defaultCloseDialog(env, 4, 5);
                        }
                    }
                    break;
                }
                case 799365: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT: {
                            if (var == 5) {
                                return sendQuestDialog(env, 2716);
                            }
                        }
                        case SET_SUCCEED: {
                            return defaultCloseDialog(env, 5, 5, true, false);
                        }
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799226) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 2802);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }
}
