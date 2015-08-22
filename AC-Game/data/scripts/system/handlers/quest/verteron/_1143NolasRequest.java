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
package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Jack85
 */
public class _1143NolasRequest extends QuestHandler {

    private final static int questId = 1143;
	private final static int npcId = 730001;
	 private final static int[] monsterId0 = {210077, 210698};
	 
    public _1143NolasRequest() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(npcId).addOnQuestStart(questId);
        qe.registerQuestNpc(npcId).addOnTalkEvent(questId);
		for (int monsterId : monsterId0) {
            qe.registerQuestNpc(monsterId).addOnKillEvent(questId);
        }
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(questId);
    int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == npcId) { 
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else {
                    return sendQuestStartDialog(env);
                }
            }		
		} else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == npcId) {
				if (env.getDialog() == DialogAction.QUEST_SELECT && qs.getQuestVarById(0) == 10) {
                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    qs.setStatus(QuestStatus.REWARD);
                    updateQuestStatus(env);
                    return sendQuestDialog(env, 1352);
                } else {
                    return sendQuestStartDialog(env);
                }
			}
        }else if (qs.getStatus() == QuestStatus.REWARD) {
			if (targetId == npcId) {
				if (env.getDialog() == DialogAction.USE_OBJECT) {
					return sendQuestDialog(env, 5);
				} else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id()) {
					return sendQuestDialog(env, 5);
				} else {
					return sendQuestEndDialog(env);
				}
			}
		}
        return false;
    }
        
    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
		int targetId = 0;
		
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }       

        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
		
		 switch (targetId) {
            case 210698:
            case 210077:
                if (qs.getQuestVarById(0) < 10) {
                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    updateQuestStatus(env);
                    return true;
                }
                break;
		 }
        return false;
    }
}
