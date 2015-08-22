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
package quest.hero;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;


public class _23500InspecttheKatalamBase extends QuestHandler {

    private final static int questId = 23500;

    public _23500InspecttheKatalamBase() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(800529).addOnQuestStart(questId);
        qe.registerQuestNpc(800529).addOnTalkEvent(questId);
        qe.registerQuestNpc(801239).addOnTalkEvent(questId);
        qe.registerQuestNpc(801244).addOnTalkEvent(questId);
        qe.registerQuestNpc(801241).addOnTalkEvent(questId);
    }


    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = env.getTargetId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (env.getTargetId() == 800529) {
            if (qs == null || qs.getStatus() == QuestStatus.NONE) {
                if (env.getDialog() == DialogAction.QUEST_SELECT)
                    return sendQuestDialog(env, 1011);
                if (env.getDialog() == DialogAction.SELECT_ACTION_1013) {

                    return sendQuestDialog(env, 1013);
                }
                if (env.getDialog() == DialogAction.SETPRO1) {
                    QuestService.startQuest(env, QuestStatus.START, false, 1);
                    giveQuestItem(env, 182215271, 1);
                    return sendQuestDialog(env, 1352);
                }
                if (env.getDialog() == DialogAction.SETPRO2) {
                    QuestService.startQuest(env, QuestStatus.START, false, 2);
                    giveQuestItem(env, 182215271, 1);
                    return sendQuestDialog(env, 1693);
                }
                if (env.getDialog() == DialogAction.SETPRO3) {
                    QuestService.startQuest(env, QuestStatus.START, false, 3);
                    giveQuestItem(env, 182215271, 1);
                    return sendQuestDialog(env, 2034);
                } else
                    return sendQuestStartDialog(env, 182215271, 1);
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (env.getTargetId() == 801239) {
                if (env.getDialog() == DialogAction.QUEST_SELECT)
                {
                    return sendQuestDialog(env, 2375);
                }
                if (env.getDialog() == DialogAction.SELECT_QUEST_REWARD)
                {
                    changeQuestStepRew(env, 0, 1);
                    return sendQuestDialog(env, 5);
                }

            }
            if (env.getTargetId() == 801241) {
                if (env.getDialog() == DialogAction.QUEST_SELECT)
                {
                    return sendQuestDialog(env, 2716);

                }
                if (env.getDialog() == DialogAction.SELECT_QUEST_REWARD)
                {
                    changeQuestStepRew(env, 0, 2);
                    return sendQuestDialog(env, 6);
                }

            }
            if (env.getTargetId() == 801244) {
                if (env.getDialog() == DialogAction.QUEST_SELECT)
                {
                    return sendQuestDialog(env, 3057);
                }
                if (env.getDialog() == DialogAction.SELECT_QUEST_REWARD)
                {
                    changeQuestStepRew(env, 0, 3);
                    return sendQuestDialog(env, 7);
                }

            }
        }
		else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == 801239 || targetId == 801241 || targetId == 801244) {
				int rewInd = 0;
				if(qs.getQuestVarById(0) == 2)
					rewInd = 1;
				else if(qs.getQuestVarById(0) == 3)
					rewInd = 2;
				return sendQuestEndDialog(env, rewInd);
			}
		}
		return false;
    }
}


