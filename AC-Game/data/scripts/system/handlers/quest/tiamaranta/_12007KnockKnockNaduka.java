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

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author zhkchi
 */
public class _12007KnockKnockNaduka extends QuestHandler {

    private final static int questId = 12007;

    public _12007KnockKnockNaduka() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(205920).addOnQuestStart(questId);
        qe.registerQuestNpc(205920).addOnTalkEvent(questId);
        qe.registerQuestNpc(205956).addOnTalkEvent(questId);
        qe.registerQuestNpc(218317).addOnKillEvent(questId);
        qe.registerQuestNpc(218318).addOnKillEvent(questId);
        qe.registerQuestNpc(218319).addOnKillEvent(questId);
        qe.registerQuestNpc(218320).addOnKillEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 205920) {
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 1011);
                    case QUEST_ACCEPT_SIMPLE:
                        return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 205956 && qs.getQuestVarById(0) == 0) {
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 1352);
                    case SETPRO1:
                        return defaultCloseDialog(env, 0, 1);
                }
            } else if (targetId == 205956 && qs.getQuestVarById(0) == 10) {
                switch (dialog) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 2375);
                    case SELECT_QUEST_REWARD:
                        changeQuestStep(env, 10, 10, true);
                        return sendQuestDialog(env, 5);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205956) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            switch (env.getTargetId()) {
                case 218317:
                case 218318:
                case 218319:
                case 218320:
                    if (qs.getQuestVarById(0) < 10) {
                        qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                        updateQuestStatus(env);
                        return true;
                    }
            }
        }
        return false;
    }
}
