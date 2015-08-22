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
 * @author Nephis
 * @reworked & modified Gigi, vlog
 */
public class _20024BattlesAtVorgaltem extends QuestHandler {

    private final static int questId = 20024;
    private final static int[] mobs = {216104, 216101, 216109, 216112, 216107, 216033, 216034, 216448, 216450, 216451,
        216108, 216449, 700811};

    public _20024BattlesAtVorgaltem() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {799226, 799308, 799298, 798713, 700707};
        qe.registerOnLevelUp(questId);
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerQuestItem(182207615, questId);
        for (int mob : mobs) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        }
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        int targetId = env.getTargetId();

        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 799226: { // Valetta
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            }
                        }
                        case SETPRO1: {
                            return defaultCloseDialog(env, 0, 1); // 1
                        }
                    }
                    break;
                }
                case 799308: { // Ankea
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            } else if (var == 3) {
                                return sendQuestDialog(env, 2034);
                            }
                        }
                        case SETPRO2: {
                            return defaultCloseDialog(env, 1, 2); // 2
                        }
                        case SETPRO4: {
                            return defaultCloseDialog(env, 3, 4); // 4
                        }
                    }
                    break;
                }
                case 799298: { // Vegrim
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 4) {
                                return sendQuestDialog(env, 2375);
                            } else if (var == 5) {
                                return sendQuestDialog(env, 2716);
                            } else if (var == 7) {
                                return sendQuestDialog(env, 3398);
                            } else if (var == 9) {
                                return sendQuestDialog(env, 4080);
                            } else if (var == 11) {
                                return sendQuestDialog(env, 1608);
                            }
                        }
                        case SELECT_ACTION_1609: {
                            if (var == 11) {
                                playQuestMovie(env, 554);
                                return sendQuestDialog(env, 1609);
                            }
                        }
                        case CHECK_USER_HAS_QUEST_ITEM: {
                            return checkQuestItems(env, 5, 6, false, 10000, 10001); // 6
                        }
                        case SETPRO5: {
                            return defaultCloseDialog(env, 4, 5); // 5
                        }
                        case SETPRO8: {
                            return defaultCloseDialog(env, 7, 8, 182207615, 1, 0, 0); // 8
                        }
                        case SETPRO10: {
                            return defaultCloseDialog(env, 9, 10, 182207616, 1, 0, 0); // 10
                        }
                        case SET_SUCCEED: {
                            return defaultCloseDialog(env, 11, 11, true, false); // reward
                        }
                        case FINISH_DIALOG: {
                            return sendQuestSelectionDialog(env);
                        }
                    }
                    break;
                }
                case 798713: { // Fjoersvith
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 6) {
                                return sendQuestDialog(env, 3057);
                            }
                        }
                        case SETPRO7: {
                            return defaultCloseDialog(env, 6, 7); // 7
                        }
                    }
                    break;
                }
                case 700707: { // Drana Control Tower
                    switch (dialog) {
                        case USE_OBJECT: {
                            return useQuestObject(env, 10, 11, false, 0, 0, 0, 182207616, 1); // 11
                        }
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799226) { // Valetta
                if (env.getDialog() == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
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
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 2) {
                int[] nadukas = {216034, 216033};
                int[] balaurs = {216448, 216450, 216451, 216108, 216449, 216107, 216112, 216109, 216104, 216101};
                int targetId = env.getTargetId();
                int var1 = qs.getQuestVarById(1);
                int var2 = qs.getQuestVarById(2);

                switch (targetId) {
                    case 216034:
                    case 216033: { // Naduka Wardrake
                        if (var1 < 4) {
                            return defaultOnKillEvent(env, nadukas, 0, 4, 1); // 1: 0 - 9
                        } else if (var1 == 4) {
                            if (var2 == 10) {
                                qs.setQuestVar(3);
                                updateQuestStatus(env);
                                return true;
                            } else {
                                return defaultOnKillEvent(env, nadukas, 4, 5, 1); // 1: 10
                            }
                        }
                        break;
                    }
                    case 216448:
                    case 216450:
                    case 216451:
                    case 216108:
                    case 216449:
                    case 216107:
                    case 216112:
                    case 216109:
                    case 216104:
                    case 216101: { // Balaur of Vorgaltem Battlefield
                        if (var2 < 9) {
                            return defaultOnKillEvent(env, balaurs, 0, 9, 2); // 2: 0 - 29
                        } else if (var2 == 9) {
                            if (var1 == 5) {
                                qs.setQuestVar(3);
                                updateQuestStatus(env);
                                return true;
                            } else {
                                return defaultOnKillEvent(env, balaurs, 9, 10, 2); // 2: 30
                            }
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        Player player = env.getPlayer();
        if (player.isInsideZone(ZoneName.get("DF4_ITEMUSEAREA_Q20024"))) {
            return HandlerResult.fromBoolean(useQuestItem(env, item, 8, 9, false)); // 9
        }
        return HandlerResult.FAILED;
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        int[] quests = {20022, 20023};
        return defaultOnLvlUpEvent(env, quests, true);
    }
}
