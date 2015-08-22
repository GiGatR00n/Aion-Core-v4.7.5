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
package quest.raksang;

import com.aionemu.gameserver.model.gameobjects.Item;
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

public class _28708Hard_Evidence extends QuestHandler {

    private final static int questId = 28708;

    public _28708Hard_Evidence() {
        super(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);

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
        if (qs.getStatus() == QuestStatus.START) {
            if (env.getTargetId() == 799435) {//Ein.
                switch (env.getDialogId()) {
                    case 26:
                        if (var == 0) {
                            return sendQuestDialog(env, 2375);
                        }
                    case 1009:
                        player.getInventory().decreaseByItemId(182212213, 1); //Sealing Stone.
                        return defaultCloseDialog(env, 0, 1, true, true);
                }
            }
        }
        return defaultCloseDialog(env, 799435, 2375); //Ein.
    }

    @Override
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        final int id = item.getItemTemplate().getTemplateId();
        final int itemObjId = item.getObjectId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (id != 182212206) {
            return HandlerResult.UNKNOWN;
        }
        PacketSendUtility.broadcastPacket(player,
                new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            sendQuestDialog(env, 4);
        }
        return HandlerResult.SUCCESS;
    }

    @Override
    public void register() {
        qe.registerQuestNpc(799435).addOnTalkEvent(questId); //Ein.
        qe.registerQuestItem(182212213, questId);
    }
}
