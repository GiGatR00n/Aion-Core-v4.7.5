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
package quest.sarpan;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Cheatkiller
 *
 */
public class _41300TheRedStallion extends QuestHandler {

    private final static int questId = 41300;

    public _41300TheRedStallion() {
        super(questId);
    }

    public void register() {
        qe.registerQuestNpc(205763).addOnQuestStart(questId);
        qe.registerQuestNpc(205794).addOnTalkEvent(questId);
        qe.registerOnRide(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 205763) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else {
                    return sendQuestStartDialog(env, 190100014, 1);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 205794) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 1) {
                        return sendQuestDialog(env, 2375);
                    }
                } else if (dialog == DialogAction.SELECT_QUEST_REWARD) {
                    return defaultCloseDialog(env, 1, 1, true, true);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205794) {
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 5);
                }
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public boolean rideAction(QuestEnv env, int rideItemId) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (rideItemId == 190100014) {
                changeQuestStep(env, 0, 1, false);
                return true;
            }
        }
        return false;
    }
}
