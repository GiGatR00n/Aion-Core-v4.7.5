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
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Gigi
 * @modified vlog
 */
public class _20020CrashoftheDredgion extends QuestHandler {

    private final static int questId = 20020;
    private final static int[] npcs = {799225, 799226, 799239, 798703, 700977};

    public _20020CrashoftheDredgion() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(799342).addOnKillEvent(questId);
        qe.registerQuestItem(182207600, questId);
        qe.registerOnEnterZone(ZoneName.get("COWARDS_COVE_220070000"), questId);
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        DialogAction dialog = env.getDialog();
        int var = qs.getQuestVarById(0);
        int targetId = env.getTargetId();

        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 799225: { // Richelle
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
                case 700977: {
                    return true;
                }
                case 799226: { // Valetta
                    switch (dialog) {
                        case QUEST_SELECT: {
                            switch (var) {
                                case 1: {
                                    return sendQuestDialog(env, 1352);
                                }
                                case 2: {
                                    return sendQuestDialog(env, 1693);
                                }
                                case 5: {
                                    return sendQuestDialog(env, 2716);
                                }
                                case 7: {
                                    return sendQuestDialog(env, 3398);
                                }
                            }
                        }
                        case SETPRO2: {
                            return defaultCloseDialog(env, 1, 2); // 2
                        }
                        case CHECK_USER_HAS_QUEST_ITEM: {
                            return checkQuestItems(env, 2, 3, false, 10000, 10001); // 3
                        }
                        case SETPRO6: {
                            return defaultCloseDialog(env, 5, 6); // 6
                        }
                        case SETPRO8: {
                            return defaultCloseDialog(env, 7, 8); // 8
                        }
                        case FINISH_DIALOG: {
                            return sendQuestSelectionDialog(env);
                        }
                    }
                    break;
                }
                case 799239: { // Vellun
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 3) {
                                return sendQuestDialog(env, 2034);
                            }
                        }
                        case SETPRO4: {
                            return defaultCloseDialog(env, 3, 4); // 4
                        }
                    }
                    break;
                }
                case 798703: { // Heszti
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 4) {
                                return sendQuestDialog(env, 2375);
                            }
                        }
                        case SETPRO5: {
                            return defaultCloseDialog(env, 4, 5, 182207600, 1, 0, 0); // 5
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
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (player.isInsideZone(ZoneName.get("DF4_ITEMUSEAREA_Q20020"))) {
                return HandlerResult.fromBoolean(useQuestItem(env, item, 6, 7, false)); // 7
            }
        }
        return HandlerResult.FAILED;
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        Player player = env.getPlayer();
        if (player == null) {
            return false;
        }
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (zoneName == ZoneName.get("COWARDS_COVE_220070000")) {
                int var = qs.getQuestVarById(0);
                if (var == 8) {
                    changeQuestStep(env, 8, 8, true); // reward
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        Player player = env.getPlayer();
        PlayerClass playerClass = player.getCommonData().getPlayerClass();
            if (playerClass == PlayerClass.RIDER) {
            return false;
            }
        return defaultOnLvlUpEvent(env, 20000, true);
    }
}
