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

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Rhys2002
 */
public class _1014OdiumintheDukakiSettlement extends QuestHandler {

    private final static int questId = 1014;
    private final static int[] npc_ids = {203129, 730020, 203098, 700090};
    private final static int[] mob_ids = {210145, 210146, 210174, 210739};

    public _1014OdiumintheDukakiSettlement() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        qe.registerQuestItem(182200012, questId);
        for (int mob_id : mob_ids) {
            qe.registerQuestNpc(mob_id).addOnKillEvent(questId);
        }
        for (int npc_id : npc_ids) {
            qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 1130, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203098) {
                return sendQuestEndDialog(env);
            }
        } else if (qs.getStatus() != QuestStatus.START) {
            return false;
        }
        if (targetId == 203129) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 0) {
                        return sendQuestDialog(env, 1011);
                    } else if (var == 10) {
                        return sendQuestDialog(env, 1352);
                    } else if (var == 14) {
                        return sendQuestDialog(env, 1693);
                    }
                    return false;

                case SELECT_ACTION_1013:
                    if (var == 0) {
                        playQuestMovie(env, 26);
                        return false;
                    }

                case SETPRO1:
                    if (var == 0) {
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                    }

                case SETPRO2:
                    if (var == 10) {
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                    }

                case SETPRO3:
                    if (var == 14) {
                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                    }

                    return false;
            }
        } else if (targetId == 730020) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 1) {
                        return sendQuestDialog(env, 1352);
                    }
                    return false;

                case SETPRO2:
                    if (var == 1) {
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                    }
                    return false;
            }
        } else if (targetId == 700090) {
            if (var == 11 && env.getDialog() == DialogAction.USE_OBJECT) {
                if (player.getInventory().getItemCountByItemId(182200011) == 0) {
                    return false;
                }
                QuestService.addNewSpawn(210030000, 1, 210739, (float) 757.7, (float) 2477.2, (float) 217.4, (byte) 0);
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

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs.getStatus() != QuestStatus.START) {
            return false;
        }
        if ((targetId == 210145 && qs.getQuestVarById(0) < 10) || (targetId == 210146 && qs.getQuestVarById(0) < 10)) {
            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            updateQuestStatus(env);
        }
        return false;
    }

    @Override
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        final int id = item.getItemTemplate().getTemplateId();
        final int itemObjId = item.getObjectId();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (id != 182200012 || qs.getQuestVarById(0) != 11) {
            return HandlerResult.UNKNOWN;
        }
        if (!player.isInsideZone(ZoneName.get("ODIUM_REFINING_CAULDRON_210030000"))) {
            return HandlerResult.UNKNOWN;
        }

        PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0,
                0), true);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                playQuestMovie(env, 172);
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0,
                        1, 0), true);
                removeQuestItem(env, 182200012, 1);
                removeQuestItem(env, 182200011, 1);
                qs.setQuestVarById(0, 14);
                updateQuestStatus(env);
            }
        }, 3000);
        return HandlerResult.SUCCESS;
    }
}
