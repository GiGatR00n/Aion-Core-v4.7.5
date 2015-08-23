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

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Rhys2002 edited by xaerolt
 * @reworked vlog
 */
public class _1042KeeperoftheKaidanKey extends QuestHandler {

    private final static int questId = 1042;

    public _1042KeeperoftheKaidanKey() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npc_ids = {203989, 203901, 730342};
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerQuestItem(182201026, questId);
        qe.addHandlerSideQuestDrop(questId, 730342, 182201026, 1, 100);
        qe.addHandlerSideQuestDrop(questId, 212025, 182201026, 1, 100);
        qe.addHandlerSideQuestDrop(questId, 212029, 182201026, 1, 100);
        qe.addHandlerSideQuestDrop(questId, 212033, 182201026, 1, 100);
        for (int npc_id : npc_ids) {
            qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
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
            if (targetId == 203989) { // Tumblusen
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        }
                    }
                    case SELECT_ACTION_1012: {
                        playQuestMovie(env, 185);
                        return sendQuestDialog(env, 1012);
                    }
                    case SETPRO1: {
                        return defaultCloseDialog(env, 0, 1); // 1
                    }
                }
            } else if (targetId == 730342) { // Strong Document Box
                if (dialog == DialogAction.USE_OBJECT) {
                    if (var == 1) {
                        return true; // loot;
                    }
                }
            } else if (targetId == 203901) { // Telemachus
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 2) {
                            return sendQuestDialog(env, 1352);
                        }
                    }
                    case CHECK_USER_HAS_QUEST_ITEM: {
                        return checkQuestItems(env, 2, 2, true, 5, 1438); // reward
                    }
                    case FINISH_DIALOG: {
                        return sendQuestSelectionDialog(env);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203901) { // Telemachus
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        return HandlerResult.fromBoolean(useQuestItem(env, item, 1, 2, false)); // 2
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env, 1040);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        int[] quests = {1300, 1040};
        return defaultOnLvlUpEvent(env, quests, true);
    }
}
