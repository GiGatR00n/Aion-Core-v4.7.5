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

import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author mr.maddison
 *
 */
public class _41502HiddenConnections extends QuestHandler {

    private final static int questId = 41502;

    public _41502HiddenConnections() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestNpc(205935).addOnQuestStart(questId);
        qe.registerQuestNpc(205935).addOnTalkEvent(questId);
        qe.registerQuestNpc(701129).addOnTalkEvent(questId);
        qe.registerQuestItem(182212514, questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 205935) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        return sendQuestDialog(env, 4762);
                    }
                    case QUEST_ACCEPT_SIMPLE: {
                        return sendQuestStartDialog(env, 182212514, 1);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 701129) {
                TeleportService2.teleportTo(player, 600030000, 329, 541, 362, (byte) 100, TeleportAnimation.BEAM_ANIMATION);
                changeQuestStep(env, 1, 2, true);
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205935) {
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
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        final Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);

        if (qs != null && qs.getStatus() == QuestStatus.START) {
            if (player.isInsideZone(ZoneName.get("SMALL_CRATER_600030000")) && item.getItemId() == 182212514) {
                TeleportService2.teleportTo(player, 600030000, 604, 1018, 210, (byte) 30, TeleportAnimation.BEAM_ANIMATION);
                changeQuestStep(env, 0, 1, false);
            }
        }
        return HandlerResult.FAILED;
    }
}
