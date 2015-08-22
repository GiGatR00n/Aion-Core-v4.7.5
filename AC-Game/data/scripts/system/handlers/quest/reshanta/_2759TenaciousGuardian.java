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

import java.util.ArrayList;
import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author vlog
 */
public class _2759TenaciousGuardian extends QuestHandler {

    private static final int questId = 2759;
    private final List<Integer> killedMobs = new ArrayList<Integer>();

    public _2759TenaciousGuardian() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(264769).addOnQuestStart(questId);
        qe.registerQuestNpc(264769).addOnTalkEvent(questId);
        qe.registerQuestNpc(278588).addOnKillEvent(questId);
        qe.registerQuestNpc(278589).addOnKillEvent(questId);
        qe.registerQuestNpc(278590).addOnKillEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 264769) { // Gudharten
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (targetId == 264769) { // Gudharten
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (var == 3) {
                        return sendQuestDialog(env, 1352);
                    }
                } else if (dialog == DialogAction.SELECT_QUEST_REWARD) {
                    changeQuestStep(env, 3, 3, true); // reward
                    killedMobs.clear();
                    return sendQuestDialog(env, 5);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 264769) { // Gudharten
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var < 3) {
                switch (targetId) {
                    case 278588: {
                        if (!killedMobs.contains(278588)) {
                            killedMobs.add(278588);
                            return defaultOnKillEvent(env, 278588, var, var + 1);
                        }
                        break;
                    }
                    case 278589: {
                        if (!killedMobs.contains(278589)) {
                            killedMobs.add(278589);
                            return defaultOnKillEvent(env, 278589, var, var + 1);
                        }
                        break;
                    }
                    case 278590: {
                        if (!killedMobs.contains(278590)) {
                            killedMobs.add(278590);
                            return defaultOnKillEvent(env, 278590, var, var + 1);
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }
}
