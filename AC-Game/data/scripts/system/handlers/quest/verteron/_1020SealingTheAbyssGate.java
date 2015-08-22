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
package quest.verteron;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * Talk with Spatalos (203098).<br>
 * Enter the Sealed Space via the Abyss Gate in Kraka's Den.<br>
 * Obtain the Seal of Kuninasha (182200024) and destroy the Abyss
 * Gate(700141).<br>
 * Report to Spatalos.
 *
 * @author Atomics
 * @reworked vlog, apozema
 */
public class _1020SealingTheAbyssGate extends QuestHandler {

	private final static int questId = 1020;
	private final static int[] npcs = { 203098, 700142, 700143 };

	public _1020SealingTheAbyssGate() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnLevelUp(questId);
		qe.registerOnDie(questId);
		qe.registerOnEnterWorld(questId);
		for (int npc : npcs) {
			qe.registerQuestNpc(npc).addOnTalkEvent(questId);
		}
		qe.registerQuestNpc(210753).addOnKillEvent(questId);
		qe.registerOnMovieEndQuest(153, questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		Player player = env.getPlayer();
		int targetId = env.getTargetId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null) {
			return false;
		}
		int var = qs.getQuestVarById(0);

		if (qs.getStatus() == QuestStatus.START) {
			switch (targetId) {
				case 203098: { // Spatalos
					if (env.getDialog() == DialogAction.QUEST_SELECT && var == 0) {
						return sendQuestDialog(env, 1011);
					} else if (env.getDialog() == DialogAction.SETPRO1) {
						TeleportService2.teleportTo(player, 210030000, 2683.2085f, 1068.8977f, 199.375f, (byte) 119, TeleportAnimation.BEAM_ANIMATION);
						changeQuestStep(env, 0, 1, false);
						return closeDialogWindow(env);
					} else if (env.getDialogId() == DialogAction.SELECT_ACTION_1013.id()) {						
						playQuestMovie(env, 29);
						return sendQuestDialog(env, 1013);
					}
					break;
				}
				case 700142: {  // Abyss Gate Guardian Stone // TODO: add a check if Kuninasha is spawned already, to avoid multiple spawns.
					if (var == 2) {
						if (env.getDialog() == DialogAction.USE_OBJECT) {
							QuestService.addNewSpawn(310030000, player.getInstanceId(), 210753, (float) 258.89917, (float) 237.20166, (float) 217.06035, (byte) 0);
							return useQuestObject(env, 2, 2, false, false);
							}
						}
				}
				case 700143:  // Abyss Gate
					long Seal = player.getInventory().getItemCountByItemId(182200024);
					if (Seal == 1) {
						destroy(-2, env);
						return false;
					}
					break;
				}

		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203098) { // Spatalos
				if (env.getDialog() == DialogAction.QUEST_SELECT) {
					return sendQuestDialog(env, 1352);
				} else {
					removeQuestItem(env, 182200024, 1);
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}

	@Override
	public boolean onEnterWorldEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null) {
			return false;
		}
		if (qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVars().getQuestVars();
			if (var == 1 && player.getWorldId() == 310030000) {
				changeQuestStep(env, 1, 2, false);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		return defaultOnKillEvent(env, 210753, 2, 2);
	}

	@Override
	public boolean onDieEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			int var = qs.getQuestVars().getQuestVars();
			if (var == 2) {
				changeQuestStep(env, 2, 1, false);
				removeQuestItem(env, 182200024, 1);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onMovieEndEvent(QuestEnv env, int movieId) {
		if (movieId == 153) {
			TeleportService2.teleportTo(env.getPlayer(), 210030000, 1724.0143f, 1493.7954f, 121.88304f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
			return true;
		}
		return false;
	}

	@Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        int[] verteronQuests = {1130, 1011, 1012, 1013, 1014, 1015, 1021, 1016, 1018, 1017, 1019, 1022, 1023};
        return defaultOnZoneMissionEndEvent(env, verteronQuests);
    }
	
	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		int[] verteronQuests = {1130, 1011, 1012, 1013, 1014, 1015, 1021, 1016, 1018, 1017, 1019, 1022, 1023};
		return defaultOnLvlUpEvent(env, verteronQuests, true);
	}

	private void destroy(final int var, final QuestEnv env) { 
		final int targetObjectId = env.getVisibleObject().getObjectId();

		final Player player = env.getPlayer();
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				if (player.getTarget().getObjectId() != targetObjectId) {
					return;
				}
				QuestState qs = player.getQuestStateList().getQuestState(questId);
				switch (var) {
					case -2:
						changeQuestStep(env, 2, 3, true);
						playQuestMovie(env, 153);
						break;
				}
			}
		}, 3000);
	}
}
