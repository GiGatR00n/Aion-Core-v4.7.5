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
package quest.katalam;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

//By Evil_dnk

public class _13030CombatMechanic extends QuestHandler {

	private final static int questId = 13030;

	public _13030CombatMechanic() {
		super(questId);
	}

	public void register() {
		qe.registerQuestNpc(801097).addOnQuestStart(questId);
		qe.registerQuestNpc(801097).addOnTalkEvent(questId);
		qe.registerQuestNpc(701685).addOnTalkEvent(questId);
		qe.registerQuestNpc(701686).addOnTalkEvent(questId);
		qe.registerQuestNpc(701687).addOnTalkEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = env.getTargetId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 801097) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                }
                else {
                    return sendQuestStartDialog(env);
                }
            }
        }
        else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 701685 || targetId == 701686 || targetId == 701687)
            {
                 final Npc npc = (Npc)env.getVisibleObject();
                 if (qs.getQuestVarById(0) < 2){
                 changeQuestStep(env, qs.getQuestVarById(0), qs.getQuestVarById(0)+1,false);
                 npc.getController().die();
                     return closeDialogWindow(env);
                 }
                 if (qs.getQuestVarById(0) == 2) {
                     changeQuestStep(env, 2,3, true);
                 return closeDialogWindow(env);
                 }

             }
        }
        else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 801097) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

}