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

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author zhkchi
 */
public class _10050SomethingToProve extends QuestHandler {

    private final static int questId = 10050;

    public _10050SomethingToProve() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(205535).addOnQuestStart(questId);
        qe.registerQuestNpc(205535).addOnTalkEvent(questId);
        qe.registerQuestNpc(205581).addOnTalkEvent(questId);
        qe.registerQuestNpc(205764).addOnTalkEvent(questId);
        qe.registerQuestNpc(218663).addOnKillEvent(questId);
        qe.registerQuestNpc(218664).addOnKillEvent(questId);
        qe.registerQuestNpc(218665).addOnKillEvent(questId);
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 10040, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null) {
            if (targetId == 205535) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        }

        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 205535 && var == 0) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else if (env.getDialog() == DialogAction.SETPRO1) {
                    return defaultCloseDialog(env, 0, 1);
                }
            } else if (targetId == 205581 && var == 1) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1352);
                } else if (env.getDialog() == DialogAction.SELECT_ACTION_1352) {
                    return sendQuestDialog(env, 1353);
                } else if (env.getDialog() == DialogAction.SETPRO2) {
                    return defaultCloseDialog(env, 1, 2);
                }
            } else if (targetId == 205764 && var == 2) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1693);
                } else if (env.getDialog() == DialogAction.SETPRO3) {
                    return defaultCloseDialog(env, 2, 3);
                }
            }
            return false;
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205581) {
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                } else {
                    QuestEngine.getInstance().onEnterZoneMissionEnd(
                            new QuestEnv(env.getVisibleObject(), env.getPlayer(), 10051, env.getDialogId()));
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = env.getTargetId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }

        switch (targetId) {
            case 218663:
            case 218664:
                if (qs.getQuestVarById(1) < 3 && qs.getQuestVarById(0) == 3) {
                    qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
                    if (qs.getQuestVarById(1) == 3) {
                        Npc npc = (Npc) env.getVisibleObject();
                        QuestService.addNewSpawn(npc.getWorldId(), npc.getInstanceId(), 218665, npc.getX(), npc.getY(),
                                npc.getZ(), (byte) 0);
                    }
                    return true;
                }
                break;
            case 218665: {
                if (qs.getQuestVarById(1) >= 3 && qs.getQuestVarById(0) == 3) {
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(env);
                    return true;
                }
            }

        }
        return false;

    }
}
