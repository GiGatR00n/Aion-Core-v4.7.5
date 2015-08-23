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
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * Talk with Loriniah (203605). Scout the MuMu Farmland
 * (MUMU_FARMLAND_220030000). Scouting completed! Talk with Loriniah. Burn the
 * MuMu Carts (700096) in the MuMu Farmland (3). Talk with Loriniah. Defeat the
 * Skurvs and Mau and bring the evidence to Loriniah.
 *
 * @author Mr. Poke
 * @reworked vlog, Gigi
 */
public class _2013ADangerousCrop extends QuestHandler {

    private final static int questId = 2013;

    public _2013ADangerousCrop() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(203605).addOnTalkEvent(questId);
        qe.registerQuestNpc(700096).addOnTalkEvent(questId);
        qe.registerOnEnterZone(ZoneName.get("MUMU_FARMLAND_220030000"), questId);
    }

    @Override
    public boolean onDialogEvent(final QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
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
                case 203605: { // Loriniah
                    switch (env.getDialog()) {
                        case QUEST_SELECT:
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            } else if (var == 2) {
                                return sendQuestDialog(env, 1352);
                            } else if (var == 8) {
                                return sendQuestDialog(env, 1693);
                            } else if (var == 9) {
                                return sendQuestDialog(env, 2034);
                            }
                        case SELECT_ACTION_1354:
                            playQuestMovie(env, 61);
                            return sendQuestDialog(env, 1354);
                        case SETPRO1:
                            return defaultCloseDialog(env, 0, 1); // 1
                        case SETPRO2:
                            return defaultCloseDialog(env, 2, 3, 182203012, 1, 0, 0); // 3
                        case SETPRO3:
                            return defaultCloseDialog(env, 8, 9, 0, 0, 182203012, 1); // 9
                        case CHECK_USER_HAS_QUEST_ITEM:
                            return checkQuestItems(env, 9, 9, true, 5, 2120); // reward
                    }
                    break;
                }
                case 700096: { // MuMu Cart
                    switch (env.getDialog()) {
                        case USE_OBJECT: {
                            if (var >= 3 && var < 5) {
                                return useQuestObject(env, var, var + 1, false, true); // 4,5
                            } else if (var == 5) {
                                return useQuestObject(env, 5, 8, false, true); // 8
                            }
                        }
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203605) // Loriniah
            {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        if (zoneName == ZoneName.get("MUMU_FARMLAND_220030000")) {
            Player player = env.getPlayer();
            if (player == null) {
                return false;
            }
            QuestState qs = player.getQuestStateList().getQuestState(questId);
            if (qs != null && qs.getStatus() == QuestStatus.START) {
                int var = qs.getQuestVarById(0);
                if (var == 1) {
                    changeQuestStep(env, 1, 2, false); // 2
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env, 2012);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 2200, true);
    }
}
