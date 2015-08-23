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
package quest.fort_tiamat;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author pralinka
 */
public class _24090FootstepsOfThePast extends QuestHandler {

    private final static int questId = 24090;

    public _24090FootstepsOfThePast() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerOnLogOut(questId);
        qe.registerOnEnterWorld(questId);
        qe.registerQuestNpc(802059).addOnTalkEvent(questId); //Protector Oriata
        qe.registerQuestNpc(730889).addOnTalkEvent(questId); //Tiamat's Remains
        qe.registerQuestNpc(802060).addOnTalkEvent(questId); //Oriata of the Past
        qe.registerQuestNpc(802061).addOnTalkEvent(questId); //Oriata of the Past
        qe.registerQuestNpc(802062).addOnTalkEvent(questId); //Israphel
        qe.registerQuestNpc(730890).addOnTalkEvent(questId); //Concentrated Ide Crystal
        qe.registerQuestItem(182215412, questId);
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
		Player player = env.getPlayer();
        PlayerClass playerClass = player.getCommonData().getPlayerClass();
            if (playerClass != PlayerClass.RIDER) {
            return false;
            }
        return defaultOnLvlUpEvent(env, 24081, true);
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
                case 802059: { //Protector Oriata
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 0) {
                                return sendQuestDialog(env, 1011);
                            }
                        }
                        case SETPRO1: {
                            giveQuestItem(env, 182215412, 1);
                            return defaultCloseDialog(env, 0, 1);
                        }
                    }
                    break;
                }
                case 730889: { //Tiamat's Remains
                    switch (dialog) {
                        case USE_OBJECT: {
                            if (var == 2) {
                                if (player.getInventory().getItemCountByItemId(182215413) == 0) {
                                    if (!giveQuestItem(env, 182215413, 1)) {
                                        return true;
                                    }
                                }
                                QuestService.addNewSpawn(300490000, player.getInstanceId(), 802060, 458f, 514f, 417f, (byte) 119);
                                Npc npc = (Npc) env.getVisibleObject();
                                npc.getController().onDelete();
                                return defaultCloseDialog(env, 2, 3);
                            }
                        }
                    }
                    break;
                }
                case 802060: { //Oriata at the past
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 3) {
                                return sendQuestDialog(env, 1693);
                            }
                        }
                        case SETPRO3: {
                            WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300500000);
                            InstanceService.registerPlayerWithInstance(newInstance, player);
                            TeleportService2.teleportTo(player, 300500000, newInstance.getInstanceId(), 224f, 251f, 125f, (byte) 10, TeleportAnimation.BEAM_ANIMATION);
                            QuestService.addNewSpawn(300500000, player.getInstanceId(), 802062, 257f, 246f, 125f, (byte) 60);
                            QuestService.addNewSpawn(300500000, player.getInstanceId(), 730890, 253f, 245f, 125f, (byte) 119);
                            Npc npc = (Npc) env.getVisibleObject();
                            npc.getController().onDelete();
                            return defaultCloseDialog(env, 3, 4);
                        }
                    }
                    break;
                }
                case 802062: { //Israphel
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 4) {
                                return sendQuestDialog(env, 2034);
                            }
                        }
                        case SETPRO4: {
                            Npc npc = (Npc) env.getVisibleObject();
                            npc.getController().onDelete();
                            return defaultCloseDialog(env, 4, 5);
                        }
                    }
                    break;
                }
                case 730890: { //Concentrated Ide Crystal
                    switch (dialog) {
                        case USE_OBJECT: {
                            if (var == 5) {
                                if (player.getInventory().getItemCountByItemId(182215414) == 0) {
                                    if (!giveQuestItem(env, 182215414, 1)) {
                                        return true;
                                    }
                                }
                                QuestService.addNewSpawn(300500000, player.getInstanceId(), 802061, 255f, 245f, 125f, (byte) 55);
                                Npc npc = (Npc) env.getVisibleObject();
                                npc.getController().onDelete();
                                return defaultCloseDialog(env, 5, 5, true, false);
                            }
                        }
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 802061) {
                playQuestMovie(env, 892);
                removeQuestItem(env, 182215413, 1);
                removeQuestItem(env, 182215414, 1);
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }

    @Override
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (player.isInsideZone(ZoneName.get("LDF4b_ITEMUSEAREA_Q24090")) && item.getItemId() == 182215412) {
                WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300490000);
                InstanceService.registerPlayerWithInstance(newInstance, player);
                TeleportService2.teleportTo(player, 300490000, newInstance.getInstanceId(), 549f, 525f, 417f, (byte) 10, TeleportAnimation.BEAM_ANIMATION);

                return HandlerResult.fromBoolean(useQuestItem(env, item, 1, 2, false));
            }
        }
        return HandlerResult.FAILED;
    }

    @Override
    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (player.getWorldId() == 300490000) {
                if (var == 2) {
                    QuestService.addNewSpawn(300490000, player.getInstanceId(), 730889, 446f, 513f, 418f, (byte) 119);
                }
            }
        }
        return false;
    }

    @Override
    public boolean onLogOutEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var <= 5) {
                qs.setQuestVar(0);
                updateQuestStatus(env);
                return true;
            }
        }
        return false;
    }
}
