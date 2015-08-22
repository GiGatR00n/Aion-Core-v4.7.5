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

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * @author Dune11
 * @reworked vlog
 * @modified apozema
 */
public class _1092JosnacksDilemma extends QuestHandler {

	private final static int questId = 1092;
	private final static int[] npc_ids = { 798155, 798206, 700389, 700388 };

	public _1092JosnacksDilemma() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnLevelUp(questId);
		qe.registerQuestNpc(700390).addOnKillEvent(questId);
		qe.addHandlerSideQuestDrop(questId, 214552, 182208033, 1, 100);
		qe.registerOnDie(questId);
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
				return sendQuestEndDialog(env);
			}
			return false;
		} else if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 798155: // Atropos
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (var == 0) {
								return sendQuestDialog(env, 1011);
							}
							if (var == 3) {
								return sendQuestDialog(env, 2034);
							}
							if (var == 4) {
								return sendQuestDialog(env, 2375);
							}
							return true;
						case SETPRO1:
							return defaultCloseDialog(env, 0, 1);
						case SETPRO4:
							return defaultCloseDialog(env, 3, 4);
						case CHECK_USER_HAS_QUEST_ITEM:
							return checkQuestItems(env, 4, 4, true, 10001, 10008);
						default:
							break;
					}
					break;
				case 798206: // Josnack
					switch (env.getDialog()) {
						case QUEST_SELECT:
							if (var == 1) {
								return sendQuestDialog(env, 1352);
							}
							if (var == 2) {
								return sendQuestDialog(env, 1693);
							}
						case SELECT_ACTION_1353:
							playQuestMovie(env, 364);
							break;
						case SETPRO2:
							if (var == 1) {
								defaultCloseDialog(env, 1, 2);
								TeleportService2.teleportTo(player, 210060000, 981.6369f, 3020.979f, 200.04276f, (byte) 119, TeleportAnimation.BEAM_ANIMATION);
								return true;
							}
						default:
							break;
					}
					break;
				case 700389: // Stone above the Statue
					switch (env.getDialog()) {
						case USE_OBJECT:
							if (var == 2 && qs.getQuestVarById(1) == 0) {
								qs.setQuestVarById(1, 1);
								updateQuestStatus(env);

								if (qs.getQuestVarById(2) == 1) {
									qs.setQuestVar(3);
									updateQuestStatus(env);
								}
								return true;
							}
						default:
							break;
					}
					break;
				case 700388: // Stone on the Statue Platform
					switch (env.getDialog()) {
						case USE_OBJECT:
							if (var == 2 && qs.getQuestVarById(2) == 0) {
								qs.setQuestVarById(2, 1);
								if (qs.getQuestVarById(1) == 1) {
									qs.setQuestVar(3);
									updateQuestStatus(env);
									return true;
								}
								updateQuestStatus(env);
								return true;
							}
						default:
							break;
					}
					break;
			}
		}
		return false;
	}

	@Override
	public boolean onDieEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null) {
			return false;
		}
		int var = qs.getQuestVarById(0);

		if (qs.getStatus() == QuestStatus.START) {
			if (var == 2) {
				qs.setQuestVarById(0, 1);
				qs.setQuestVarById(1, 0);
				updateQuestStatus(env);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		VisibleObject target = env.getVisibleObject();
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVars().getQuestVars();
			Storage bag = player.getInventory();
			if (target instanceof Npc) {
				Npc npc = (Npc) target;
				if (npc.getNpcId() == 700390 && var == 4 && (bag.getItemCountByItemId(182208012) < 6)) {
					QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 214552, (float) 239.66934, (float) 2734.8235, (float) 76.56028, (byte) 81);
					QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 214552, (float) 239.33173, (float) 2739.9043, (float) 77.56217, (byte) 30);
					return true;
				}
			}
		}
		return false;
	}
}
