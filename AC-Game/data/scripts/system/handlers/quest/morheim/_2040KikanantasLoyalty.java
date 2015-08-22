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
package quest.morheim;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Hellboy aion4Free
 */
public class _2040KikanantasLoyalty extends QuestHandler {

    private final static int questId = 2040;
    private final static int[] npc_ids = {204388, 204414, 204304, 204345};

    public _2040KikanantasLoyalty() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        for (int npc_id : npc_ids) {
            qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env, 2039);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        int[] quests = {2300, 2039};
        return defaultOnLvlUpEvent(env, quests, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 204388: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT:
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            } else if (var == 3) {
                                return sendQuestDialog(env, 2034);
                            }
                        case SETPRO1:
                            if (var == 0) {
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(env);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                                return true;
                            }
                        case SETPRO4:
                            if (var == 3) {
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(env);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                                return true;
                            }
                    }
                }
                break;
                case 204345: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT:
                            if (var == 4) {
                                return sendQuestDialog(env, 2375);
                            }
                        case SET_SUCCEED:
                            if (var == 4) {
                                qs.setStatus(QuestStatus.REWARD);
                                updateQuestStatus(env);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                                return true;
                            }
                    }
                }
                break;
                case 204414: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT:
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            } else if (var == 2) {
                                return sendQuestDialog(env, 1693);
                            }
                        case SELECT_ACTION_1354:
                            playQuestMovie(env, 85);
                            break;
                        case SETPRO2:
                            if (var == 1) {
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(env);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                                return true;
                            }
                        case SETPRO3:
                            if (var == 2) {
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(env);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                                return true;
                            }
                        case CHECK_USER_HAS_QUEST_ITEM:
                            if (var == 2) {
                                if (QuestService.collectItemCheck(env, false)) {
                                    return sendQuestDialog(env, 10000);
                                } else {
                                    return sendQuestDialog(env, 10001);
                                }
                            }
                    }
                }
                break;
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 204304) {
                if (env.getDialog() == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                } else {
                    removeQuestItem(env, 182204018, 1);
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }
}
