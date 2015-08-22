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
package quest.inggison;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import static com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE.*;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author pralinka
 */
public class _14061HolyProblems extends QuestHandler {

    private final static int questId = 14061;

    public _14061HolyProblems() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {798927, 798954, 799022};
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerOnEnterWorld(questId);
        qe.registerOnDie(questId);
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
        qe.registerQuestItem(182215348, questId);
        qe.registerQuestItem(182215349, questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();
        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 798927: {
                    switch (dialog) {
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
                case 798954: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            } else if (var == 25) {
                                return sendQuestDialog(env, 3057);
                            }
                        }
                        case SETPRO2: {
                            return defaultCloseDialog(env, 1, 2);
                        }
                        case SET_SUCCEED: {
                            return defaultCloseDialog(env, 25, 25, true, false);
                        }
                    }
                    break;
                }
                case 799022: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 2) {
                                return sendQuestDialog(env, 1693);
                            } else if (var == 24) {
                                return sendQuestDialog(env, 2716);
                            }
                        }
                        case SETPRO3: {
                            if (var == 2) {
                                if (player.isInGroup2()) {
                                    return sendQuestDialog(env, 2546);
                                } else {
                                    if (giveQuestItem(env, 182215348, 1) && giveQuestItem(env, 182215349, 1)) {
                                        WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300190000);
                                        InstanceService.registerPlayerWithInstance(newInstance, player);
                                        TeleportService2.teleportTo(player, 300190000, newInstance.getInstanceId(), 202.26694f, 226.0532f,
                                                1098.236f, (byte) 30);
                                        changeQuestStep(env, 2, 3, false);
                                        return closeDialogWindow(env);
                                    } else {
                                        PacketSendUtility.sendPacket(player, STR_MSG_FULL_INVENTORY);
                                        return sendQuestSelectionDialog(env);
                                    }
                                }
                            }
                        }
                        case CHECK_USER_HAS_QUEST_ITEM: {
                            removeQuestItem(env, 182215348, 1);
                            removeQuestItem(env, 182215349, 1);
                            return checkQuestItems(env, 24, 25, false, 10000, 10001);
                        }
                        case FINISH_DIALOG: {
                            return sendQuestSelectionDialog(env);
                        }
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 798927) {
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
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (player.getWorldId() == 300190000) {
                int var = qs.getQuestVarById(0);
                int itemId = item.getItemId();
                if (itemId == 182215348) {
                    if (var == 3) {
                        changeQuestStep(env, 3, 4, false);
                        return HandlerResult.SUCCESS;
                    }
                } else if (itemId == 182215349) {
                    if (var >= 4 && var < 23) {
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(env);
                        return HandlerResult.SUCCESS;
                    } else if (var == 23) {
                        qs.setQuestVar(24);
                        updateQuestStatus(env);
                        return HandlerResult.SUCCESS;
                    }
                }
            }
        }
        return HandlerResult.UNKNOWN;
    }

    @Override
    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (player.getWorldId() != 300190000) {
                int var = qs.getQuestVarById(0);
                if (var >= 3 || var <= 24 || (var == 25 && player.getInventory().getItemCountByItemId(182215346) < 1)) {
                    removeQuestItem(env, 182215348, 1);
                    removeQuestItem(env, 182215349, 1);
                    qs.setQuestVar(2);
                    updateQuestStatus(env);
                    return true;
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
            if (var == 3 || var == 4 || (var == 25 && player.getInventory().getItemCountByItemId(182215346) < 1)) {
                removeQuestItem(env, 182215348, 1);
                removeQuestItem(env, 182215349, 1);
                qs.setQuestVar(2);
                updateQuestStatus(env);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 14060, true);
    }
}
