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
package quest.katalam;

import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

//By Evil_dnk

public class _23562BureaucracyInaction extends QuestHandler {

    private final static int questId = 23562;

    public _23562BureaucracyInaction() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(801144).addOnQuestStart(questId);
        qe.registerQuestNpc(801140).addOnTalkEvent(questId);
        qe.registerQuestNpc(801141).addOnTalkEvent(questId);
        qe.registerQuestNpc(800959).addOnTalkEvent(questId);
        qe.registerQuestItem(182213478, questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();
        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 801144) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 1011);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        }

        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (targetId == 801140) {
                if (dialog == DialogAction.QUEST_SELECT)
                   {
                        return sendQuestDialog(env, 1352);
                    }
                if (dialog == DialogAction.SETPRO1)
                   {
                        return defaultCloseDialog(env, 0, 1);
                    }
                }
            if (targetId == 801141) {
                if (dialog == DialogAction.QUEST_SELECT)
                {
                    return sendQuestDialog(env, 1693);
                }
                if (dialog == DialogAction.SETPRO2)
                {
                    giveQuestItem(env, 182213478, 1);
                    return defaultCloseDialog(env, 1, 2);
                }
            }
            if (targetId == 800959) {
                if (dialog == DialogAction.QUEST_SELECT)
                {
                    return sendQuestDialog(env, 2375);
                }
                if (dialog == DialogAction.SELECT_QUEST_REWARD)
                {
                    return defaultCloseDialog(env, 3, 3, true, true);
                }
            }
        }
        else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 800959) {
                return sendQuestEndDialog(env);
            }
        }
        return false;
    }


    @Override
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        final int id = item.getItemTemplate().getTemplateId();
        final int itemObjId = item.getObjectId();
        QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (id != 182213478)
            return HandlerResult.UNKNOWN;

        if (qs == null || qs.getStatus() != QuestStatus.START)
            return HandlerResult.UNKNOWN;

        if (qs.getQuestVarById(0) != 2)
            return HandlerResult.UNKNOWN;

        PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0,
                0), true);
        ThreadPoolManager.getInstance().schedule(new Runnable() {

            @Override
            public void run() {
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0,
                        1, 0), true);
                changeQuestStep(env, 2, 3, false);
            }
        }, 3000);
        return HandlerResult.SUCCESS;
    }

}