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
package quest.tiamaranta;

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
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Cheatkiller
 *
 */
public class _12004CanFlyOnHungryWings extends QuestHandler {

    private final static int questId = 12004;

    public _12004CanFlyOnHungryWings() {
        super(questId);
    }

    public void register() {
        qe.registerOnDie(questId);
        qe.registerOnLogOut(questId);
        qe.registerQuestNpc(205897).addOnQuestStart(questId);
        qe.registerQuestNpc(205897).addOnTalkEvent(questId);
        qe.registerQuestNpc(205948).addOnTalkEvent(questId);
        qe.registerQuestNpc(205936).addOnTalkEvent(questId);
        qe.registerAddOnReachTargetEvent(questId);
        qe.registerAddOnLostTargetEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 205897) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 205897) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                }
                if (dialog == DialogAction.SETPRO1) {
                    Npc brax = (Npc) QuestService.spawnQuestNpc(player.getWorldId(), player.getInstanceId(), 701367, 287.21f, 1637.4f, 293.55f, (byte) 113);
                    brax.getSpawn().setWalkerId("12004");
                    WalkManager.startWalking((NpcAI2) brax.getAi2());
                    PacketSendUtility.broadcastPacket(brax, new SM_EMOTION(brax, EmotionType.START_EMOTE2, 0, brax.getObjectId()));
                    player.getController().addTask(TaskId.QUEST_FOLLOW, QuestTasks.newFollowingToTargetCheckTask(env, brax, 733.65125f, 1540.8422f, 733.651f));
                    return defaultCloseDialog(env, 0, 1);
                }
            } else if (targetId == 205948) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1693);
                }
                if (dialog == DialogAction.SET_SUCCEED) {
                    giveQuestItem(env, 182212590, 1);
                    return defaultCloseDialog(env, 2, 3, true, false);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205936) {
                switch (dialog) {
                    case USE_OBJECT: {
                        return sendQuestDialog(env, 10002);
                    }
                    default: {
                        removeQuestItem(env, 182212590, 1);
                        return sendQuestEndDialog(env);
                    }
                }
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
            if (var == 1) {
                qs.setQuestVar(0);
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
            if (var == 1) {
                qs.setQuestVar(0);
                updateQuestStatus(env);
            }
        }
        return false;
    }

    @Override
    public boolean onNpcReachTargetEvent(QuestEnv env) {
        return defaultFollowEndEvent(env, 1, 2, false);
    }

    @Override
    public boolean onNpcLostTargetEvent(QuestEnv env) {
        return defaultFollowEndEvent(env, 1, 0, false);
    }
}
