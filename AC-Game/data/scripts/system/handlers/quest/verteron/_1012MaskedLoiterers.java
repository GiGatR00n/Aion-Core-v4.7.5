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

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * Talk with Spiros (203111). Scout around Verteron Citadel (210030000) for
 * suspicious strangers. Scouting completed! Report back to Spiros. Collect the
 * Revolutionary Symbol (182200010) (5) and take them to Spiros.
 *
 * @author MrPoke, Dune11
 * @reworked vlog
 */
public class _1012MaskedLoiterers extends QuestHandler {

	private final static int questId = 1012;

	public _1012MaskedLoiterers() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(203111).addOnTalkEvent(questId);
		qe.registerOnEnterZone(ZoneName.get("LF1A_SENSORYAREA_Q1012_1_206004_8_210030000"), questId);
		qe.registerOnEnterZone(ZoneName.get("LF1A_SENSORYAREA_Q1012_2_206005_4_210030000"), questId);
		qe.registerOnEnterZone(ZoneName.get("LF1A_SENSORYAREA_Q1012_3_206006_6_210030000"), questId);
		qe.registerOnEnterZoneMissionEnd(questId);
		qe.registerOnLevelUp(questId);
	}

	@Override
	public boolean onZoneMissionEndEvent(QuestEnv env) {
		return defaultOnZoneMissionEndEvent(env);
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 1130, true);
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

		if (qs.getStatus() == QuestStatus.START) {
			if (targetId == 203111) // Spiros
			{
				switch (env.getDialog()) {
					case QUEST_SELECT:
						if (var == 0) {
							return sendQuestDialog(env, 1011);
						}
						if (var == 2) {
							return sendQuestDialog(env, 1352);
						}
						if (var == 3) {
							return sendQuestDialog(env, 1693);
						}
					case SETPRO1:
						return defaultCloseDialog(env, 0, 1); // 1
					case SETPRO2:
						return defaultCloseDialog(env, 2, 3); // 3
					case CHECK_USER_HAS_QUEST_ITEM:
						return checkQuestItems(env, 3, 3, true, 5, 2034);
					default:
						break;
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 203111) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}

	@Override
	public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
		final Player player = env.getPlayer();
			if (player == null) {
				return false;
			}
			final QuestState qs = player.getQuestStateList().getQuestState(questId);
			if (qs == null) {
				return false;
			}

			if (qs.getQuestVars().getQuestVars() == 1) {
				qs.setQuestVarById(0, 2); // 2
				updateQuestStatus(env);
				return true;
			}
		return false;
	}
}
