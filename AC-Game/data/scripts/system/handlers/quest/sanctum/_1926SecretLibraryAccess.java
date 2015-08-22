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
package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapType;

/**
 * @author xaerolt, Rolandas
 */
public class _1926SecretLibraryAccess extends QuestHandler {

    private final static int questId = 1926;
    private final static int[] npc_ids = {203894, 203098};

    public _1926SecretLibraryAccess() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(203894).addOnQuestStart(questId);
        for (int npc_id : npc_ids) {
            qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
        }
    }

    private boolean AreVerteronQuestsFinished(Player player) {
        QuestState qs = player.getQuestStateList().getQuestState(1020);
        return ((qs == null) || (qs.getStatus() != QuestStatus.COMPLETE && qs.getStatus() != QuestStatus.NONE)) ? false
                : true;
    }

    private boolean AreAethertechQuestsFinished(Player player) {
        QuestState qs = player.getQuestStateList().getQuestState(14016);
        return ((qs == null) || (qs.getStatus() != QuestStatus.COMPLETE && qs.getStatus() != QuestStatus.NONE)) ? false
                : true;
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc) {
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        }
        final QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (targetId == 203894) {
            if (qs == null || qs.getStatus() == QuestStatus.NONE) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env);
                }
            } else if (qs.getStatus() == QuestStatus.REWARD && qs.getQuestVarById(0) == 0) {
                if (env.getDialog() == DialogAction.USE_OBJECT && qs.getStatus() == QuestStatus.REWARD) {
                    return sendQuestDialog(env, 10002);
                } else if (env.getDialogId() == DialogAction.SELECTED_QUEST_NOREWARD.id()) {
                    removeQuestItem(env, 182206022, 1);
                    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
                    updateQuestStatus(env);
                    return sendQuestEndDialog(env);
                } else if (env.getDialogId() == DialogAction.SELECT_QUEST_REWARD.id()) {
                    return sendQuestEndDialog(env);
                }
            } else if (qs.getStatus() == QuestStatus.COMPLETE) {
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        TeleportService2.teleportTo(player, WorldMapType.SANCTUM.getId(), 2032.9f, 1473.1f, 592.2f, (byte) 195);
                    }
                }, 3000);
            }
        } else if (targetId == 203098) {
            if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
                if (env.getDialog() == DialogAction.QUEST_SELECT) {
                    if (AreVerteronQuestsFinished(player)) {
                        return sendQuestDialog(env, 1011);
                    } else if (AreAethertechQuestsFinished(player)) {
                        return sendQuestDialog(env, 1011);
                    } else {
                        return sendQuestDialog(env, 1097);
                    }
                } else if (env.getDialogId() == DialogAction.SET_SUCCEED.id()) {
                    if (giveQuestItem(env, 182206022, 1)) {
                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(env);
                    }
                    PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
                    return true;
                } else {
                    return sendQuestStartDialog(env);
                }
            }
        }
        return false;
    }
}
