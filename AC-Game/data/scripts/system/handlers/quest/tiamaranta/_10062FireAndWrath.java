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
package quest.tiamaranta;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author vlog
 */
public class _10062FireAndWrath extends QuestHandler {

    private final static int questId = 10062;

    public _10062FireAndWrath() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnLogOut(questId);
        qe.registerOnLevelUp(questId);
        qe.addHandlerSideQuestDrop(questId, 701136, 182212556, 1, 100, 3);
        qe.registerQuestNpc(205886).addOnTalkEvent(questId);
        qe.registerQuestNpc(800018).addOnTalkEvent(questId);
        qe.registerQuestNpc(218766).addOnKillEvent(questId);
        qe.registerGetingItem(182212556, questId);
        qe.registerOnEnterZone(ZoneName.get("WRATH_VENT_600030000"), questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 205886) { // Adella
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        }
                    }
                    case SETPRO1: {
                        return defaultCloseDialog(env, 0, 1); // 1
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 800018) { // Garnon
                switch (dialog) {
                    case USE_OBJECT: {
                        if (var == 3) {
                            return sendQuestDialog(env, 10002);
                        }
                    }
                    default: {
                        removeQuestItem(env, 182212556, 1);
                        return sendQuestEndDialog(env);
                    }
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
            if (var == 1) {
                changeQuestStep(env, 1, 2, false); // 2
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        return defaultOnKillEvent(env, 218766, 2, 3); // 3
    }

    @Override
    public boolean onLogOutEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 3 && player.getInventory().getItemCountByItemId(182212556) >= 1) {
                changeQuestStep(env, 3, 3, true); // reward
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onGetItemEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 3) {
                changeQuestStep(env, 3, 3, true); // reward
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env);
    }
}
