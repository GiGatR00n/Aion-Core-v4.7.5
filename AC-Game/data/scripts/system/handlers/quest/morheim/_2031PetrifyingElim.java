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

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Mcrizza
 */
public class _2031PetrifyingElim extends QuestHandler {

    private final static int questId = 2031;

    public _2031PetrifyingElim() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestItem(182204001, questId);
        qe.registerQuestNpc(204304).addOnTalkEvent(questId); // Vili
        qe.registerQuestNpc(730038).addOnTalkEvent(questId); // Nabaru
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
    }

    @Override
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        final int id = item.getItemTemplate().getTemplateId();
        final int itemObjId = item.getObjectId();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (id != 182204001 || qs.getStatus() == QuestStatus.COMPLETE) {
            return HandlerResult.UNKNOWN;
        }

        if (!player.isInsideZone(ZoneName.get("DF2_ITEMUSEAREA_Q2031"))) {
            return HandlerResult.UNKNOWN;
        }

        PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0,
                0), true);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0,
                        1, 0), true);
                playQuestMovie(env, 72);
                removeQuestItem(env, 182204001, 1);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(env);
            }
        }, 3000);

        return HandlerResult.SUCCESS;
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 2300, true);
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

        if (targetId == 204304) // Vili
        {
            if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else if (env.getDialog() == DialogAction.SETPRO1) {
                    qs.setQuestVar(1);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        } else if (targetId == 730038) // Nabaru
        {
            if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1352);
                } else if (env.getDialogId() == DialogAction.SELECT_ACTION_1353.id()) {
                    playQuestMovie(env, 71);
                } else if (env.getDialog() == DialogAction.SETPRO2) {
                    qs.setQuestVar(2);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                } else {
                    return sendQuestStartDialog(env);
                }
            }

            if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1693);
                } else if (env.getDialogId() == DialogAction.CHECK_USER_HAS_QUEST_ITEM.id()) {
                    if (player.getInventory().getItemCountByItemId(182204001) > 0) {
                        qs.setQuestVar(3);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                    } else {
                        return sendQuestDialog(env, 10001);
                    }
                } else {
                    return sendQuestStartDialog(env);
                }
            }

            if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 2034);
                } else if (env.getDialog() == DialogAction.SETPRO4) {
                    qs.setQuestVar(4);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                } else {
                    return sendQuestStartDialog(env);
                }
            } else if (qs.getStatus() == QuestStatus.REWARD) {
                return sendQuestEndDialog(env);
            }
        }

        return false;

    }
}
