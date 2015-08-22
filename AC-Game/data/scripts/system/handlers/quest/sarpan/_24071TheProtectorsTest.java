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

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author pralinka
 */
public class _24071TheProtectorsTest extends QuestHandler {

    private final static int questId = 24071;

    public _24071TheProtectorsTest() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerOnEnterWorld(questId);
        qe.registerQuestItem(182215401, questId);
        qe.registerQuestItem(182215402, questId);
        int[] npcs = {205585, 205987, 802058, 205754, 702088, 205743, 205756};
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
        int[] mobs = {217912, 217913, 217914, 218098, 218100, 218578};
        for (int mob : mobs) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        }
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 24070, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);

        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();
        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 205585: { //rafael
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            }
                        }
                        case SETPRO1: {
                            return defaultCloseDialog(env, 0, 1);
                        }
                    }
                    break;
                }
                case 205987: { //garnon
                    int var3 = qs.getQuestVarById(3);
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            } else if (var == 7 && var3 == 6) {
                                return sendQuestDialog(env, 3398);
                            }
                        }
                        case SETPRO2: {
                            TeleportService2.teleportTo(player, 600020000, 2652f, 1102f, 201f);
                            return defaultCloseDialog(env, 1, 2);
                        }
                        case SETPRO8: {
                            TeleportService2.teleportTo(player, 600020000, 2652f, 1102f, 201f);
                            return defaultCloseDialog(env, 7, 8);
                        }
                    }
                    break;
                }
                case 802058: { //oriata
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 2) {
                                playQuestMovie(env, 889);
                                return sendQuestDialog(env, 1693);
                            } else if (var == 8) {
                                return sendQuestDialog(env, 3740);
                            }
                        }
                        case SETPRO3: {
                            return defaultCloseDialog(env, 2, 3);
                        }
                        case SETPRO9: {
                            giveQuestItem(env, 182215402, 1);
                            return defaultCloseDialog(env, 8, 9);
                        }
                    }
                    break;
                }
                case 205754: { //hatiel
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 3) {
                                return sendQuestDialog(env, 2034);
                            }
                        }
                        case SETPRO4: {
                            return defaultCloseDialog(env, 3, 4);
                        }
                    }
                    break;
                }
                case 702088: {
                    return true;
                }
                case 205743: { //manyos
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 4) {
                                return sendQuestDialog(env, 2375);
                            } else if (var == 5) {
                                return sendQuestDialog(env, 2716);
                            }
                        }
                        case CHECK_USER_HAS_QUEST_ITEM: {
                            return checkQuestItems(env, 4, 5, false, 10000, 10001);
                        }
                        case SETPRO6: {
                            return defaultCloseDialog(env, 5, 6);
                        }
                    }
                    break;
                }
                case 205756: { //sutton
                    int var1 = qs.getQuestVarById(1);
                    int var2 = qs.getQuestVarById(2);
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 6 && var1 == 5 && var2 == 3) {
                                return sendQuestDialog(env, 3057);
                            }
                        }
                        case SETPRO7: {
                            return defaultCloseDialog(env, 6, 7);
                        }
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 802058) { // oriata
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null || qs.getStatus() != QuestStatus.START) {
            return false;
        }
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        int var = qs.getQuestVarById(0);
        if (targetId == 217912 || targetId == 217913) {
            if (var == 6) {
                switch (qs.getQuestVarById(1)) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4: {
                        qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
                        updateQuestStatus(env);
                        return true;
                    }
                }
            }
        } else if (targetId == 217914) {
            if (var == 6) {
                switch (qs.getQuestVarById(2)) {
                    case 0:
                    case 1:
                    case 2: {
                        qs.setQuestVarById(2, qs.getQuestVarById(2) + 1);
                        updateQuestStatus(env);
                        return true;
                    }
                }
            }
        } else if (targetId == 218098 || targetId == 218100 || targetId == 218578) {
            if (var == 7) {
                switch (qs.getQuestVarById(3)) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5: {
                        qs.setQuestVarById(3, qs.getQuestVarById(3) + 1);
                        updateQuestStatus(env);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        Player player = env.getPlayer();
        if (player.isInsideZone(ZoneName.get("IDLDF4A_ItemUseArea_Q14071"))) {
            return HandlerResult.fromBoolean(useQuestItem(env, item, 9, 9, true, 890)); // 9
        }
        return HandlerResult.FAILED;
    }

    @Override
    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (player.getWorldId() == 300330000) {
                if (var == 2) {
                    QuestService.addNewSpawn(300330000, player.getInstanceId(), 702089, (float) 250.331, (float) 245.210, (float) 126.270, (byte) 60);
                    QuestService.addNewSpawn(300330000, player.getInstanceId(), 802058, (float) 249.11, (float) 248.15, (float) 125.06, (byte) 70);
                } else if (var == 8 || var == 9) {
                    QuestService.addNewSpawn(300330000, player.getInstanceId(), 802058, (float) 249.11, (float) 248.15, (float) 125.06, (byte) 72);
                    QuestService.addNewSpawn(300330000, player.getInstanceId(), 702089, (float) 250.331, (float) 245.210, (float) 126.270, (byte) 60);
                }
            }
        }
        return false;
    }
}
