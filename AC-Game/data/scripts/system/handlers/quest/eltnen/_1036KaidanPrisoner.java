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
package quest.eltnen;

import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Rhys2002
 * @Fixed Ritsu
 */
public class _1036KaidanPrisoner extends QuestHandler {

    private final static int questId = 1036;
    private final static int[] npc_ids = {203904, 204045, 204003, 204004, 204020, 203901};

    public _1036KaidanPrisoner() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnLevelUp(questId);
        for (int npc_id : npc_ids) {
            qe.registerQuestNpc(npc_id).addOnTalkEvent(questId);
        }
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 1300, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        final Player player = env.getPlayer();
        final QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);
        int targetId = env.getTargetId();

        if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 203901) {
                removeQuestItem(env, 182201005, 1);
                return sendQuestEndDialog(env);
            }
        } else if (qs.getStatus() != QuestStatus.START) {
            return false;
        }
        if (targetId == 203904) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 0) {
                        return sendQuestDialog(env, 1011);
                    }
                case SETPRO1:
                    if (var == 0) {
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                    }
                    return false;
            }
        } else if (targetId == 204045) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 1) {
                        return sendQuestDialog(env, 1352);
                    }
                case SELECT_ACTION_1354:
                    if (var == 1) {
                        playQuestMovie(env, 32);
                    }
                    break;
                case SETPRO2:
                    if (var == 1) {
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(env);
                        TeleportService2.teleportTo(player, 210020000, 1357f, 2566f, 279.6f, (byte) 89, TeleportAnimation.BEAM_ANIMATION);
                        return true;
                    }
                    return false;
            }
        } else if (targetId == 204003) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 2) {
                        return sendQuestDialog(env, 1693);
                    } else if (var == 3 && QuestService.collectItemCheck(env, true)) {
                        return sendQuestDialog(env, 2034);
                    } else {
                        return sendQuestDialog(env, 2120);
                    }
                case SETPRO3:
                    if (var == 2) {
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                    }
                case SETPRO4:
                    if (var == 3) {
                        playQuestMovie(env, 50);
                        qs.setQuestVarById(0, var + 1);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                    }
                    return false;
            }
        } else if (targetId == 204004) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 4) {
                        return sendQuestDialog(env, 2375);
                    }
                 case SETPRO5:
               if (var == 4) {
                  if (!giveQuestItem(env, 182201004, 1)) {
                     return true;
                  }
                  qs.setQuestVarById(0, var + 1);
                  updateQuestStatus(env);
                  TeleportService2.teleportTo(player, 210020000, 1596.1948f, 1529.9152f, 317, (byte) 120, TeleportAnimation.BEAM_ANIMATION);   
		  PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                  return true;
                    }
                    return false;
            }
        } else if (targetId == 204020) {
            switch (env.getDialog()) {
                case QUEST_SELECT:
                    if (var == 5) {
                        return sendQuestDialog(env, 2716);
                    }
                case SELECT_ACTION_2717:
                    removeQuestItem(env, 182201004, 1);
                case SETPRO5:
                    if (var == 5) {
                        if (!giveQuestItem(env, 182201005, 1)) {
                            return true;
                        }
                        qs.setQuestVarById(0, var + 1);
                        qs.setStatus(QuestStatus.REWARD);
                        updateQuestStatus(env);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                    }
                    return false;
            }
        }
        return false;
    }
}
