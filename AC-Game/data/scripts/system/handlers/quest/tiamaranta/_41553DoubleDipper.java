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
package quest.tiamaranta;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Cheatkiller
 *
 */
public class _41553DoubleDipper extends QuestHandler {

    private final static int questId = 41553;

    public _41553DoubleDipper() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(205967).addOnQuestStart(questId);
        qe.registerQuestNpc(205967).addOnTalkEvent(questId);
        qe.registerQuestItem(182212548, questId);
        qe.registerQuestItem(182212549, questId);
        qe.registerQuestItem(182212550, questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 205967) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else if (dialog == DialogAction.QUEST_ACCEPT_SIMPLE) {
                    giveQuestItem(env, 182212548, 1);
                    return sendQuestStartDialog(env);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (targetId == 205967 && var == 1) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1352);
                } else if (dialog == DialogAction.SELECT_ACTION_1353) {
                    return sendQuestDialog(env, 1353);
                } else if (dialog == DialogAction.SETPRO2) {
                    return defaultCloseDialog(env, 1, 2);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205967) {
                switch (dialog) {
                    case USE_OBJECT: {
                        return sendQuestDialog(env, 10002);
                    }
                    default: {
                        removeQuestItem(env, 182212551, 1);
                        return sendQuestEndDialog(env);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int itemId = item.getItemTemplate().getTemplateId();
        if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) != 1) {
            switch (itemId) {
                case 182212548:
                    changeQuestStep(env, 0, 1, false);
                    break;
                case 182212549:
                    changeQuestStep(env, 2, 3, false);
                    break;
                case 182212550:
                    changeQuestStep(env, 3, 3, true);
                    break;
            }
            removeQuestItem(env, itemId, 1);
            giveQuestItem(env, itemId + 1, 1);
            return HandlerResult.SUCCESS;
        }
        return HandlerResult.FAILED;
    }
}
