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

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * Talk with Nokir (203631). Go to the MuMu Village and get rid of the Black
 * Claw Tribe (5): Black Claw Sharpeye (210455, 210456), Black Claw Warrior
 * (214039, 210458), Black Claw Searcher (214032). Go back to Nokir. Talk with
 * Shania (203621). Gather Arachna Poison Sacs (182203018) (3) and take them to
 * Shania. Go to the MuMu Village, and pour the toxin into the Fresh Water
 * Source (MUMU_VILLAGE_220030000). Mission completed! Report the result to
 * Nokir.
 *
 * @author Mr. Poke
 * @reworked vlog
 */
public class _2016FearThis extends QuestHandler {

    private final static int questId = 2016;
    private final static int[] mobs = {210455, 210456, 214039, 210458, 214032};

    public _2016FearThis() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(203631).addOnTalkEvent(questId);
        qe.registerQuestNpc(203621).addOnTalkEvent(questId);
        for (int mob : mobs) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        }
        qe.registerQuestItem(182203018, questId);
        qe.registerQuestItem(182203019, questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        final int var = qs.getQuestVarById(0);
        int targetId = env.getTargetId();

        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 203631: { // Nokir
                    switch (env.getDialog()) {
                        case QUEST_SELECT: {
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            } else if (var == 6) {
                                return sendQuestDialog(env, 1352);
                            }
                        }
                        case SELECT_ACTION_1012: {
                            playQuestMovie(env, 63);
                            return sendQuestDialog(env, 1012);
                        }
                        case SETPRO1: {
                            return defaultCloseDialog(env, 0, 1); // 1
                        }
                        case SETPRO2: {
                            return defaultCloseDialog(env, 6, 7); // 7
                        }
                    }
                    break;
                }
                case 203621: { // Shania
                    switch (env.getDialog()) {
                        case QUEST_SELECT: {
                            if (var == 7) {
                                return sendQuestDialog(env, 1693);
                            } else if (var == 8) {
                                return sendQuestDialog(env, 2034);
                            }
                        }
                        case SETPRO3: {
                            return defaultCloseDialog(env, 7, 8); // 8
                        }
                        case CHECK_USER_HAS_QUEST_ITEM: {
                            return checkQuestItems(env, 8, 10, false, 2035, 2120, 182203019, 1); // 10
                        }
                        case FINISH_DIALOG: {
                            return defaultCloseDialog(env, 8, 8);
                        }
                        case SETPRO4: {
                            return defaultCloseDialog(env, 10, 10);
                        }
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203631) { // Nokir
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 2375);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        return defaultOnKillEvent(env, mobs, 1, 6); // 6
    }

    @Override
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        Player player = env.getPlayer();
        if (player.isInsideZone(ZoneName.get("DF1A_ITEMUSEAREA_Q2016"))) {
            return HandlerResult.fromBoolean(useQuestItem(env, item, 10, 10, true)); // reward
        }
        return HandlerResult.FAILED;
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
