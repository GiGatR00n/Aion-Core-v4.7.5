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
package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author Gigi
 */
public class _18602NightmareinShiningArmor extends QuestHandler {

    private final static int questId = 18602;
    private final static int[] npc_ids = {205229, 700939};

    public _18602NightmareinShiningArmor() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterWorld(questId);
        qe.registerOnDie(questId);
        for (int npc_id : npc_ids) {
            qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
        }
        qe.registerQuestNpc(205229).addOnQuestStart(questId);
        qe.registerQuestNpc(217005).addOnKillEvent(questId);
	qe.registerQuestNpc(217006).addOnKillEvent(questId);
        qe.registerQuestNpc(700939).addOnAtDistanceEvent(questId);
        qe.registerOnMovieEndQuest(454, questId);
    }

    @Override
    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (player.getWorldId() != 300230000) {
                int var = qs.getQuestVarById(0);
                if (var > 0) {
                    changeQuestStep(env, var, 0, false);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onDieEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var > 0) {
                changeQuestStep(env, var, 0, false);
                return true;
            }
        }
        return false;
    }

	@Override
	public boolean onKillEvent(QuestEnv env) {
		Player player = env.getPlayer();
		int targetId = env.getTargetId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if (qs != null && qs.getStatus() == QuestStatus.START) {
			if (targetId == 217005) {
				return defaultOnKillEvent(env, 217005, 3, true);
			}
			if (targetId == 217006) {
				qs.setStatus(QuestStatus.REWARD);
				updateQuestStatus(env);
				return true;
			}
		}
		return false;
	}

    @Override
    public boolean onMovieEndEvent(QuestEnv env, int movieId) {
        if (movieId != 454) {
            return false;
        }
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }
        changeQuestStep(env, 1, 2, false);
        return true;
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = env.getTargetId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
            if (targetId == 205229) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (targetId == 205229) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else if (env.getDialog() == DialogAction.SETPRO1) {
                    WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300230000);
                    InstanceService.registerPlayerWithInstance(newInstance, player);
                    TeleportService2.teleportTo(player, 300230000, newInstance.getInstanceId(), 244.98566f, 244.14162f,
                            189.52058f, (byte) 30);
                    changeQuestStep(env, 0, 1, false); // 1
                    return closeDialogWindow(env);
                }
            } else if (targetId == 700939) {
                if (env.getDialog() == DialogAction.USE_OBJECT) {
                    if (var == 2) {
                        return sendQuestDialog(env, 1693);
                    }
                } else if (env.getDialog() == DialogAction.SETPRO3) {
                    return defaultCloseDialog(env, 2, 3);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205229) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public boolean onAtDistanceEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && (qs.getStatus() == QuestStatus.START || qs.getStatus() == QuestStatus.COMPLETE)) {
            if (!player.getEffectController().hasAbnormalEffect(19288)) {
                SkillEngine.getInstance().applyEffectDirectly(19288, player, player, 0);
                return true;
            }
        }
        return false;
    }
}
