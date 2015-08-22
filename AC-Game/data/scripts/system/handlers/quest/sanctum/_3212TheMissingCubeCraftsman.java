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
package quest.sanctum;

import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.questEngine.task.QuestTasks;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Cheatkiller
 *
 */
public class _3212TheMissingCubeCraftsman extends QuestHandler {

    private final static int questId = 3212;

    public _3212TheMissingCubeCraftsman() {
        super(questId);
    }

    public void register() {
        qe.registerQuestNpc(798321).addOnQuestStart(questId);
        qe.registerQuestNpc(203838).addOnTalkEvent(questId);
        qe.registerQuestNpc(798011).addOnTalkEvent(questId);
        qe.registerQuestNpc(798337).addOnTalkEvent(questId);
        qe.registerQuestNpc(730208).addOnTalkEvent(questId);
        qe.registerAddOnReachTargetEvent(questId);
        qe.registerAddOnLostTargetEvent(questId);
        qe.registerOnDie(questId);
        qe.registerOnLogOut(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 798321) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 203838) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 0) {
                        return sendQuestDialog(env, 1011);
                    }
                } else if (dialog == DialogAction.SETPRO1) {
                    return defaultCloseDialog(env, 0, 1);
                }
            } else if (targetId == 798011) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 1) {
                        return sendQuestDialog(env, 1352);
                    }
                } else if (dialog == DialogAction.SETPRO2) {
                    return defaultCloseDialog(env, 1, 2);
                }
            } else if (targetId == 798337) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 2) {
                        return sendQuestDialog(env, 1693);
                    }
                } else if (dialog == DialogAction.SETPRO3) {
                    Npc npc = (Npc) env.getVisibleObject();
                    npc.getSpawn().setWalkerId("4212");
                    WalkManager.startWalking((NpcAI2) npc.getAi2());
                    PacketSendUtility.broadcastPacket(npc, new SM_EMOTION(npc, EmotionType.START_EMOTE2, 0, npc.getObjectId()));
                    player.getController().addTask(TaskId.QUEST_FOLLOW, QuestTasks.newFollowingToTargetCheckTask(env, npc, 505.69427f, 437.69382f, 885.1844f));
                    return defaultCloseDialog(env, 2, 3);
                }
            } else if (targetId == 730208) {
                Npc npc = (Npc) env.getVisibleObject();
                npc.getController().delete();
                return true;
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 798011) {
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                }
                return sendQuestEndDialog(env);
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
            if (var == 3) {
                qs.setQuestVar(2);
                updateQuestStatus(env);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onLogOutEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 3) {
                qs.setQuestVar(2);
                updateQuestStatus(env);
            }
        }
        return false;
    }

    @Override
    public boolean onNpcReachTargetEvent(QuestEnv env) {
        return defaultFollowEndEvent(env, 3, 4, true); // reward
    }

    @Override
    public boolean onNpcLostTargetEvent(QuestEnv env) {
        return defaultFollowEndEvent(env, 3, 2, false);
    }
}
