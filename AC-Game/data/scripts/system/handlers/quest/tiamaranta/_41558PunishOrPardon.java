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
public class _41558PunishOrPardon extends QuestHandler {

    private final static int questId = 41558;

    public _41558PunishOrPardon() {
        super(questId);
    }

    public void register() {
        qe.registerQuestItem(182212527, questId);
        qe.registerGetingItem(182212528, questId);
        qe.registerQuestNpc(205914).addOnQuestStart(questId);
        qe.registerQuestNpc(205914).addOnTalkEvent(questId);
        qe.registerQuestNpc(205894).addOnTalkEvent(questId);
        qe.registerQuestNpc(701324).addOnTalkEvent(questId);
        qe.registerQuestNpc(218725).addOnKillEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 205914) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 205894) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else if (dialog == DialogAction.SETPRO10) {
                    giveQuestItem(env, 182212527, 1);
                    changeQuestStep(env, 0, 1, false);
                    return sendQuestDialog(env, 1352);
                } else if (dialog == DialogAction.SETPRO20) {
                    return defaultCloseDialog(env, 0, 2);
                }
            } else if (targetId == 205914) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1693);
                } else if (dialog == DialogAction.SETPRO3) {
                    return defaultCloseDialog(env, 2, 3);
                }
            } else if (targetId == 701324) {
                return true;
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205894) {
                switch (dialog) {
                    case USE_OBJECT: {
                        return sendQuestDialog(env, 4081);
                    }
                    default: {
                        return sendQuestEndDialog(env);
                    }
                }
            } else if (targetId == 205914) {
                switch (dialog) {
                    case USE_OBJECT: {
                        return sendQuestDialog(env, 4166);
                    }
                    default: {
                        removeQuestItem(env, 182212528, 1);
                        return sendQuestEndDialog(env, 1);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        return defaultOnKillEvent(env, 218725, 4, true);
    }

    @Override
    public boolean onGetItemEvent(QuestEnv env) {
        return defaultOnGetItemEvent(env, 3, 4, false);
    }

    @Override
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            return HandlerResult.fromBoolean(useQuestItem(env, item, 1, 1, true)); // reward
        }
        return HandlerResult.FAILED;
    }
}
