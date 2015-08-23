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
package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author pralinka
 */
public class _14025CookingUpDisasters extends QuestHandler {

    private final static int questId = 14025;
    private final static int[] npcs = {203989, 204020, 203901};
    private final static int[] mobs = {212025, 212029, 212039, 212351,};

    public _14025CookingUpDisasters() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
        for (int mob : mobs) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        }
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env, 14024);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        int[] quests = {14020, 14024};
        return defaultOnLvlUpEvent(env, quests, true);
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 5) {
                int[] kaidan = {212025, 212029, 212039};
                int[] kalabar = {212351};
                switch (targetId) {
                    case 212025:
                    case 212029:
                    case 212039: {
                        return defaultOnKillEvent(env, kaidan, 0, 4, 1); // 1: 4x
                    }
                    case 212351: {
                        return defaultOnKillEvent(env, kalabar, 0, 1, 2); // 2: 1x
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203901) // Telemachus			
            {
                return sendQuestEndDialog(env);
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            int var1 = qs.getQuestVarById(1);
            int var2 = qs.getQuestVarById(2);
            if (targetId == 203989) { // Tumblusen
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        } else if (var == 1) {
                            return sendQuestDialog(env, 1352);
                        } else if (var == 4) {
                            return sendQuestDialog(env, 2034);
                        } else if (var == 5 && var1 == 4 && var2 == 1) {
                            return sendQuestDialog(env, 2716);
                        }
                    case CHECK_USER_HAS_QUEST_ITEM:
                        if (var == 1) {
                            if (QuestService.collectItemCheck(env, true)) {
                                qs.setQuestVarById(0, var + 2);
                                updateQuestStatus(env);
                                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                                return true;
                            }
                            return false;
                        }
                    case SETPRO1:
                        if (var == 0) {
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(env);
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                            return true;
                        }
                        return false;
                    case SETPRO4:
                        playQuestMovie(env, 36);
                        removeQuestItem(env, 182201005, 1);
                        return defaultCloseDialog(env, 4, 5);
                    case SETPRO6:
                        return defaultCloseDialog(env, 5, 5, true, false);
                    case FINISH_DIALOG: {
                        return closeDialogWindow(env);
                    }
                }
            } else if (targetId == 204020) { // Mabangtah
                switch (env.getDialog()) {
                    case QUEST_SELECT:
                        if (var == 3) {
                            return sendQuestDialog(env, 1693);
                        }
                    case SETPRO3: {
                        giveQuestItem(env, 182201005, 1);
                        return defaultCloseDialog(env, 3, 4);
                    }
                }
            }
        }
        return false;
    }
}
