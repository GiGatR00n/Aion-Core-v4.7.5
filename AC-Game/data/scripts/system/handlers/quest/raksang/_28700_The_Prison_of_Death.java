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
package quest.raksang;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Cheatkiller
 *
 */
public class _28700_The_Prison_of_Death extends QuestHandler {

    private final static int questId = 28700;
    private final static int[] mobs = {730453, 730454, 730455, 730456, 217392, 217425, 217451, 217764, 217647};

    public _28700_The_Prison_of_Death() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(799266).addOnQuestStart(questId);
        qe.registerQuestNpc(799266).addOnTalkEvent(questId);
        qe.registerQuestNpc(799439).addOnTalkEvent(questId);
        qe.registerQuestNpc(799429).addOnTalkEvent(questId);
        qe.registerQuestNpc(799438).addOnTalkEvent(questId);
        qe.registerQuestNpc(730468).addOnTalkEvent(questId);
        qe.registerOnEnterZone(ZoneName.get("RAKSANG_DUNGEON_CHASM_300310000"), questId);
        for (int mob : mobs) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        }
    }

    @Override
    public boolean onDialogEvent(final QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (targetId == 799532) {
            if (qs == null || qs.getStatus() == QuestStatus.NONE) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env);
                }
            } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
                switch (dialog) {
                    case USE_OBJECT:
                        return sendQuestDialog(env, 10002);
                    case SELECT_QUEST_REWARD:
                        return sendQuestDialog(env, 5);
                    default:
                        return sendQuestEndDialog(env);
                }
            }
        }

        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 799439) {
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 1011);
                    case SETPRO1:
                        return defaultCloseDialog(env, 0, 1); // 1
                }
            } else if (targetId == 799429) {
                switch (dialog) {
                    case USE_OBJECT:
                        return sendQuestDialog(env, 2375);
                    case SETPRO5:
                        playQuestMovie(env, 455);
                        return defaultCloseDialog(env, 4, 5);
                }
            }
        } else if (qs.getStatus() != QuestStatus.START) {
            return false;
        }

        if (targetId == 799438) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 1) {
                        return sendQuestDialog(env, 1352);
                    }
                case SETPRO2:
                    return defaultCloseDialog(env, 1, 2); // 2
            }
        }
        if (qs.getStatus() == QuestStatus.START && var == 9) {
            if (targetId == 799439) {
                switch (dialog) {
                    case USE_OBJECT:
                        return sendQuestDialog(env, 4080);
                    case SET_SUCCEED:
                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(env);
                        return closeDialogWindow(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 2) {
                checkAndUpdateVarGen(qs, env, targetId);
            } else if (var == 3) {
                return defaultOnKillEvent(env, 217392, 3, 4); // 4
            } else if (var == 5) {
                checkAndUpdateVarBosses(qs, env, targetId);
            } else if (var == 7) {
                return defaultOnKillEvent(env, 217764, 7, 8); // 8
            } else if (var == 8) {
                return defaultOnKillEvent(env, 217647, 8, 9); // 8
            }
        }
        return false;
    }

    private void checkAndUpdateVarGen(QuestState qs, QuestEnv env, int targetId) {
        switch (targetId) {
            case 730453:
                qs.setQuestVarById(1, 1);
                updateQuestStatus(env);
                isAllKilledGenerator(qs, env);
                break;
            case 730454:
                qs.setQuestVarById(2, 1);
                updateQuestStatus(env);
                isAllKilledGenerator(qs, env);
                break;
            case 730455:
                qs.setQuestVarById(3, 1);
                updateQuestStatus(env);
                isAllKilledGenerator(qs, env);
                break;
            case 730456:
                qs.setQuestVarById(4, 1);
                updateQuestStatus(env);
                isAllKilledGenerator(qs, env);
                break;
        }
    }

    private void isAllKilledGenerator(QuestState qs, QuestEnv env) {
        if (qs.getQuestVarById(1) == 1 && qs.getQuestVarById(2) == 1 && qs.getQuestVarById(3) == 1 && qs.getQuestVarById(4) == 1) {
            qs.setQuestVarById(1, 0);
            qs.setQuestVarById(2, 0);
            qs.setQuestVarById(3, 0);
            qs.setQuestVarById(4, 0);
            changeQuestStep(env, 2, 3, false);
        }
    }

    private void checkAndUpdateVarBosses(QuestState qs, QuestEnv env, int targetId) {
        switch (targetId) {
            case 217425:
                qs.setQuestVarById(1, 1);
                updateQuestStatus(env);
                isAllKilledBosses(qs, env);
                break;
            case 217451:
                qs.setQuestVarById(2, 1);
                updateQuestStatus(env);
                isAllKilledBosses(qs, env);
                break;
        }
    }

    private void isAllKilledBosses(QuestState qs, QuestEnv env) {
        if (qs.getQuestVarById(1) == 1 && qs.getQuestVarById(2) == 1) {
            qs.setQuestVarById(1, 0);
            qs.setQuestVarById(2, 0);
            changeQuestStep(env, 5, 6, false);
        }
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        if (zoneName != ZoneName.get("RAKSANG_DUNGEON_CHASM_300310000")) {
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

        if (qs.getQuestVarById(0) == 6) {
            changeQuestStep(env, 6, 7, false);
            return true;
        }
        return false;
    }
}
