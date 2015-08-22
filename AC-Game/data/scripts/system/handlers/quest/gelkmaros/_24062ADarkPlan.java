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
package quest.gelkmaros;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author pralinka
 */
public class _24062ADarkPlan extends QuestHandler {

    private final static int questId = 24062;
    private final static int[] mob_ids = {216061, 216062, 216055, 216056, 216757, 216067};

    public _24062ADarkPlan() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(799364).addOnTalkEvent(questId);
        qe.registerQuestNpc(799295).addOnTalkEvent(questId);
        qe.registerQuestNpc(799326).addOnTalkEvent(questId);
        qe.registerOnEnterZone(ZoneName.get("LF4_SensoryArea_Q24062_220070000"), questId);
        for (int mob_id : mob_ids) {
            qe.registerQuestNpc(mob_id).addOnKillEvent(questId);
        }
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 24061, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        final int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 799364: //mehren.
                    if (var == 0 && env.getDialog() == DialogAction.QUEST_SELECT) {
                        return sendQuestDialog(env, 1011);
                    }
                    if (var == 11 && env.getDialog() == DialogAction.QUEST_SELECT) {
                        if (QuestService.collectItemCheck(env, true)) {
                            qs.setStatus(QuestStatus.REWARD);
                            updateQuestStatus(env);
                            return sendQuestDialog(env, 5);
                        }
                    }
                    if (env.getDialog() == DialogAction.SETPRO1) {
                        return defaultCloseDialog(env, 0, 1);
                    }
                    break;
                case 799295: // ortiz.
                    if (var == 1 && env.getDialog() == DialogAction.QUEST_SELECT) {
                        return sendQuestDialog(env, 1352);
                    } else if (var == 8 && env.getDialog() == DialogAction.QUEST_SELECT) {
                        return sendQuestDialog(env, 3739);
                    }
                    if (env.getDialog() == DialogAction.SETPRO2) {
                        return defaultCloseDialog(env, 1, 2);
                    }
                    if (env.getDialog() == DialogAction.SETPRO9) {
                        return defaultCloseDialog(env, 8, 9);
                    }
                    break;
                case 799326: // rhonnam.
                    if (var == 9 && env.getDialog() == DialogAction.QUEST_SELECT) {
                        return sendQuestDialog(env, 4080);
                    }
                    if (env.getDialog() == DialogAction.SETPRO10) {
                        return defaultCloseDialog(env, 9, 10);
                    }
                    break;
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799364) { // merhen. 
                if (env.getDialog() == DialogAction.SELECT_QUEST_REWARD) {
                    return sendQuestEndDialog(env);
                } else if (env.getDialog() == DialogAction.SELECTED_QUEST_REWARD1) {
                    return sendQuestEndDialog(env);
                } else if (env.getDialog() == DialogAction.SELECTED_QUEST_REWARD2) {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        return defaultOnKillEvent(env, mob_ids, 2, 8); // 8
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (zoneName.equals(ZoneName.get("LF4_SensoryArea_Q24062_220070000"))) {
                if (var == 10) {
                    changeQuestStep(env, 10, 11, false);
                    return true;
                }
            }
        }
        return false;
    }
}
