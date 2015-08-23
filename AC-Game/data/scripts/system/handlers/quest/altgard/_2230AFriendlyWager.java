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

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.model.TaskId;

/**
 * @author HellBoy
 * @Reworked Majka Ajural
 */
public class _2230AFriendlyWager extends QuestHandler {

	private final static int questId = 2230;
	private final static int questDropItemId = 182203223; // Mosbear Tusks
	private final static int questStartNpcId = 203621; // Shania
	private final static int questDurationTime = 1800; // Duration time of the quest 1800

	public _2230AFriendlyWager() {
		super(questId);
	}

	@Override
	public void register() {
		qe.registerQuestNpc(questStartNpcId).addOnQuestStart(questId);
		qe.registerQuestNpc(questStartNpcId).addOnTalkEvent(questId);
		qe.registerOnQuestTimerEnd(questId);
		qe.registerOnLogOut(questId);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		int targetId = env.getTargetId();

		QuestState qs = player.getQuestStateList().getQuestState(questId);
		DialogAction dialog = env.getDialog();

		if (targetId == questStartNpcId) {
			if (qs == null || qs.getStatus() == QuestStatus.NONE) {
				
				switch (dialog) {
					case QUEST_ACCEPT_1:
						if (QuestService.startQuest(env)) {
							QuestService.questTimerStart(env, questDurationTime);
							return sendQuestDialog(env, 1003);
						}
						break;
					case QUEST_SELECT:
						return sendQuestDialog(env, 1011);
					default:
						return sendQuestStartDialog(env);
				}
			} else if (qs.getStatus() == QuestStatus.START) {
				switch (dialog) {
					case QUEST_SELECT:
						return sendQuestDialog(env, 2375);
					case SETPRO1:
						if(!player.getController().hasTask(TaskId.QUEST_TIMER)) { // A new chance starts
							QuestService.questTimerStart(env, questDurationTime);
						}
						return sendQuestSelectionDialog(env);
					case CHECK_USER_HAS_QUEST_ITEM:
						// Still time left; check collected items: reward if right number otherwise dialogue to continue
						if(player.getController().hasTask(TaskId.QUEST_TIMER)) {
							if (QuestService.collectItemCheck(env, true)) {
								qs.setStatus(QuestStatus.REWARD);
								updateQuestStatus(env);
								QuestService.questTimerEnd(env);
								return sendQuestDialog(env, 5);
							} else {
								return sendQuestDialog(env, 2716);
							}
						} else { // Time ended; remove quest items and ask for new chance;
							long mosbearTusks = player.getInventory().getItemCountByItemId(questDropItemId);
							removeQuestItem(env, questDropItemId, mosbearTusks);
							return sendQuestDialog(env, 3057);
						}
				}
			} else if (qs.getStatus() == QuestStatus.REWARD) {
				return sendQuestEndDialog(env);
			}
		}
		return false;
	}
	
	// On time end if not in reward status delete timer task
	@Override
	public boolean onQuestTimerEndEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			player.getController().cancelTask(TaskId.QUEST_TIMER);
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
			long mosbearTusks = player.getInventory().getItemCountByItemId(questDropItemId);
			removeQuestItem(env, questDropItemId, mosbearTusks);
			QuestService.abandonQuest(player, questId);
			return true;
		}
		return false;
	}
}
