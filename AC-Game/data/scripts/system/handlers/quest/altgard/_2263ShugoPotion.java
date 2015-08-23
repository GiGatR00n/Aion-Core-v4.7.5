/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 * Aion-Lightning is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 2 of the License, or (at your option) any
 * later version.
 *
 * Aion-Lightning is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. *
 *
 * You should have received a copy of the GNU General Public License along with
 * Aion-Lightning. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below Please do
 * not change here something, ragarding the developer credits, except the
 * "developed by XXXX". Even if you edit a lot of files in this source, you
 * still have no rights to call it as "your Core". Everybody knows that this
 * Emulator Core was developed by Aion Lightning
 *
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/**
 * @Author Majka Ajural
 *
 */
public class _2263ShugoPotion extends QuestHandler {

	private final static int questId = 2263; // Altgard - Shugo Potion
	private final static int questDropItemId = 182203242; // Malodor Pollen
	private final static int questStartNpcId = 798036; // Mabrunerk

	public _2263ShugoPotion() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(questStartNpcId).addOnQuestStart(questId); // Mabrunerk
		qe.registerQuestNpc(questStartNpcId).addOnTalkEvent(questId); // Mabrunerk
		qe.registerOnQuestTimerEnd(questId);
		qe.registerOnLogOut(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		int targetId = 0;
		if (env.getVisibleObject() instanceof Npc) {
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		}
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();

		if (qs == null || qs.getStatus() == QuestStatus.NONE) {
			if (targetId == questStartNpcId) { // Mabrunerk
				switch (dialog) {
					case QUEST_ACCEPT_1:
						if (QuestService.startQuest(env)) {
							QuestService.questTimerStart(env, 300);
							return sendQuestDialog(env, 1003);
						}
						break;
					case QUEST_SELECT:
						return sendQuestDialog(env, 1011);
					default:
						return sendQuestStartDialog(env);
				}
			}
		} else if (qs.getStatus() == QuestStatus.START) {

			if (targetId == questStartNpcId) { // Mabrunerk
				switch (dialog) {
					case QUEST_SELECT:
						return sendQuestDialog(env, 2375);

					case CHECK_USER_HAS_QUEST_ITEM:
						// Checks if player has 3 Malodor Pollens [ID: 182203242]
						if (QuestService.collectItemCheck(env, true)) { 
							qs.setStatus(QuestStatus.REWARD);
							updateQuestStatus(env);
							QuestService.questTimerEnd(env);
							return sendQuestDialog(env, 5);
						} else {
							return sendQuestDialog(env, 2716);
						}
				}
			}
		} else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == questStartNpcId) { // Mabrunerk
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}

	// On time end if not in reward status delete quest items and quest itself
	@Override
	public boolean onQuestTimerEndEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			long malodorPollen = player.getInventory().getItemCountByItemId(questDropItemId);
			removeQuestItem(env, questDropItemId, malodorPollen);
			QuestService.abandonQuest(player, questId);
			return true;
		}
		return false;
	}

	// On logout if not in reward status delete quest items and quest itself
	@Override
	public boolean onLogOutEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			long malodorPollen = player.getInventory().getItemCountByItemId(questDropItemId);
			removeQuestItem(env, questDropItemId, malodorPollen);
			QuestService.abandonQuest(player, questId);
			return true;
		}
		return false;
	}
}
