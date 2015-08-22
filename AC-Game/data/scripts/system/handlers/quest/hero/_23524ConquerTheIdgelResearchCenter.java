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

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

public class _23524ConquerTheIdgelResearchCenter extends QuestHandler {

    public static final int questId = 23524;

    public _23524ConquerTheIdgelResearchCenter() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterWorld(questId);
        qe.registerQuestNpc(800918).addOnQuestStart(questId);
        qe.registerQuestNpc(800918).addOnTalkEvent(questId);
        qe.registerQuestNpc(800918).addOnTalkEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();
        QuestState qs1 = player.getQuestStateList().getQuestState(28010);
        QuestState qs2 = player.getQuestStateList().getQuestState(28011);
        QuestState qs3 = player.getQuestStateList().getQuestState(28012);
        QuestState qs4 = player.getQuestStateList().getQuestState(28013);
        QuestState qs5 = player.getQuestStateList().getQuestState(28014);


        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 800918) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (qs1 == null || qs2 == null || qs3 == null || qs4 == null || qs5 == null) {
                            return closeDialogWindow(env);
                        }

                        if (qs1.getStatus() == QuestStatus.COMPLETE && qs2.getStatus() == QuestStatus.COMPLETE && qs3.getStatus() == QuestStatus.COMPLETE
                                && qs4.getStatus() == QuestStatus.COMPLETE && qs5.getStatus() == QuestStatus.COMPLETE) {
                        QuestService.startQuest(env);
                        return sendQuestDialog(env, 2375);
                        }
                         else
                            return closeDialogWindow(env);
                    }

                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 800918: {
                    switch (dialog) {
                        case QUEST_SELECT: {
                            return sendQuestDialog(env, 2375);
                        }
                        case SELECT_QUEST_REWARD: {
                            changeQuestStep(env, 0, 0, true);
                            return sendQuestEndDialog(env);
                        }
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 800918) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }
    @Override
    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        QuestState qs1 = player.getQuestStateList().getQuestState(28010);
        QuestState qs2 = player.getQuestStateList().getQuestState(28011);
        QuestState qs3 = player.getQuestStateList().getQuestState(28012);
        QuestState qs4 = player.getQuestStateList().getQuestState(28013);
        QuestState qs5 = player.getQuestStateList().getQuestState(28014);

        if (qs1 == null || qs2 == null || qs3 == null || qs4 == null || qs5 == null) {
            return false;
        }

        if (qs1.getStatus() == QuestStatus.COMPLETE && qs2.getStatus() == QuestStatus.COMPLETE && qs3.getStatus() == QuestStatus.COMPLETE
                && qs4.getStatus() == QuestStatus.COMPLETE && qs5.getStatus() == QuestStatus.COMPLETE) {
            if (qs == null || qs.getStatus() == QuestStatus.NONE){
                QuestService.startQuest(env);
                return true;
            }
        }
        return false;
    }
}

