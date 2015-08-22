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
package quest.theobomos;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Hellboy Aion4Free
 * @modified apozema
 */
public class _1093TheCalydonRuins extends QuestHandler {

	private final static int questId = 1093;
	private final static int[] npc_ids = { 798155, 203784, 798176, 700391, 700392, 700393, 798212 };

	public _1093TheCalydonRuins() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnLevelUp(questId);
		qe.registerQuestItem(182208013, questId);
		for (int npc_id : npc_ids) {
			qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
		}
	}

	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env) {
		return defaultOnZoneMissionEndEvent(env);
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 1091, true);
	}

	@Override
	public boolean onDialogEvent(final QuestEnv env) {
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

		if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 798155) { // Atropos
				if (env.getDialog() == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 10002);
				} else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id()) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
			return false;
		} else if (qs.getStatus() != QuestStatus.START) {
			return false;
		}
		if (targetId == 798155) { // Atropos
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 0) {
						return sendQuestDialog(env, 1011);
					}
				case SETPRO1:
					if (var == 0) {
						qs.setQuestVarById(0, var + 1);
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						TeleportService2.teleportTo(player, 110010000, 1850.9097f, 1540.9253f, 590.15826f, (byte) 43, TeleportAnimation.BEAM_ANIMATION);
						return true;
					}
				default:
					break;
			}
		} else if (targetId == 203784) { // Hestia
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 1) {
						return sendQuestDialog(env, 1352);
					}
				case SETPRO2:
					if (var == 1) {
						if (!giveQuestItem(env, 182208013, 1)) { // Calydon Candy
							return true;
						}
						qs.setQuestVarById(0, var + 1);
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						return true;
					}
				default:
					break;
			}
		} else if (targetId == 798176) { // Jamanok
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 2) {
						return sendQuestDialog(env, 1693);
					}
				case SELECT_ACTION_1694:
					playQuestMovie(env, 365);
					break;
				case SETPRO3:
					if (var == 2) {
						qs.setQuestVarById(0, var + 1);
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						return true;
					}
				default:
					break;
			}
		} else if (targetId == 798212) { // Serimnir
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 7) {
						return sendQuestDialog(env, 3398);
					}
					if (var == 8) {
						return sendQuestDialog(env, 3739);
					}
				case SETPRO4:
					if (var == 7) {
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						return true;
					}
				case SET_SUCCEED:
					if (var == 8) {
						qs.setStatus(QuestStatus.REWARD);
						removeQuestItem(env, 182208013, 1); // Calydon Candy
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						return true;
					}
				case CHECK_USER_HAS_QUEST_ITEM:
					if (var == 7) {
						if (QuestService.collectItemCheck(env, true)) {
							qs.setQuestVarById(0, var + 1);
							updateQuestStatus(env);
							return sendQuestDialog(env, 3739);
						} else {
							return sendQuestDialog(env, 10001);
						}
					}
				default:
					break;
			}
		} else if (targetId == 700391) { // First Calydon Stone Plate
			switch (env.getDialog()) {
				case USE_OBJECT:
					if (var == 4) {
						if (!giveQuestItem(env, 182208014, 1)) { // First Rubbed Copy
							return false;
						}
						qs.setQuestVarById(0, 5);
						updateQuestStatus(env);
					}
				default:
					break;
			}
		} else if (targetId == 700392) { // Second Calydon Stone Plate
			switch (env.getDialog()) {
				case USE_OBJECT:
					if (var == 5) {
						if (!giveQuestItem(env, 182208015, 1)) { // Second Rubbed Copy
							return false;
						}
						qs.setQuestVarById(0, 6);
						updateQuestStatus(env);
					}
				default:
					break;
			}
		} else if (targetId == 700393) { // Third Calydon Stone Plate
			switch (env.getDialog()) {
				case USE_OBJECT:
					if (var == 6) {
						if (!giveQuestItem(env, 182208016, 1)) { // Third Rubbed Copy
							return false;
						}
						qs.setQuestVarById(0, 7);
						updateQuestStatus(env);
					}
				default:
					break;
			}
		}
		return false;
	}

	@Override
	public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
		final Player player = env.getPlayer();
		final int id = item.getItemTemplate().getTemplateId();

		if (id != 182208013) { // Calydon Candy
			return HandlerResult.UNKNOWN;
		}

		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getQuestVarById(0) != 3) {
			return HandlerResult.UNKNOWN;
		}

		qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
		updateQuestStatus(env);
		return HandlerResult.SUCCESS;
	}
}
