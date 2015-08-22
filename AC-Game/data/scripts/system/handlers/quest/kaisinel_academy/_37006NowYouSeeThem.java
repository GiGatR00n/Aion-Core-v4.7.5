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
package quest.kaisinel_academy;

import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import static com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE.*;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Cheatkiller
 *
 */
public class _37006NowYouSeeThem extends QuestHandler {

    private final static int questId = 37006;

    public _37006NowYouSeeThem() {
        super(questId);
    }

    public void register() {
        qe.registerQuestNpc(700969).addOnTalkEvent(questId);
        qe.registerQuestNpc(799836).addOnTalkEvent(questId);
        qe.registerQuestNpc(217171).addOnKillEvent(questId);
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        return defaultOnKillEvent(env, 217171, 0, 5);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.canRepeat()) {
            if (targetId == 0) {
                if (dialog == DialogAction.QUEST_ACCEPT_1) {
                    QuestService.startQuest(env);
                    return closeDialogWindow(env);
                }
            }
        }

        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (targetId == 700969) {
                if (player.isInGroup2()) {
                    PlayerGroup group = player.getPlayerGroup2();
                    for (Player member : group.getMembers()) {
                        if (member.isMentor() && MathUtil.getDistance(player, member) < GroupConfig.GROUP_MAX_DISTANCE) {
                            Npc npc = (Npc) env.getVisibleObject();
                            npc.getController().scheduleRespawn();
                            npc.getController().onDelete();
                            QuestService.addNewSpawn(npc.getWorldId(), npc.getInstanceId(), 217171, npc.getX(), npc.getY(), npc.getZ(), (byte) 0);
                            return true;
                        } else {
                            PacketSendUtility.sendPacket(player, STR_MSG_DailyQuest_Ask_Mentor);
                        }
                    }
                }
            }
            if (targetId == 799836) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    if (qs.getQuestVarById(0) == 5) {
                        return sendQuestDialog(env, 1352);
                    }
                } else if (dialog == DialogAction.SELECT_QUEST_REWARD) {
                    return defaultCloseDialog(env, 5, 5, true, true);
                }
            }
        } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 799836) {
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 5);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }
}
