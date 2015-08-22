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

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Luzien
 */
public class _10060TheHeartOfTiamat extends QuestHandler {

    private final static int questId = 10060;

    public _10060TheHeartOfTiamat() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnLevelUp(questId);
        qe.registerQuestItem(182212555, questId);
        qe.registerQuestNpc(205842).addOnTalkEvent(questId);
        qe.registerQuestNpc(800018).addOnTalkEvent(questId);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        int var = qs.getQuestVarById(0);
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();

        if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 205842) {
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
            } else if (targetId == 800018) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 1) {
                            return sendQuestDialog(env, 1352);
                        } else if (player.getInventory().getItemCountByItemId(182212591) == 1) {
                            return sendQuestDialog(env, 1693);
                        }
                    }
                    case CHECK_USER_HAS_QUEST_ITEM: {
                        if (QuestService.collectItemCheck(env, true)) {
                            if (!giveQuestItem(env, 182212555, 1)) {
                                return true;
                            }
                            qs.setQuestVarById(0, var + 1);
                            updateQuestStatus(env);
                            return sendQuestDialog(env, 10000);
                        }
                    }
                    case SETPRO2: {
                        return defaultCloseDialog(env, 1, 2);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 800018) {
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
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (player.isInsideZone(ZoneName.get("LDF4B_ITEMUSEAREA_Q10060A")) && item.getItemId() == 182212555) {
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 701119, player.getX(), player.getY(), player.getZ(), (byte) 100);
                        QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 218822, player.getX() + 3, player.getY() - 3, player.getZ(), (byte) 100);
                        QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 218822, player.getX() - 3, player.getY() + 3, player.getZ(), (byte) 100);
                    }
                }, 3000);
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        for (Npc npc : World.getInstance().getNpcs()) {
                            if (npc.getNpcId() == 701119) {
                                npc.getController().onDelete();
                            }
                        }
                    }
                }, 30000);
                return HandlerResult.fromBoolean(useQuestItem(env, item, 3, 4, true));
            }
        }
        return HandlerResult.FAILED;
    }
}
