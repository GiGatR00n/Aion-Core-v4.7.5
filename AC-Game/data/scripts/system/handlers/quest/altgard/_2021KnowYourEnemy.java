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
package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Mr. Poke
 */
public class _2021KnowYourEnemy extends QuestHandler {

    private final static int questId = 2021;

    public _2021KnowYourEnemy() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(203669).addOnTalkEvent(questId);
        qe.registerOnEnterZone(ZoneName.get("BLACK_CLAW_OUTPOST_220030000"), questId);
        qe.registerQuestNpc(700099).addOnKillEvent(questId);
        qe.registerQuestNpc(203557).addOnTalkEvent(questId);
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
                case 203669:
                    switch (env.getDialog()) {
                        case QUEST_SELECT:
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            } else if (var == 2) {
                                player.getEffectController().removeEffect(1868);
                                return sendQuestDialog(env, 1352);
                            } else if (var == 6) {
                                return sendQuestDialog(env, 1693);
                            }
                            break;
                        case SETPRO1:
                            if (var == 0) {
                                SkillEngine.getInstance().applyEffectDirectly(1868, player, player, 0);
                                return defaultCloseDialog(env, 0, 1); // 1
                            }
                            break;
                        case SETPRO2:
                            return defaultCloseDialog(env, 2, 3); // 3
                        case SETPRO3:
                            return defaultCloseDialog(env, 6, 6, true, false); // reward
                    }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203557) {
                if (env.getDialog() == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 2034);
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

        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (targetId == 700099 && var >= 3 && var < 6) {
            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(env);
            return true;
        }
        return false;
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        if (zoneName != ZoneName.get("BLACK_CLAW_OUTPOST_220030000")) {
            return false;
        }
        final Player player = env.getPlayer();
        if (player == null) {
            return false;
        }
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        if (qs.getQuestVarById(0) == 1) {
            qs.setQuestVarById(0, 2);
            updateQuestStatus(env);
            return true;
        }
        return false;
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 2200, true);
    }
}
