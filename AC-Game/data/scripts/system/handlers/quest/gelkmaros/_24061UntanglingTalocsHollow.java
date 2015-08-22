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

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import static com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE.*;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.TeleportAnimation;
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
public class _24061UntanglingTalocsHollow extends QuestHandler {

    private final static int questId = 24061;

    public _24061UntanglingTalocsHollow() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {799226, 799247, 799250, 799325, 799503, 799239};
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerOnEnterWorld(questId);
		qe.registerOnLogOut(questId);
        qe.registerOnDie(questId);
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
        qe.registerQuestNpc(215488).addOnKillEvent(questId);
        qe.registerQuestItem(182215381, questId);
        qe.registerQuestItem(182215382, questId);
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        return defaultOnKillEvent(env, 215488, 6, 7);
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
                case 799226: {
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
                case 799247: {
                    switch (dialog) {
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
                case 799250: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 2) {
                                return sendQuestDialog(env, 1693);
                            }
                        }
                        case SETPRO3: {
                            return defaultCloseDialog(env, 2, 3);
                        }
                    }
                    break;
                }
                case 799325: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 3) {
                                return sendQuestDialog(env, 2034);
                            }
                        }
                        case SETPRO4: {
                            if (var == 3) {
                                if (player.isInGroup2()) {
                                    return sendQuestDialog(env, 2546);
                                } else {
                                    if (giveQuestItem(env, 182215381, 1) && giveQuestItem(env, 182215382, 1)) {
                                        WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300190000);
                                        InstanceService.registerPlayerWithInstance(newInstance, player);
                                        TeleportService2.teleportTo(player, 300190000, newInstance.getInstanceId(), 202.26694f, 226.0532f,
                                                1098.236f, (byte) 30);
                                        changeQuestStep(env, 3, 4, false);
                                        return closeDialogWindow(env);
                                    } else {
                                        PacketSendUtility.sendPacket(player, STR_MSG_FULL_INVENTORY);
                                        return sendQuestSelectionDialog(env);
                                    }
                                }
                            }
                        }
                        case FINISH_DIALOG: {
                            return sendQuestSelectionDialog(env);
                        }
                    }
                    break;
                }
                case 799503: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 7) {
                                TeleportService2.teleportTo(player, 220070000, 991.1934f, 2560.148f, 239.909f, (byte) 118, TeleportAnimation.BEAM_ANIMATION);
                                changeQuestStep(env, 7, 8, false);
                                return closeDialogWindow(env);
                            }
                        }
                    }
                    break;
                }
                case 799239: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 8) {
                                return sendQuestDialog(env, 1608);
                            }
                        }
                        case SET_SUCCEED: {
                            return defaultCloseDialog(env, 8, 8, true, false);
                        }
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799226) {
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
                int var3 = qs.getQuestVarById(3);
                int itemId = item.getItemId();
                if (itemId == 182215382) {
                    if (var == 4) {
                        changeQuestStep(env, 4, 5, false);
                        return HandlerResult.SUCCESS;
                    }
                } else if (itemId == 182215381) {
                    if (var == 5) {

                        if (var3 >= 0 && var3 < 19) {
                            changeQuestStep(env, var3, var3 + 1, false, 3); // 3: 19
                            return HandlerResult.SUCCESS;
                        } else if (var3 == 19) {
                            qs.setQuestVar(6);
                            updateQuestStatus(env);
                            return HandlerResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return HandlerResult.UNKNOWN;
    }

    @Override
    public boolean onLogOutEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (player.getWorldId() != 300190000) {
                int var = qs.getQuestVarById(0);
                if (var >= 4 || var <= 6) {
                    removeQuestItem(env, 182215381, 1);
                    removeQuestItem(env, 182215382, 1);
                    qs.setQuestVar(3);
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
		if  (var >= 4 || var <= 6) {
                removeQuestItem(env, 182215381, 1);
                removeQuestItem(env, 182215382, 1);
                qs.setQuestVar(3);
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
        return defaultOnLvlUpEvent(env, 24060, true);
    }
}
