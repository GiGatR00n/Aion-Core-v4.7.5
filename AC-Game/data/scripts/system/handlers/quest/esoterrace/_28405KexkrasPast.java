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
package quest.esoterrace;

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
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author Vincas
 */
public class _28405KexkrasPast extends QuestHandler {

    public static final int questId = 28405;
    public static final int npcLuigur = 799558, npcRelyt = 799557;

    public _28405KexkrasPast() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(npcLuigur).addOnTalkEvent(questId);
        qe.registerQuestNpc(npcRelyt).addOnTalkEvent(questId);
        qe.registerQuestItem(182215014, questId);
    }

    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();

        if (env.getTargetId() == 0 && env.getDialog() == DialogAction.QUEST_ACCEPT_1) {
            QuestService.startQuest(env);
            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(0, 0));
            return true;
        }

        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        if (qs.getStatus() == QuestStatus.START) {
            switch (env.getTargetId()) {
                case npcLuigur:
                    if (qs.getQuestVarById(0) == 0) {
                        if (env.getDialog() == DialogAction.QUEST_SELECT) {
                            return sendQuestDialog(env, 1352);
                        } else if (env.getDialog() == DialogAction.SETPRO1) {
                            return defaultCloseDialog(env, 0, 1, 182215025, 1, 182215014, 1);
                        }
                    }
                case npcRelyt:
                    if (qs.getQuestVarById(0) == 1) {
                        if (env.getDialog() == DialogAction.QUEST_SELECT) {
                            return sendQuestDialog(env, 2375);
                        } else if (env.getDialog() == DialogAction.SELECT_QUEST_REWARD) {
                            removeQuestItem(env, 182215025, 1);
                        }
                        return defaultCloseDialog(env, 1, 2, true, true);
                    }
            }
        }
        return sendQuestRewardDialog(env, npcRelyt, 0);
    }

    @Override
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        final int id = item.getItemTemplate().getTemplateId();
        final int itemObjId = item.getObjectId();

        if (id != 182215014) {
            return HandlerResult.FAILED;
        }
        PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0,
                0), true);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0,
                        1, 0), true);
                sendQuestDialog(env, 4);
            }
        }, 3000);
        return HandlerResult.SUCCESS;
    }
}
