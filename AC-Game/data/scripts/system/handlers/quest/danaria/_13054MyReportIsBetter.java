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
package quest.danaria;

import com.aionemu.gameserver.model.gameobjects.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
* @author pralinka
*/
public class _13054MyReportIsBetter extends QuestHandler {

    private final static int questId = 13054;

    public _13054MyReportIsBetter() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(801091).addOnQuestStart(questId); //neaira
        qe.registerQuestNpc(801091).addOnTalkEvent(questId); //neaira
        qe.registerQuestNpc(801090).addOnTalkEvent(questId); //thetys
        qe.registerQuestNpc(801092).addOnTalkEvent(questId); //odoacer
        qe.registerQuestItem(182213405, questId); // Tethys' Report
		qe.registerQuestItem(182213406, questId); //Revised Report
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 801091) {
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
        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 801090: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT: {
                            return sendQuestDialog(env, 1352);
                        }
                        case SETPRO1: {
                            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                            updateQuestStatus(env);
							giveQuestItem(env, 182213405, 1);
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                            return true;
                        }
                    }
                }
				case 801091: {
					switch (env.getDialog()) {
                        case QUEST_SELECT: {
                            return sendQuestDialog(env, 1693);
                        }
						case SETPRO2: {
							removeQuestItem(env, 182213405, 1);
							giveQuestItem(env, 182213406, 1);
							qs.setQuestVar(2);
							changeQuestStep(env, 1, 2, false);
							updateQuestStatus(env);							
							return closeDialogWindow(env);
                        }
					}
				}
                case 801092: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT: {
                            return sendQuestDialog(env, 2375);
                        }
                        case SELECT_QUEST_REWARD: {
                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(env);
                            removeQuestItem(env, 182213406, 1);
                            return sendQuestEndDialog(env);
                        }
                        default:
                            return sendQuestEndDialog(env);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 801092) {
                switch (env.getDialog()) {
                    case SET_SUCCEED: {
                        return sendQuestDialog(env, 5);
                    }
                    default:
                        return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (qs.getQuestVarById(0) == 2) {
                qs.setQuestVar(2);
                changeQuestStep(env, 2, 3, false);
                return HandlerResult.SUCCESS;
            }
        }
        return HandlerResult.FAILED;
    }
}