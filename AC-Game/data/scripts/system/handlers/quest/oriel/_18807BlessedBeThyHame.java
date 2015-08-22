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
package quest.oriel;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author zhkchi
 */
public class _18807BlessedBeThyHame extends QuestHandler {

    private static final int questId = 18807;

    public _18807BlessedBeThyHame() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(830194).addOnQuestStart(questId);
        qe.registerQuestNpc(830194).addOnTalkEvent(questId);
        qe.registerQuestNpc(730524).addOnTalkEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();
        QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 830194) {
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 1011);
                    case QUEST_ACCEPT_1:
                    case QUEST_ACCEPT_SIMPLE:
                        return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 730524: {
                    switch (dialog) {
                        case QUEST_SELECT:
                            return sendQuestDialog(env, 1352);
                        case SELECT_ACTION_1353:
                            return sendQuestDialog(env, 1353);
                        case SETPRO1:
                            return defaultCloseDialog(env, 0, 1);
                    }
                    break;
                }
                case 830194: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            return sendQuestDialog(env, 2375);
                        }
                        case SELECT_QUEST_REWARD:
                            changeQuestStep(env, 1, 1, true);
                            return sendQuestDialog(env, 5);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 830194) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }
}
