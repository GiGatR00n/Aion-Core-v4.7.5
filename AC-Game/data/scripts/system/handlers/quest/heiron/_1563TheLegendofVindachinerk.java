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
package quest.heiron;

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
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author Gigi, Shepper
 */
public class _1563TheLegendofVindachinerk extends QuestHandler {

    private final static int questId = 1563;

    public _1563TheLegendofVindachinerk() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(798096).addOnQuestStart(questId); // Poporinerk
        qe.registerQuestNpc(279005).addOnTalkEvent(questId); // Kohrunerk
        qe.registerQuestItem(182201729, questId); // Jaiorunerk's Diary
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();
        QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 798096) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        }

        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 798096: // Poporinerk
                    switch (dialog) {
                        case QUEST_SELECT:
                            return sendQuestDialog(env, 1352);
                        case SETPRO2:
                            return checkQuestItems(env, 1, 0, true, 5, 1353);
                    }
                    break;
                case 279005:
                    switch (dialog) {
                        case QUEST_SELECT:
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            }
                        case SETPRO2:
                            return checkQuestItems(env, 1, 2, true, 6, 1439);
                    }
            }
        }
        if (sendQuestRewardDialog(env, 798096, 0, 0) || sendQuestRewardDialog(env, 279005, 0, 1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public HandlerResult onItemUseEvent(final QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        final int id = item.getItemTemplate().getTemplateId();
        final int itemObjId = item.getObjectId();

        if (id != 182201729) {
            return HandlerResult.UNKNOWN;
        }
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return HandlerResult.UNKNOWN;
        }
        PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0,
                0), true);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0,
                        1, 0), true);
                qs.setQuestVar(1);
                updateQuestStatus(env);
            }
        }, 3000);
        return HandlerResult.SUCCESS;
    }
}
