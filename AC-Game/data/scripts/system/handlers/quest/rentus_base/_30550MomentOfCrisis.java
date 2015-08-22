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
package quest.rentus_base;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Ritsu
 *
 */
public class _30550MomentOfCrisis extends QuestHandler {

    private final static int questId = 30550;

    public _30550MomentOfCrisis() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(205864).addOnQuestStart(questId); //Skafir
        qe.registerQuestNpc(205864).addOnTalkEvent(questId);
        qe.registerQuestNpc(799549).addOnTalkEvent(questId); //Maios
        qe.registerQuestNpc(799544).addOnTalkEvent(questId); //Oreitia
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();
        if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
            if (targetId == 205864) {
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 1011);
                    default:
                        return sendQuestStartDialog(env);
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            switch (targetId) {

                case 799549: {
                    switch (dialog) {
                        case QUEST_SELECT:
                            return sendQuestDialog(env, 1352);
                        case SETPRO1: {
                            return defaultCloseDialog(env, 0, 1);
                        }
                    }
                }
                case 799544: {
                    switch (dialog) {
                        case QUEST_SELECT:
                            return sendQuestDialog(env, 2375);
                        case SELECT_QUEST_REWARD: {
                            if (var == 1) {
                                qs.setStatus(QuestStatus.REWARD);
                                updateQuestStatus(env);
                                return sendQuestDialog(env, 5);
                            }
                        }
                    }
                }
            }

        } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            switch (targetId) {
                case 799544: {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }
}
