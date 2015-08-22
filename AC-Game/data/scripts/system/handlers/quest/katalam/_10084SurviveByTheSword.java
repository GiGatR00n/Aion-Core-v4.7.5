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
package quest.katalam;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Romanz
 */
public class _10084SurviveByTheSword extends QuestHandler {

	private final static int questId = 10084;
	private final static int[] mobs = { 230407, 230408 };

	public _10084SurviveByTheSword() {
		super(questId);
	}

	@Override
	public void register() {
		int[] npcIds = { 800548, 800549, 800552, 800550, 800561, 701538, 800541 };
		qe.registerQuestItem(182215224, questId);
		qe.registerQuestNpc(206284).addOnAtDistanceEvent(questId);
		qe.registerQuestNpc(231225).addOnAtDistanceEvent(questId);
		qe.registerOnLevelUp(questId);
     	        qe.registerOnEnterZone(ZoneName.get("LDF5A_SENSORYAREA_Q10084_206284_4_600050000"),questId);
		for (int npcId : npcIds) {
			qe.registerQuestNpc(npcId).addOnTalkEvent(questId);
		}
		for (int mob : mobs) {
			qe.registerQuestNpc(mob).addOnKillEvent(questId);
		}
	}

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 10083);
	}

	@Override
	public boolean onDialogEvent(QuestEnv env) {
		final Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = env.getTargetId();
		DialogAction dialog = env.getDialog();

		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (targetId == 800548) { // Torio
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 0) {
							return sendQuestDialog(env, 1011);
						} else if (qs.getQuestVarById(0) == 1) {
							return sendQuestDialog(env, 1352);
						} else if (qs.getQuestVarById(0) == 6) {
							return sendQuestDialog(env, 2034);
						}
					}
					case SETPRO1: {
						return defaultCloseDialog(env, 0, 1);
					}
					case SETPRO4: {
						return defaultCloseDialog(env, 6, 7);
					}
					case CHECK_USER_HAS_QUEST_ITEM: {
						return checkQuestItems(env, 1, 2, false, 10000, 10001);
					}
					default:
						break;
				}
			} else if (targetId == 800549) { // Jemini
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 7) {
							return sendQuestDialog(env, 2375);
						}
					}
					case SETPRO5: {
						giveQuestItem(env, 182215224, 1);
						return defaultCloseDialog(env, 7, 8);
					}
					default:
						break;
				}
			} else if (targetId == 800552) { // Gram
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 9) {
							return sendQuestDialog(env, 3057);
						}
					}
					case SETPRO7: {
						return defaultCloseDialog(env, 9, 10);
					}
					default:
						break;
				}
			} else if (targetId == 800550) { // Mortal remains of a Beritra soldier
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 10) {
							return sendQuestDialog(env, 3398);
						}
					}
					case SETPRO8: {
						giveQuestItem(env, 182215228, 1);
						return defaultCloseDialog(env, 10, 11);
					}
					default:
						break;
				}
			} else if (targetId == 800561) { // Thrasir
				switch (dialog) {
					case QUEST_SELECT: {
						if (qs.getQuestVarById(0) == 12) {
							return sendQuestDialog(env, 4080);
						}
					}
					case SETPRO10: {
						return defaultCloseDialog(env, 12, 13);
					}
					default:
						break;
				}
			} else if (targetId == 701538) { // Broken Sword
				switch (dialog) {
					case USE_OBJECT: {
						if (qs.getQuestVarById(0) == 13) {
							return sendQuestDialog(env, 4082);
						}
					}
					case SETPRO11: {
						giveQuestItem(env, 182215229, 1);
						return defaultCloseDialog(env, 13, 14, true, false);
					}
					default:
						break;
				}
			}
		} else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 800541) { // Jakahn
				if (dialog == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 5);
				} else {
					removeQuestItem(env, 182215229, 1);
					removeQuestItem(env, 182215228, 1);
					return sendQuestEndDialog(env);
				}
			}
		}
		return false;
	}

	@Override
	public boolean onKillEvent(QuestEnv env) {
		return defaultOnKillEvent(env, mobs, 2, 6, 0);
	}

	@Override
	public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getQuestVarById(0) == 8) {
			changeQuestStep(env, 8, 9, false);
			removeQuestItem(env, 182215224, 1);
			return HandlerResult.SUCCESS;
		}
		return HandlerResult.FAILED;
	}

	@Override
	public boolean onAtDistanceEvent(QuestEnv env) {
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (qs.getQuestVarById(0) == 11) {
				changeQuestStep(env, 11, 12, false);
				return true;
			}
		}
		return false;
	}
    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        if (zoneName == ZoneName.get("LDF5A_SENSORYAREA_Q10084_206284_4_600050000")) {
            Player player = env.getPlayer();
            if (player == null)
                return false;
            QuestState qs = player.getQuestStateList().getQuestState(questId);
            if (qs != null && qs.getStatus() == QuestStatus.START) {
                int var = qs.getQuestVarById(0);
                if (var == 11) {
                    changeQuestStep(env, 11, 12, false);
                    return true;
                }
            }
        }
        return false;
    }
}
