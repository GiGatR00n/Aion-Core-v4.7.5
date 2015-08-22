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
package quest.katalam;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.zone.ZoneName;

//By Evil_dnk

public class _23048HeadstrongandLost extends QuestHandler {

	private final static int questId = 23048;

	public _23048HeadstrongandLost() {
		super(questId);
	}

	public void register() {
		qe.registerQuestNpc(801063).addOnQuestStart(questId);
		qe.registerQuestNpc(801063).addOnTalkEvent(questId);
		qe.registerQuestNpc(801282).addOnTalkEvent(questId);
		qe.registerQuestNpc(801278).addOnTalkEvent(questId);
        qe.registerOnLogOut(questId);
        qe.registerAddOnReachTargetEvent(questId);
        qe.registerAddOnLostTargetEvent(questId);

    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = env.getTargetId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 801063) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                }
                else {
                    return sendQuestStartDialog(env);
                }
            }
        }
        else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 801282)
            {
                if (dialog == DialogAction.USE_OBJECT) {
                    if (qs.getQuestVarById(0) == 0) {
                        return sendQuestDialog(env, 1011);
                    }
                    if (qs.getQuestVarById(0) == 1)
                        return closeDialogWindow(env);
                    }

                if (dialog == DialogAction.SETPRO1) {
                    if (qs.getQuestVarById(0) == 0) {
                        changeQuestStep(env, 0, 1, false);
                        updateQuestStatus(env);
                        return defaultStartFollowEvent(env, (Npc) env.getVisibleObject(), ZoneName.get("GOLDRINE_VILLAGE_600060000"), 0, 1);
                    }
                }
            }
                if (targetId == 801278)
                {
                    if (dialog == DialogAction.QUEST_SELECT) {
                        if (qs.getQuestVarById(0) == 0) {
                            return sendQuestDialog(env, 1011);
                        }
                        if (qs.getQuestVarById(0) == 1)
                            return closeDialogWindow(env);
                    }

                    if (dialog == DialogAction.SETPRO1) {
                        if (qs.getQuestVarById(0) == 0) {
                            Npc npc = (Npc) env.getVisibleObject();
                            changeQuestStep(env, 0, 1, false);
                            updateQuestStatus(env);
                            Npc clar = (Npc) QuestService.spawnQuestNpc(npc.getWorldId(), npc.getInstanceId(), 801282, player.getX(), player.getY(), player.getZ(), (byte) 0);
                            npc.getController().onDieSilence();

                            defaultStartFollowEvent(env, clar, ZoneName.get("GOLDRINE_VILLAGE_600060000"), 0, 1);
                            return  closeDialogWindow(env);
                        }
                    }
                }

        }

        else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 801063) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }


    @Override
    public boolean onNpcReachTargetEvent(QuestEnv env) {
        return defaultFollowEndEvent(env, 1, 1, true);
    }

    @Override
    public boolean onNpcLostTargetEvent(QuestEnv env) {
        return defaultFollowEndEvent(env, 1, 0, false);
    }
    @Override
    public boolean onLogOutEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 1) {
                changeQuestStep(env, 1, 0, false);
            }
        }
        return false;
    }
}
