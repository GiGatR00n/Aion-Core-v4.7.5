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
package quest.greater_stigma;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Gigi
 */
public class _30217GroupStigmasScars extends QuestHandler {

    private final static int questId = 30217;

    public _30217GroupStigmasScars() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(798909).addOnQuestStart(questId);
        qe.registerQuestNpc(798909).addOnTalkEvent(questId);
        qe.registerQuestNpc(799506).addOnTalkEvent(questId);
        qe.registerQuestNpc(798941).addOnTalkEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);

        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 798909) {
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

        if (qs != null && qs.getStatus() == QuestStatus.START) {
            switch (targetId) {
                case 798941: {
                    switch (env.getDialog()) {
                        case QUEST_SELECT:
                            if (var == 0 && qs != null) {
                                return sendQuestDialog(env, 1011);
                            }
                        case SETPRO1:
                            QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 799506, player.getX(),
                                    player.getY(), player.getZ(), player.getHeading());
                            qs.setQuestVarById(0, 1);
                            updateQuestStatus(env);
                            PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                            return true;
                    }
                }
                case 798909:
                    switch (env.getDialog()) {
                        case QUEST_SELECT:
                            long itemCount1 = player.getInventory().getItemCountByItemId(182209618);
                            long itemCount2 = player.getInventory().getItemCountByItemId(182209619);
                            if (var == 2) {
                                if (itemCount1 > 0 && itemCount2 > 0) {
                                    removeQuestItem(env, 182209618, 1);
                                    removeQuestItem(env, 182209619, 1);
                                    qs.setStatus(QuestStatus.REWARD);
                                    updateQuestStatus(env);
                                    return sendQuestDialog(env, 1693);
                                } else {
                                    return sendQuestDialog(env, 10001);
                                }
                            }
                    }
                case 799506:
                    switch (env.getDialog()) {
                        case QUEST_SELECT:
                            if (var == 1) {
                                return sendQuestDialog(env, 1352);
                            }
                        case SETPRO2:
                            env.getVisibleObject().getController().onDelete();
                            qs.setQuestVarById(0, 2);
                            updateQuestStatus(env);
                            return true;
                    }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 798909) {
                if (env.getDialogId() == DialogAction.CHECK_USER_HAS_QUEST_ITEM.id()) {
                    return sendQuestDialog(env, 5);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }
}
