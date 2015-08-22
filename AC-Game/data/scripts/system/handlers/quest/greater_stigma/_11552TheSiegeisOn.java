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
package quest.greater_stigma;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author zhkchi
 *
 */
public class _11552TheSiegeisOn extends QuestHandler {

    private final static int questId = 11552;

    public _11552TheSiegeisOn() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(205531).addOnQuestStart(questId);
        qe.registerQuestNpc(205531).addOnTalkEvent(questId);
        qe.registerQuestNpc(259014).addOnAttackEvent(questId);
        qe.registerQuestNpc(259214).addOnAttackEvent(questId);
        qe.registerQuestNpc(259414).addOnAttackEvent(questId);
        qe.registerQuestNpc(259614).addOnAttackEvent(questId);
    }

    @Override
    public boolean onKillInWorldEvent(QuestEnv env) {
        return defaultOnKillRankedEvent(env, 0, 10, false); // reward
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();

        if (env.getTargetId() == 205531) {
            if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 4762);
                    case QUEST_ACCEPT_SIMPLE:
                        return sendQuestStartDialog(env);
                }
            } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1352);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onAttackEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVars().getQuestVars();
            if (var == 0 && env.getTargetId() == 259014) {
                changeQuestStep(env, var, var + 1, false);
                return true;
            } else if (var == 1 && env.getTargetId() == 259214) {
                changeQuestStep(env, var, var + 1, false);
                return true;
            } else if (var == 2 && env.getTargetId() == 259414) {
                changeQuestStep(env, var, var + 1, false);
                return true;
            } else if (var == 3 && env.getTargetId() == 259614) {
                changeQuestStep(env, var, var + 1, true);
                return true;
            }
        }
        return false;
    }
}
