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
package quest.reshanta;

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
public class _4718PressingTheAttack extends QuestHandler {

    private final static int questId = 4718;

    public _4718PressingTheAttack() {
        super(questId);
    }

    public void register() {
        qe.registerOnDredgionReward(questId);
        qe.registerQuestNpc(278001).addOnQuestStart(questId);
        qe.registerQuestNpc(278001).addOnTalkEvent(questId);
        qe.registerQuestNpc(214814).addOnKillEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 278001) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            int var1 = qs.getQuestVarById(1);
            int var2 = qs.getQuestVarById(2);
            if (targetId == 278001) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 0) {
                        return sendQuestDialog(env, 1011);
                    } else if (var1 == 3 && var2 == 8) {
                        return sendQuestDialog(env, 10002);
                    }
                } else if (dialog == DialogAction.SETPRO1) {
                    return defaultCloseDialog(env, 0, 1);
                } else if (dialog == DialogAction.SELECT_QUEST_REWARD) {
                    return defaultCloseDialog(env, 1, 1, true, true);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 278001) {
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                }
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        return defaultOnKillEvent(env, 214814, 0, 8, 2);
    }

    @Override
    public boolean onDredgionRewardEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var1 = qs.getQuestVarById(1);
            if (var1 < 3) {
                changeQuestStep(env, var1, var1 + 1, false, 1);
                return true;
            }
        }
        return false;
    }
}
