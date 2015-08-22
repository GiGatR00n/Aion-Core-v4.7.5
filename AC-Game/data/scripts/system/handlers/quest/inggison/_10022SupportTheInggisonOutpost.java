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

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Nephis
 * @reworked Gigi
 * @modified apozema
 */
public class _10022SupportTheInggisonOutpost extends QuestHandler {

	private final static int questId = 10022;
	private final static int[] npc_ids = { 798932, 798996, 203786, 204656, 798176, 798926, 700601 };

	public _10022SupportTheInggisonOutpost() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnLevelUp(questId);
		qe.registerQuestNpc(215622).addOnKillEvent(questId);
		qe.registerQuestNpc(216784).addOnKillEvent(questId);
		qe.registerQuestNpc(215633).addOnKillEvent(questId);
		qe.registerQuestNpc(216731).addOnKillEvent(questId);
		qe.registerQuestNpc(215634).addOnKillEvent(questId);
		for (int npc_id : npc_ids) {
			qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
		}
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 10020, true);
	}

	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env) {
		return defaultOnZoneMissionEndEvent(env);
	}

	@Override
	public boolean onDialogEvent(final QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null) {
			return false;
		}

		int var = qs.getQuestVarById(0);
		int var3 = qs.getQuestVarById(3);
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}

		if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 798926) { // Outremus
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
		if (targetId == 798932) { // Secundila
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 0) {
						return sendQuestDialog(env, 1011);
					} else if (var == 11) {
						return sendQuestDialog(env, 1608);
					}
				case SETPRO1:
					return defaultCloseDialog(env, 0, 1);
				case SET_SUCCEED:
					return defaultCloseDialog(env, 11, 11, true, false);
				default:
					break;
			}
		} else if (targetId == 798996) { // Iaetia
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 1) {
						return sendQuestDialog(env, 1352);
					} else if (qs.getQuestVarById(1) == 10 || qs.getQuestVarById(2) == 10 || qs.getQuestVarById(0) == 3) {
						playQuestMovie(env, 503);
						return sendQuestDialog(env, 2034);
					} else if (var == 10) {
						return sendQuestDialog(env, 4080);
					}
				case SETPRO2:
					return defaultCloseDialog(env, 1, 2);
				case SETPRO4:
					if (qs.getQuestVarById(1) == 10 || qs.getQuestVarById(2) == 10 || qs.getQuestVarById(0) == 3) {
						qs.setQuestVar(4);
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						return true;
					}
					break;
				case SETPRO10:
					if (var == 10) {
						return defaultCloseDialog(env, 10, 11);
					}
				default:
					break;
			}
		} else if (targetId == 203786) { // Diana
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 4) {
						return sendQuestDialog(env, 2375);
					} else if (var == 7) {
						return sendQuestDialog(env, 3398);
					} else if (var == 8) {
						return sendQuestDialog(env, 3739);
					}
				case CHECK_USER_HAS_QUEST_ITEM:
					if (QuestService.collectItemCheck(env, true)) {
						qs.setQuestVarById(0, var + 1);
						updateQuestStatus(env);
						PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
						return true;
					} else {
						return sendQuestDialog(env, 10001);
					}
				case SETPRO8:
					if (var == 7) {
						return defaultCloseDialog(env, 7, 8);
					}
					break;
				case SETPRO9:
					if (var == 8) {
						TeleportService2.teleportTo(player, 210050000, 1079.2302f, 1493.7306f, 404.86075f, (byte) 89, TeleportAnimation.BEAM_ANIMATION);
						return defaultCloseDialog(env, 8, 9);
					}
				default:
					break;
			}
		} else if (targetId == 204656) { // Maloren
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 5) {
						return sendQuestDialog(env, 2716);
					}
				case SETPRO6:
					if (var == 5) {
						TeleportService2.teleportTo(player, 210060000, 397.94107f, 1218.6017f, 134.589f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
						return defaultCloseDialog(env, 5, 6);
					}
				default:
					break;
			}
		} else if (targetId == 798176) { // Jamanok
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 6) {
						return sendQuestDialog(env, 3057);
					}
				case SETPRO7:
					if (var == 6) {
						TeleportService2.teleportTo(player, 110010000,1846.21f, 1448.8267f, 590.12213f, (byte) 29, TeleportAnimation.BEAM_ANIMATION);
						return defaultCloseDialog(env, 6, 7);
					}
				default:
					break;
			}
		} else if (targetId == 700601) { // Inggison Drana
			if (var == 9 && env.getDialog() == DialogAction.USE_OBJECT) {
				if (var3 < 4) {
					return useQuestObject(env, var3, var3 + 1, false, 3, true);
				} else if (var3 == 4) {
					qs.setQuestVar(10);
					updateQuestStatus(env);
				}
			}
		}
		return false;
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() != QuestStatus.START) {
			return false;
		}

		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}

		switch (targetId) {
			case 215622: // Basrasa Worker
			case 216784: // Basrasa Worker
				if (qs.getQuestVarById(1) < 10 && qs.getQuestVarById(0) == 2) {
					qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
					updateQuestStatus(env);
					return true;
				} else if (qs.getQuestVarById(1) == 9 && qs.getQuestVarById(2) == 10 && qs.getQuestVarById(0) == 2) {
					qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
					updateQuestStatus(env);
					return true;
				}
				break;
			case 215633: // Basrasa Vaegir
			case 216731: // Basrasa Vaegir
			case 215634: // Basrasa Sentry
				if (qs.getQuestVarById(2) < 10 && qs.getQuestVarById(0) == 2) {
					qs.setQuestVarById(2, qs.getQuestVarById(2) + 1);
					updateQuestStatus(env);
					return true;
				} else if (qs.getQuestVarById(1) == 10 && qs.getQuestVarById(2) == 9 && qs.getQuestVarById(0) == 2) {
					qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
					updateQuestStatus(env);
					return true;
				}
		}
		return false;
	}
}
