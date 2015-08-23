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
package quest.eltnen;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Xitanium
 * @author Ritsu
 * @author Antraxx
 */
public class _1032ARulersDuty extends QuestHandler {

    private final static int questId = 1032;

    public _1032ARulersDuty() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestItem(182201001, questId);
        qe.registerQuestNpc(203932).addOnTalkEvent(questId); // Phomona
        qe.registerQuestNpc(730020).addOnTalkEvent(questId); // Demro
        qe.registerQuestNpc(730019).addOnTalkEvent(questId); // Lodas
        qe.registerQuestNpc(700157).addOnTalkEvent(questId); // Seau kerubien
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
    }

	@Override
	public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
		final Player player = env.getPlayer();
		final int id = item.getItemTemplate().getTemplateId();
		final int itemObjId = item.getObjectId();

		if (id != 182201001) {
			return HandlerResult.UNKNOWN;
		}
		if (!player.isInsideZone(ZoneName.get("LF2_ITEMUSEAREA_Q1032"))) {
			return HandlerResult.UNKNOWN;
		}

		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
			PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
				qs.setQuestVar(4);
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
        return defaultOnLvlUpEvent(env, 1300, true);
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

		if (targetId == 203932) // Phomona
		{
			if (qs.getStatus() == QuestStatus.START) {
				if (env.getDialog() == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 1011);
                } else if (env.getDialog() == DialogAction.SELECT_ACTION_1013
                        && qs.getStatus() == QuestStatus.START
                        && qs.getQuestVarById(0) == 0) {
					PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(0, questId, 177, 0, player.getTarget().getObjectId()));
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(player.getTarget().getObjectId(), 1013, questId));
					return true;
				} else if (env.getDialog() == DialogAction.SETPRO1) {
					qs.setQuestVar(1);
					updateQuestStatus(env);
					PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
					return true;
				}
                return sendQuestStartDialog(env);
            } else if (qs.getStatus() == QuestStatus.REWARD) {
                if (env.getDialog() == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 2716);
                }
                return sendQuestEndDialog(env);
            }
        } else if (targetId == 730020) // Demro
        {
            if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1352);
                } else if (env.getDialog() == DialogAction.SETPRO2) {
                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                }
                return sendQuestStartDialog(env);
            } else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 5) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 2375);
                } else if (env.getDialogId() == DialogAction.SETPRO5.id()) {
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                }
                return sendQuestStartDialog(env);
            }
        } else if (targetId == 730019) // Lodas
        {
            if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1693);
                } else if (env.getDialog() == DialogAction.SETPRO3) {
                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                }
                return sendQuestStartDialog(env);
            } else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 4) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 2034);
                } else if (env.getDialog() == DialogAction.SETPRO4) {
                    removeQuestItem(env, 182201001, 1);
	            playQuestMovie(env, 49);
                    qs.setQuestVar(5);
                    updateQuestStatus(env);
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                    return true;
                }
                return sendQuestStartDialog(env);
            }
        } else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {
            switch (targetId) {
                case 700157: { // Seau kerubien
                    if (qs.getQuestVarById(0) == 3 && env.getDialog() == DialogAction.USE_OBJECT) {
                        return true; // loot
                    }
                }
            }
        }
        return false;
    }
}
