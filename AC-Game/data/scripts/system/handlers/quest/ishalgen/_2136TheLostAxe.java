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
package quest.ishalgen;

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

/**
 * @author Rhys2002
 * @modified Hellboy
 */
public class _2136TheLostAxe extends QuestHandler {

    private final static int questId = 2136;
    private final static int[] npc_ids = {700146, 790009};

    public _2136TheLostAxe() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestItem(182203130, questId);
        for (int npc_id : npc_ids) {
            qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onDialogEvent(final QuestEnv env) {
        final Player player = env.getPlayer();
        int targetId = 0;
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (env.getDialogId() == DialogAction.QUEST_ACCEPT_1.id()) {
                QuestService.startQuest(env);
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
                return true;
            } else {
                PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
            }
        }

        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 790009) {
                final Npc npc = (Npc) env.getVisibleObject();
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        npc.getController().onDelete();
                    }
                }, 10000);
                return sendQuestEndDialog(env);
            }
        } else if (qs.getStatus() != QuestStatus.START) {
            return false;
        }

        if (targetId == 790009) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 1) {
                        return sendQuestDialog(env, 1011);
                    }
                case SETPRO1:
                    if (var == 1) {
                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(env);
                        removeQuestItem(env, 182203130, 1);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                        return sendQuestDialog(env, 6);
                    }
                case SETPRO2:
                    if (var == 1) {
                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(env);
                        removeQuestItem(env, 182203130, 1);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                        return sendQuestDialog(env, 5);
                    }
            }
        } else if (targetId == 700146) {
            switch (env.getDialog()) {
                case USE_OBJECT:
                    if (var == 0) {
                        playQuestMovie(env, 59);
                        qs.setQuestVarById(0, 1);
                        updateQuestStatus(env);
                        QuestService.addNewSpawn(220010000, player.getInstanceId(), 790009, 1088.5f, 2371.8f, 258.375f, (byte) 87);
                        return true;
                    }
            }
        }
        return false;
    }

    @Override
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        final int id = item.getItemTemplate().getTemplateId();
        final int itemObjId = item.getObjectId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (id != 182203130) {
            return HandlerResult.UNKNOWN;
        }
        PacketSendUtility.broadcastPacket(player,
                new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (QuestService.startQuest(env)) {
                return HandlerResult.fromBoolean(sendQuestDialog(env, 4));
            }
        }
        return HandlerResult.SUCCESS;
    }
}
