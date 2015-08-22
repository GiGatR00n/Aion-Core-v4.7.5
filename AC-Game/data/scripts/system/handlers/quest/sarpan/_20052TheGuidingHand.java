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
package quest.sarpan;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author vlog
 */
public class _20052TheGuidingHand extends QuestHandler {

    private static final int questId = 20052;

    public _20052TheGuidingHand() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcIds = {205987, 205788, 730474, 205988, 205617};
        for (int npcId : npcIds) {
            qe.registerQuestNpc(npcId).addOnTalkEvent(questId);
        }
        qe.registerQuestNpc(218100).addOnKillEvent(questId);
        qe.registerOnEnterZone(ZoneName.get("SARPAN_CAPITOL_600020000"), questId);
        qe.registerOnEnterZone(ZoneName.get("DEBARIM_PETRALITH_STUDIO_600020000"), questId);
        qe.registerOnLevelUp(questId);
        qe.registerOnEnterZoneMissionEnd(questId);
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 20051, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            switch (targetId) {
                case 205987: { // Garnon
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            } else if (var == 4) {
                                return sendQuestDialog(env, 2375);
                            }
                        }
                        case SETPRO2: {
                            return defaultCloseDialog(env, 1, 2); // 2
                        }
                        case SETPRO5: {
                            TeleportService2.teleportTo(player, 600020000, 951f, 2219f, 533f);
                            return defaultCloseDialog(env, 4, 5); // 5
                        }
                    }
                    break;
                }
                case 205788: { // Nytia
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
                    break;
                }
                case 730474: { // Zayedan's Record
                    switch (dialog) {
                        case USE_OBJECT: {
                            if (var == 6) {
                                return sendQuestDialog(env, 3057);
                            }
                        }
                        case SETPRO7: {
                            changeQuestStep(env, 6, 7, false); // 7
                            return closeDialogWindow(env);
                        }
                    }
                    break;
                }
                case 205988: { // Ispharel
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 7) {
                                return sendQuestDialog(env, 3398);
                            }
                        }
                        case SET_SUCCEED: {
                            return defaultCloseDialog(env, 7, 7, true, false); // reward
                        }
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205617) { // Aimah
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
            if (zoneName.equals(ZoneName.get("SARPAN_CAPITOL_600020000"))) {
                if (var == 0) {
                    changeQuestStep(env, 0, 1, false); // 1
                    playQuestMovie(env, 704);
                    return true;
                }
            } else if (zoneName.equals(ZoneName.get("DEBARIM_PETRALITH_STUDIO_600020000"))) {
                if (var == 5) {
                    changeQuestStep(env, 5, 6, false); // 6
                    playQuestMovie(env, 706);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        return defaultOnKillEvent(env, 218100, 3, 4); // 4
    }
}
