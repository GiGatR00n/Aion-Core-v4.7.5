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

import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author pralinka
 */
public class _14060TheLandOfIde extends QuestHandler {

    private final static int questId = 14060;

    public _14060TheLandOfIde() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(203700).addOnTalkEvent(questId); // fasimedes
        qe.registerQuestNpc(798600).addOnTalkEvent(questId); // eremitia
        qe.registerQuestNpc(798408).addOnTalkEvent(questId); // sibylle
        qe.registerQuestNpc(798926).addOnTalkEvent(questId); // outremus
        qe.registerQuestNpc(799053).addOnTalkEvent(questId); // nydrea
        qe.registerQuestNpc(798927).addOnTalkEvent(questId); // versetti
        qe.registerOnEnterWorld(questId);
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
    }

    @Override
    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVars().getQuestVars();
            if (var == 7) {
                if (player.getWorldId() == 210050000) {
                    return playQuestMovie(env, 501);
                }
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
        Player player = env.getPlayer();
        PlayerClass playerClass = player.getCommonData().getPlayerClass();
            if (playerClass != PlayerClass.RIDER) {
            return false;
            }
        return defaultOnLvlUpEvent(env);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 203700 && var == 0) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else if (env.getDialog() == DialogAction.SETPRO1) {
                    qs.setQuestVar(++var);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                } else {
                    return sendQuestStartDialog(env);
                }
            } else if (targetId == 798600 && var == 1) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1352);
                } else if (env.getDialog() == DialogAction.SETPRO2) {
                    qs.setQuestVar(++var);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                } else {
                    return sendQuestStartDialog(env);
                }
            } else if (targetId == 798408 && var == 2) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1693);
                } else if (env.getDialog() == DialogAction.SETPRO3) {
                    qs.setQuestVar(++var);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    playQuestMovie(env, 501);
                    TeleportService2.teleportTo(player, 210050000, 1, 1318, 253, 592, (byte) 77, TeleportAnimation.BEAM_ANIMATION);
                    return true;
                } else {
                    return sendQuestStartDialog(env);
                }
            } else if (targetId == 798926 && var == 3) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 2034);
                } else if (env.getDialog() == DialogAction.SETPRO4) {
                    qs.setQuestVar(++var);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                } else {
                    return sendQuestStartDialog(env);
                }
            } else if (targetId == 799053 && var == 4) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 2375);
                } else if (env.getDialogId() == 10004) {
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                } else {
                    return sendQuestStartDialog(env);
                }
            }

        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 798927) {
                if (env.getDialogId() == -3) {
                    return sendQuestDialog(env, 2716);
                }
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }
}
