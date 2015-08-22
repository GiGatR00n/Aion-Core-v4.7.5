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
package quest.siels_spear;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.SystemMessageId;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Cheatkiller
 *
 */
public class _41450Steps_To_The_Spear extends QuestHandler {

    private final static int questId = 41450;
    private final static int[] npc_ids = {205798, 205799, 205800, 205801, 205579, 730527, 800280, 800298};

    public _41450Steps_To_The_Spear() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnDie(questId);
        qe.registerOnLogOut(questId);
        qe.registerOnQuestTimerEnd(questId);
        qe.registerQuestNpc(205579).addOnQuestStart(questId);
        for (int npc_id : npc_ids) {
            qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 205579) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else if (dialog == DialogAction.QUEST_ACCEPT_SIMPLE) {
                    QuestService.questTimerStart(env, 780);
                    return sendQuestStartDialog(env);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (targetId == 205798) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else if (dialog == DialogAction.SETPRO1) {
                    return defaultCloseDialog(env, 0, 1);
                }
            }
            if (targetId == 205799 && var == 1) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1352);
                } else if (dialog == DialogAction.SETPRO2) {
                    return defaultCloseDialog(env, 1, 2);
                }
            }
            if (targetId == 205800 && var == 2) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1693);
                } else if (dialog == DialogAction.SETPRO3) {
                    return defaultCloseDialog(env, 2, 3);
                }
            }
            if (targetId == 205801 && var == 3) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 2034);
                } else if (dialog == DialogAction.SETPRO4) {
                    return defaultCloseDialog(env, 3, 4);
                }
            }
            if (targetId == 205579 && var == 4) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 2375);
                } else if (dialog == DialogAction.SETPRO5) {
                    return defaultCloseDialog(env, 4, 5);
                }
            }
            if (targetId == 205579 && var == 5) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 2716);
                } else if (dialog == DialogAction.CHECK_USER_HAS_QUEST_ITEM_SIMPLE) {
                    return checkQuestItems(env, 5, 6, true, 10002, 10001);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205579) {
                if (dialog == DialogAction.SELECT_QUEST_REWARD) {
                    QuestService.questTimerEnd(env);
                    return sendQuestDialog(env, 5);
                } else {
                    return sendQuestEndDialog(env);
                }
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
            if (var > 1) {
                qs.setStatus(QuestStatus.NONE);
                qs.setQuestVar(0);
                updateQuestStatus(env);
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1,
                        DataManager.QUEST_DATA.getQuestById(questId).getName()));
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
            if (var >= 0) {
                qs.setStatus(QuestStatus.NONE);
                qs.setQuestVar(0);
                updateQuestStatus(env);
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1,
                        DataManager.QUEST_DATA.getQuestById(questId).getName()));
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
            if (var >= 0) {
                qs.setStatus(QuestStatus.NONE);
                qs.setQuestVar(0);
                updateQuestStatus(env);
                return true;
            }
        }
        return false;
    }
}
