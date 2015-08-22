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
public class _10063ShotThroughTheHeart extends QuestHandler {

    private final static int questId = 10063;

    public _10063ShotThroughTheHeart() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {800018, 800065, 205886, 701237};
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
        qe.registerOnEnterZone(ZoneName.get("HEART_OF_PETRIFICATION_ENTRANCE_600030000"), questId);
        qe.registerOnLevelUp(questId);
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
            if (targetId == 800018) { // Garnon
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        } else if (var == 3) {
                            return sendQuestDialog(env, 2034);
                        }
                    }
                    case SETPRO1: {
                        return defaultCloseDialog(env, 0, 1); // 1
                    }
                    case CHECK_USER_HAS_QUEST_ITEM: {
                        return checkQuestItems(env, 3, 3, true, 10000, 10001); // reward
                    }
                    case FINISH_DIALOG: {
                        return closeDialogWindow(env);
                    }
                }
            } else if (targetId == 800065) { // Betri
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 2) {
                            return sendQuestDialog(env, 1693);
                        }
                    }
                    case SETPRO3: {
                        return defaultCloseDialog(env, 2, 3); // 3
                    }
                }
            } else if (targetId == 701237) {
                switch (dialog) {
                    case USE_OBJECT: {
                        if (var == 3) {
                            if (!giveQuestItem(env, 182212557, 1)) {
                                return true;
                            }
                        }
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205886) { // Adella
                if (dialog == DialogAction.USE_OBJECT) {
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
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env);
    }
}
