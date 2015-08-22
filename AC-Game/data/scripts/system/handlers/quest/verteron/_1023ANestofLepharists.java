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
package quest.verteron;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Mr. Poke
 * @author Dune11
 * @author vlog
 * @author Antraxx
 */
public class _1023ANestofLepharists extends QuestHandler {

    private final static int questId = 1023;

    public _1023ANestofLepharists() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(203098).addOnTalkEvent(questId);
        qe.registerQuestNpc(203183).addOnTalkEvent(questId);
        qe.registerOnEnterZone(ZoneName.get("LF1A_SENSORYAREA_Q1023_SPG_206008_2_210030000"), questId);
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerOnQuestTimerEnd(questId);
        qe.registerOnDie(questId);
        qe.registerOnEnterWorld(questId);
        qe.registerOnMovieEndQuest(23, questId);
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env, 1013);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        int[] quests = {1130, 1013};
        return defaultOnLvlUpEvent(env, quests, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 203098) // Spatalos
            {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        }
                    case SELECT_ACTION_1012:
                        return sendQuestDialog(env, 1012);
                    case SELECT_ACTION_1013:
                        return sendQuestDialog(env, 1013);
                    case SETPRO1:
                        return defaultCloseDialog(env, 0, 1); // 1
                }
            } else if (targetId == 203183) // Khidia
            {
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 1) {
                            return sendQuestDialog(env, 1352);
                        } else if (var == 3) {
                            return sendQuestDialog(env, 1693);
                        } else if (var == 4) {
                            return sendQuestDialog(env, 2034);
                        }
                    case SELECT_ACTION_1353:
                        playQuestMovie(env, 30);
                        return sendQuestDialog(env, 1353);
                    case SETPRO2:
                        if (var == 1) {
                            SkillEngine.getInstance().applyEffectDirectly(8197, player, player, 0);
                            QuestService.questTimerStart(env, 300);
                            updateQuestStatus(env);
                            return defaultCloseDialog(env, 1, 2); // 2
                        }
                    case SELECT_ACTION_1694:
                        return sendQuestDialog(env, 1694);
                    case SETPRO3:
                        if (var == 3) {
                            return defaultCloseDialog(env, 3, 4); // 4
                        }
                    case CHECK_USER_HAS_QUEST_ITEM:
                        if (var == 4) {
                            return checkQuestItems(env, 4, 4, false, 2120, 2035);
                        }
                    case FINISH_DIALOG:
                        return sendQuestDialog(env, 10);
                    case SETPRO4:
                        qs.setStatus(QuestStatus.REWARD);
                        qs.setQuestVarById(0, 5);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                        return true;
                    //return sendQuestSelectionDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203098) // Spatalos
            {
                if (env.getDialog() == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 2375);
                }
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public boolean onQuestTimerEndEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if ((var > 1) && (var < 3)) {
                changeQuestStep(env, var, 1, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onDieEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var > 1 && var < 3) {
                QuestService.questTimerEnd(env);
                return this.onQuestTimerEndEvent(env);
            }
        }
        return false;
    }

    @Override
    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var > 1 && var < 3) {
                QuestService.questTimerEnd(env);
                return this.onQuestTimerEndEvent(env);
            }
        }
        return false;
    }

    @Override
    public boolean onMovieEndEvent(QuestEnv env, int movieId) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && movieId == 23) {
            player.getEffectController().removeEffect(8197);
            qs.setQuestVarById(0, 3);
            updateQuestStatus(env);
            return true;
        }
        return false;
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        if (zoneName != ZoneName.get("LF1A_SENSORYAREA_Q1023_SPG_206008_2_210030000")) {
            return false;
        }
        final Player player = env.getPlayer();
        if (player == null) {
            return false;
        }
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null || qs.getQuestVars().getQuestVars() != 2) {
            return false;
        }
        if (qs.getQuestVars().getVarById(0) == 2) {
            QuestService.questTimerEnd(env);
            playQuestMovie(env, 23);
            return true;
        }
        return false;
    }
}
