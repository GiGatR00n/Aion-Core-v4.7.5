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
package quest.rentus_base;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Ritsu
 *
 */
public class _30551OnToRentusOutpost extends QuestHandler {

    private final static int questId = 30551;

    public _30551OnToRentusOutpost() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(799544).addOnQuestStart(questId); //Oreitia
        qe.registerQuestNpc(799544).addOnTalkEvent(questId);
        qe.registerQuestNpc(799666).addOnTalkEvent(questId); //ariana
        qe.registerOnEnterZone(ZoneName.get("RENTUS_BASE_300280000"), questId);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();
        if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
            switch (targetId) {
                case 799544: {
                    switch (dialog) {
                        case QUEST_SELECT:
                            return sendQuestDialog(env, 4762);
                        default:
                            return sendQuestStartDialog(env);
                    }
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 799666: {
                    switch (dialog) {
                        case SET_SUCCEED: {
                            return defaultCloseDialog(env, 1, 1, true, false);//reward
                        }
                    }
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            switch (targetId) {
                case 799666: {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (zoneName.equals(ZoneName.get("RENTUS_BASE_300280000"))) {
                if (var == 0) {
                    changeQuestStep(env, 0, 1, true); // 1
                    return true;
                }
            }
        }
        return false;
    }
}
