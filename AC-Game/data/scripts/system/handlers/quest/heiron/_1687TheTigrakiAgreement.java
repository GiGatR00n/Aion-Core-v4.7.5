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
package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * Go to Draupnir Cave in Asmodae and get Blue Balaur Blood (186000035) (2) and
 * Balaur Rainbow Scales (186000036) (5) for Brosia (204601). Go to Brosia to
 * choose your reward.
 *
 * @author Balthazar
 * @reworked vlog
 */
public class _1687TheTigrakiAgreement extends QuestHandler {

    private final static int questId = 1687;
    private int rewardGroup;

    public _1687TheTigrakiAgreement() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(204601).addOnQuestStart(questId);
        qe.registerQuestNpc(204601).addOnTalkEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
            if (targetId == 204601) { // Brosia
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (targetId == 204601) { // Brosia
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        return sendQuestDialog(env, 1011);
                    case CHECK_USER_HAS_QUEST_ITEM: {
                        long collect1 = player.getInventory().getItemCountByItemId(186000035);
                        long collect2 = player.getInventory().getItemCountByItemId(186000036);
                        if (collect1 >= 2 && collect2 >= 5) {
                            removeQuestItem(env, 186000035, 2);
                            removeQuestItem(env, 186000036, 5);
                            return sendQuestDialog(env, 1352); // choose your reward
                        } else {
                            return sendQuestDialog(env, 1097);
                        }
                    }
                    case FINISH_DIALOG:
                        return defaultCloseDialog(env, var, var);
                    case SETPRO10: {
                        rewardGroup = 0;
                        return defaultCloseDialog(env, var, var, true, true, 0); // reward 1
                    }
                    case SETPRO20: {
                        rewardGroup = 1;
                        return defaultCloseDialog(env, var, var, true, true, 1); // reward 2
                    }
                    case SETPRO30: {
                        rewardGroup = 2;
                        return defaultCloseDialog(env, var, var, true, true, 2); // reward 3
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 204601) { // Brosia
                return sendQuestEndDialog(env, rewardGroup);
            }
        }
        return false;
    }
}
