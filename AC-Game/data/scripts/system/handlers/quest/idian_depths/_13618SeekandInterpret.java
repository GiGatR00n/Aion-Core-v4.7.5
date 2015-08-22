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
package quest.idian_depths;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Evil_dnk
 */
public class _13618SeekandInterpret extends QuestHandler {

    private final static int questId = 13618;

    public _13618SeekandInterpret() {
        super(questId);
    }

    public void register() {
        qe.registerQuestNpc(801543).addOnQuestStart(questId);
        qe.registerQuestNpc(801543).addOnTalkEvent(questId);
        qe.registerQuestNpc(730823).addOnTalkEvent(questId);
        qe.registerQuestNpc(730824).addOnTalkEvent(questId);

    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = env.getTargetId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 801543) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                }
                else {
                    return sendQuestStartDialog(env);
                }
            }
        }
        else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 730823) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 0){
                        return sendQuestDialog(env, 1352);
                    }
                }
                else if (dialog == DialogAction.SETPRO1) {
                    changeQuestStep(env, 0, 1, false);
                    return closeDialogWindow(env);
                }
            }
           else if (targetId == 730824) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 1){
                        return sendQuestDialog(env, 1693);
                    }
                }
                else if (dialog == DialogAction.SETPRO2) {
                    changeQuestStep(env, 1, 2, false);
                    return closeDialogWindow(env);
                }
            }
            else if (targetId == 801543) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 2){
                        return sendQuestDialog(env, 2375);
                    }
                }
                else if (dialog == DialogAction.SELECT_QUEST_REWARD) {
                    changeQuestStep(env, 2, 3, true);
                    return sendQuestDialog(env, 5);
                }
            }
        }

        else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 801543) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }
}

