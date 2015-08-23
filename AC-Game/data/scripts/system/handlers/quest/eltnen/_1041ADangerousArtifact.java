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
package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * @author Xitanium
 * @reworked vlog
 */
public class _1041ADangerousArtifact extends QuestHandler {

    private final static int questId = 1041;

    public _1041ADangerousArtifact() {
        super(questId);
    }

	@Override
	public void register() {
		int[] npcs = { 203901, 204015, 203833, 278500, 204042, 700181 };
		qe.registerGetingItem(182201011, questId);
		qe.registerOnLogOut(questId);
		qe.registerAddOnReachTargetEvent(questId);
		qe.registerAddOnLostTargetEvent(questId);
		qe.registerOnEnterZone(ZoneName.get("LF2_SENSORYAREA_Q1041_A_206040_21_210020000"), questId);
		qe.registerOnEnterZone(ZoneName.get("LF2_SENSORYAREA_Q1041_B_206042_23_210020000"), questId);
		qe.registerOnLevelUp(questId);
		for (int npc : npcs) {
			qe.registerQuestNpc(npc).addOnTalkEvent(questId);
		}
	}

    @Override
    public boolean onDialogEvent(final QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();
        if (qs == null) {
            return false;
        }

        if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            switch (targetId) {
                case 203901: { // Telemachus
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            } else if (var == 3) {
                                return sendQuestDialog(env, 1693);
                            } else if (var == 6) {
                                return sendQuestDialog(env, 2716);
                            }
                        }
                        case SETPRO1: {
                            return defaultCloseDialog(env, 0, 1); // 1
                        }
                        case SETPRO3: {
                            return defaultCloseDialog(env, 3, 4); // 4
                        }
                        case SETPRO6: {
                            return defaultCloseDialog(env, 6, 7); // 7
                        }
                    }
                    break;
                }
                case 204015: { // Civil Engineer
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            }
                        }
                        case SETPRO2: {
                            return defaultStartFollowEvent(env, (Npc) env.getVisibleObject(), 2264.636f, 2359.2563f, 278.62735f, 1, 2);
                        }
                    }
                    break;
                }
                case 203833: { // Xenophon
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 4) {
                                return sendQuestDialog(env, 2034);
                            }
                        }
                        case SETPRO4: {
                            return defaultCloseDialog(env, 4, 5); // 5
                        }
                    }
                    break;
                }
                case 278500: { // Yuditio
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 5) {
                                return sendQuestDialog(env, 2375);
                            }
                        }
						case SETPRO5: {
							TeleportService2.teleportTo(player, 210020000, 267.9352f, 2790.1128f, 272.47702f);
							changeQuestStep(env, 5, 6, false); // 6
							return closeDialogWindow(env);
						}
                    }
                    break;
                }
                case 204042: { // Laigas
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 7) {
                                return sendQuestDialog(env, 3057);
                            } else if (var == 9) {
                                return sendQuestDialog(env, 3398);
                            }
                        }
                        case SETPRO7: {
                            giveQuestItem(env, 182201011, 1);
                            return closeDialogWindow(env);
                        }
                        case SETPRO8: {
                            changeQuestStep(env, 9, 9, true); // reward
                            playQuestMovie(env, 38);
                            return closeDialogWindow(env);
                        }
                    }
                    break;
                }
                case 700181: { // Stolen Artifact
                    if (dialog == DialogAction.USE_OBJECT) {
                        return useQuestObject(env, 8, 9, false, 0, 0, 0, 182201011, 1); // 9
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203901) { // Telemachus
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 3739);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onGetItemEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 7) {
                changeQuestStep(env, 7, 8, false); // 8
                return playQuestMovie(env, 37);
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
            if (var == 2) {
                changeQuestStep(env, 2, 1, false);
            }
        }
        return false;
    }

    @Override
    public boolean onNpcReachTargetEvent(QuestEnv env) {
        return defaultFollowEndEvent(env, 2, 3, false); // 3
    }

    @Override
    public boolean onNpcLostTargetEvent(QuestEnv env) {
        return defaultFollowEndEvent(env, 2, 1, false); // 1
    }

	@Override
	public boolean onLvlUpEvent(QuestEnv env) {
		return defaultOnLvlUpEvent(env, 1300, true);
	}
	
	@Override
	public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
	final Player player = env.getPlayer();
		if (player == null) {
			return false;
		}
		final QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs == null || qs.getStatus() != QuestStatus.START) {
			return false;
		}
		int var = qs.getQuestVarById(0);
		if (var == 2) {
		env.setQuestId(questId);
		changeQuestStep(env, 2, 3, false); // 3
		updateQuestStatus(env);
		return true;
		}
	return false;
	}
}
