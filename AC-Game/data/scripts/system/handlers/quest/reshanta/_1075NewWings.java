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
package quest.reshanta;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Rhys2002
 * @author vlog
 * @author Antraxx
 */
public class _1075NewWings extends QuestHandler {

    private final static int questId = 1075;
    private boolean killed = false;

    public _1075NewWings() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {278506, 279023, 278643};
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(214102).addOnKillEvent(questId);
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
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

        DialogAction dialog = env.getDialog();

        if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 279023) { // Agemonerk
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                } else if (dialog == DialogAction.SELECT_QUEST_REWARD) {
                    return sendQuestDialog(env, 5);
                }
                return sendQuestEndDialog(env);
            }
            return false;
        } else if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 278506: { // Tellus
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            }
                        }
                        case SELECT_ACTION_1013: {
                            playQuestMovie(env, 272);
                            break;
                        }
                        case SETPRO1: {
                            return defaultCloseDialog(env, 0, 1); // 1
                        }
                    }
                    break;
                }
                case 279023: { // Agemonerk
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            }
                        }
                        case SETPRO2: {
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(env);
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                            PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.START_FLYTELEPORT, 57001, 0), true);
                            return true;
                        }
                    }
                    break;
                }
                case 278643: { // Raithor
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 2) {
                                return sendQuestDialog(env, 1693);
                            } else if (var == 3) {
                                if (killed) {
                                    return sendQuestDialog(env, 2034);
                                }
                            }
                        }
                        case SETPRO3: {
                            if (var == 2) {
                                qs.setQuestVarById(0, var + 1);
                                updateQuestStatus(env);
                                QuestService.addNewSpawn(400010000, player.getInstanceId(), 214102, 2344.32f, 1789.96f, 2258.88f, (byte) 86);
                                QuestService.addNewSpawn(400010000, player.getInstanceId(), 214102, 2344.51f, 1786.01f, 2258.88f, (byte) 52);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                                return true;
                            }
                        }
                        case SETPRO4: {
                            if (var == 3) {
                                qs.setQuestVarById(0, 12);
                                qs.setStatus(QuestStatus.REWARD);
                                updateQuestStatus(env);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                                PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.START_FLYTELEPORT, 58001, 0), true);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (env.getTargetId() == 214102) {
                if (var == 3) {
                    killed = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env, 1072);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        int[] quests = {1701, 1072};
        return defaultOnLvlUpEvent(env, quests, true);
    }
}
