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
package quest.inggison;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author zhkchi
 *
 */
public class _11289VeillesGift extends QuestHandler {

    private final static int questId = 11289;

    public _11289VeillesGift() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(799038).addOnTalkEvent(questId);
        qe.registerQuestItem(182213147, questId);
    }

    @Override
    public boolean onDialogEvent(final QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();
        QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (dialog == DialogAction.QUEST_SELECT) {
                return sendQuestDialog(env, 4);
            } else {
                return sendQuestStartDialog(env);
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (targetId == 799038) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (checkItemExistence(env, 182213147, 1, true)) {
                        changeQuestStep(env, 0, 0, true);
                        return sendQuestDialog(env, 2375);
                    }
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799038) {
                switch (dialog) {
                    case USE_OBJECT: {
                        return sendQuestDialog(env, 2375);
                    }
                    case SELECT_QUEST_REWARD: {
                        return sendQuestDialog(env, 5);
                    }
                    default: {
                        return sendQuestEndDialog(env);
                    }
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

        if (id != 182213147) {
            return HandlerResult.UNKNOWN;
        }
        PacketSendUtility.broadcastPacket(player,
                new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            sendQuestDialog(env, 4);
        }
        return HandlerResult.SUCCESS;
    }
}
