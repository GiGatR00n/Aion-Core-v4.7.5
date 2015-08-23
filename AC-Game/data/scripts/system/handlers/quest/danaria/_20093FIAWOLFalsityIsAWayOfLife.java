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
package quest.danaria;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.SystemMessageId;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author pralinka
 */
public class _20093FIAWOLFalsityIsAWayOfLife extends QuestHandler {

	private final static int questId = 20093;

	public _20093FIAWOLFalsityIsAWayOfLife() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnLevelUp(questId);
		qe.registerQuestNpc(800835).addOnTalkEvent(questId); // lucullus
		qe.registerQuestNpc(800839).addOnTalkEvent(questId); // runa
		qe.registerQuestNpc(800846).addOnTalkEvent(questId); // lucullus
		qe.registerQuestNpc(701558).addOnTalkEvent(questId); // Danuar Idgel
																// Cube
		qe.registerQuestNpc(800847).addOnTalkEvent(questId); // lucullus
		qe.registerQuestNpc(800848).addOnTalkEvent(questId); // skuldun
		qe.registerQuestNpc(800529).addOnTalkEvent(questId); // vard
		qe.registerQuestNpc(801328).addOnTalkEvent(questId); // marchutan
		qe.registerQuestNpc(230396).addOnKillEvent(questId); // Hyperion Defense
																// Officer
		qe.registerQuestNpc(230397).addOnKillEvent(questId); // Hyperion Rescue
																// Specialists
		qe.registerQuestNpc(230402).addOnKillEvent(questId); // Hiding Spirits
		qe.registerOnLogOut(questId);
		qe.registerOnDie(questId);
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 20092);
	}

	@Override
	public boolean onDieEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var > 0 && var < 13) {
				changeQuestStep(env, var, 0, false);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onLogOutEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			if (var > 0 && var < 13) {
				changeQuestStep(env, var, 0, false);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null) {
			return false;
		}
		DialogAction dialog = env.getDialog();
		int targetId = env.getTargetId();
		if (qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVarById(0);
			switch (targetId) {
				case 800835: { // lucullus
					switch (dialog) {
						case QUEST_SELECT: {
							if (qs.getQuestVarById(0) == 0) {
								return sendQuestDialog(env, 1011);
							}
						}
						case SETPRO1: {
							WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300900000);
							InstanceService.registerPlayerWithInstance(newInstance, player);
							TeleportService2.teleportTo(player, 300900000, newInstance.getInstanceId(), 153, 143, 125);
							QuestService.addNewSpawn(300900000, player.getInstanceId(), 800839, 146f, 144f, 125f, (byte) 119);
							return defaultCloseDialog(env, 0, 1);
						}
						default:
							break;
					}
					break;
				}
				case 800839: { // runa
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 4) {
								playQuestMovie(env, 856);
								QuestService.addNewSpawn(300900000, player.getInstanceId(), 800846, 138f, 157f, 121f, (byte) 105);
								env.getVisibleObject().getController().delete();
								return defaultCloseDialog(env, 4, 5);
							}
						}
						default:
							break;
					}
					break;
				}
				case 800846: { // lucullus
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 5) {
								return sendQuestDialog(env, 2034);
							}
						}
						case SETPRO4: {
							env.getVisibleObject().getController().delete();
							return defaultCloseDialog(env, 5, 6);
						}
						default:
							break;
					}
					break;
				}
				case 701558: { // Danuar Idgel Cube
					switch (dialog) {
						case USE_OBJECT: {
							if (var == 6) {
								return sendQuestDialog(env, 2375);
							} else if (var == 11) {
								return sendQuestDialog(env, 3398);
							}
						}
						case SETPRO5: {
							QuestService.addNewSpawn(300900000, player.getInstanceId(), 230402, 116f, 137f, 113f, (byte) 119);
							QuestService.addNewSpawn(300900000, player.getInstanceId(), 230402, 117f, 145f, 113f, (byte) 119);
							QuestService.addNewSpawn(300900000, player.getInstanceId(), 230402, 119f, 139f, 112f, (byte) 49);
							QuestService.addNewSpawn(300900000, player.getInstanceId(), 800847, 104f, 139f, 112f, (byte) 119);
							return defaultCloseDialog(env, 6, 7);
						}
						case SETPRO8: {
							playQuestMovie(env, 858);
							QuestService.addNewSpawn(300900000, player.getInstanceId(), 800848, 105f, 143f, 125f, (byte) 119);
							TeleportService2.teleportTo(player, 300900000, 109, 141, 125);
							return defaultCloseDialog(env, 11, 12);
						}
						default:
							break;
					}
					break;
				}
				case 800847: { // lucullus
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 10) {
								return sendQuestDialog(env, 3057);
							}
						}
						case SETPRO7: {
							env.getVisibleObject().getController().delete();
							return defaultCloseDialog(env, 10, 11);
						}
						default:
							break;
					}
					break;
				}
				case 800848: { // skuldun
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 12) {
								return sendQuestDialog(env, 3739);
							}
						}
						case SETPRO9: {
							TeleportService2.teleportTo(player, 600050000, 416f, 424f, 289f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
							env.getVisibleObject().getController().delete();
							return defaultCloseDialog(env, 12, 13);
						}
						default:
							break;
					}
					break;
				}
				case 800529: { // vard
					switch (dialog) {
						case QUEST_SELECT: {
							if (var == 13) {
								return sendQuestDialog(env, 4080);
							}
						}
						case SETPRO10: {
							return defaultCloseDialog(env, 13, 13, true, false);
						}
						default:
							break;
					}
					break;
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 801328) { // marchutan
				if (env.getDialog() == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 10002);
				} else {
					return sendQuestEndDialog(env);
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

		int targetId = env.getTargetId();
		int questVar = qs.getQuestVarById(0);
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}
		Npc npc = (Npc) env.getVisibleObject();
		if (targetId == 230396 || targetId == 230397) {
			if (questVar >= 1 && questVar < 4) {
				qs.setQuestVarById(0, questVar + 1);
				updateQuestStatus(env);
				return true;
			} else if (questVar == 4) {
				changeQuestStep(env, 4, 5, false);
			}
		} else if (targetId == 230402) {
			if (questVar >= 7 && questVar < 10) {
				qs.setQuestVarById(0, questVar + 1);
				updateQuestStatus(env);
				return true;
			} else if (questVar == 10) {
				changeQuestStep(env, 10, 11, false);
			}
		}
		return false;
	}
}
