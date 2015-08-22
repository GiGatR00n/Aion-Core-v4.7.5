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
 * @author maddison
 */
public class _30502ConquertheVasharti extends QuestHandler {

    private static final int questId = 30502;

    public _30502ConquertheVasharti() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(799666).addOnQuestStart(questId);
        qe.registerQuestNpc(217307).addOnKillEvent(questId);
        qe.registerQuestNpc(217308).addOnKillEvent(questId);
        qe.registerQuestNpc(217313).addOnKillEvent(questId);
        qe.registerQuestNpc(217310).addOnKillEvent(questId);
        qe.registerQuestNpc(799670).addOnTalkEvent(questId);
        qe.registerQuestNpc(799544).addOnTalkEvent(questId);
        qe.registerOnEnterZone(ZoneName.get("SPARRING_GROUNDS_300280000"), questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
            if (targetId == 799666) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        return sendQuestDialog(env, 4762);
                    }
                    default:
                        return sendQuestStartDialog(env);
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (targetId == 799670) {
                switch (dialog) {
                    case USE_OBJECT: {
                        return sendQuestDialog(env, 2716);
                    }
                    case SET_SUCCEED:
                        return defaultCloseDialog(env, 5, 5, true, false);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799544) {
                if (env.getDialog() == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onEnterZoneEvent(QuestEnv env, ZoneName zoneName) {
        if (zoneName == ZoneName.get("SPARRING_GROUNDS_300280000")) {
            Player player = env.getPlayer();
            if (player == null) {
                return false;
            }
            QuestState qs = player.getQuestStateList().getQuestState(questId);
            if (qs != null && qs.getStatus() == QuestStatus.START) {
                int var = qs.getQuestVarById(0);
                if (var == 0) {
                    changeQuestStep(env, 0, 1, false);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);
        int targetId = env.getTargetId();

        if (qs.getStatus() != QuestStatus.START) {
            return false;
        }

        if (var == 1) {
            if (targetId == 217307) {
                qs.setQuestVarById(1, 1);
            } else if (targetId == 217308) {
                qs.setQuestVarById(2, 1);
            }
            updateQuestStatus(env);
            if (qs.getQuestVarById(1) == 1 && qs.getQuestVarById(2) == 1) {
                changeQuestStep(env, 1, 2, false);
            }
        } else if (var == 2) {
            if (targetId == 217310) {
                changeQuestStep(env, 2, 3, false);
                updateQuestStatus(env);
            }
        } else if (var == 3) {
            if (targetId == 217313) {
                changeQuestStep(env, 3, 5, false);
                updateQuestStatus(env);
            }
        }
        return false;
    }
}
