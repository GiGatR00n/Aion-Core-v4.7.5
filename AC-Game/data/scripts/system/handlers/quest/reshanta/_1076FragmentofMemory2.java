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
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author Rhys2002
 * @modified apozema
 */
public class _1076FragmentofMemory2 extends QuestHandler {

	private final static int questId = 1076;
	private final static int[] npc_ids = { 278500, 203834, 203786, 203754, 203704 };

	public _1076FragmentofMemory2() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnLevelUp(questId);
		qe.registerQuestItem(182202006, questId);
		qe.registerOnMovieEndQuest(170, questId);
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
		return defaultOnLvlUpEvent(env, 1701, true);
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

		if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203704) { // Boreas
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
		if (targetId == 278500) { // Yuditio
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 0) {
						return sendQuestDialog(env, 1011);
					}
				case SETPRO1:
					if (var == 0) {
						defaultCloseDialog(env, 0, 1);
						return TeleportService2.teleportTo(env.getPlayer(), 110010000, 2013.6453f, 1493.0521f, 581.1387f, (byte) 71, TeleportAnimation.BEAM_ANIMATION);
					}
				default:
					break;
			}
		} else if (targetId == 203834) { // Nestor
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 1) {
						return sendQuestDialog(env, 1352);
					} else if (var == 3) {
						return sendQuestDialog(env, 2034);
					} else if (var == 5) {
						return sendQuestDialog(env, 2716);
					}
				case SELECT_ACTION_1353:
					playQuestMovie(env, 102);
					break;
				case SETPRO2:
					if (var == 1) {
						return defaultCloseDialog(env, 1, 2);
					}
				case SETPRO4:
					if (var == 3) {
						changeQuestStep(env, 3, 4, false); //4
						WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(310070000);
						InstanceService.registerPlayerWithInstance(newInstance, player);
						TeleportService2.teleportTo(player, 310070000, newInstance.getInstanceId(), 180, 253, 1374);
						playQuestMovie(env, 170);						
						return closeDialogWindow(env);
					}
				case SETPRO6:
					if (var == 5) {
						removeQuestItem(env, 182202006, 1);
						return defaultCloseDialog(env, 5, 6);
					}
				default:
					break;
			}
		} else if (targetId == 203786) { // Diana
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 2) {
						return sendQuestDialog(env, 1693);
					}
				case CHECK_USER_HAS_QUEST_ITEM:
					return checkQuestItems(env, 2, 3, false, 10000, 10001, 182202006, 1);
				default:
					break;
			}
		} else if (targetId == 203754) { // Aithra
			switch (env.getDialog()) {
				case QUEST_SELECT:
					if (var == 6) {
						return sendQuestDialog(env, 3057);
					}
				case SET_SUCCEED:
					if (var == 6) {
						return defaultCloseDialog(env, 6, 6, true, false);
					}
				default:
					break;
			}
		}
		return false;
	}
	
	@Override
	public boolean onMovieEndEvent(QuestEnv env, int movieId) {
		Player player = env.getPlayer();
		if (movieId == 170) {
			int var = env.getPlayer().getQuestStateList().getQuestState(questId).getQuestVarById(0);
			if (var == 4) {
				changeQuestStep(env, 4, 5, false); // 5
				TeleportService2.teleportTo(player, 110010000, 2004.2399f, 1489.9116f, 581.1387f, (byte) 97, TeleportAnimation.BEAM_ANIMATION);
				return true;
			}
		}
		return false;
	}

}
