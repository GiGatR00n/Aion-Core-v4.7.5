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
package quest.fort_tiamat;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.instance.InstanceService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author Cheatkiller
 *
 */
public class _20072IsraphelTrueFace extends QuestHandler {

    private final static int questId = 20072;

    public _20072IsraphelTrueFace() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {800365, 205617, 205579};
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
        qe.registerOnLevelUp(questId);
        qe.registerOnEnterWorld(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs == null) {
            return false;
        }

        int var = qs.getQuestVarById(0);

        if (qs.getStatus() == QuestStatus.START) {
            if (targetId == 205617) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        } else if (player.getInventory().getItemCountByItemId(182213251) >= 3) {
                            return sendQuestDialog(env, 1352);
                        } else if (var == 2) {
                            return sendQuestDialog(env, 1693);
                        }
                    }
                    case CHECK_USER_HAS_QUEST_ITEM: {
                        return checkQuestItems(env, 1, 2, false, 10000, 10001);
                    }
                    case SETPRO1: {
                        return defaultCloseDialog(env, 0, 1); // 1
                    }
                    case SETPRO2: {
                        return sendQuestDialog(env, 1693);
                    }
                    case SETPRO3: {
                        WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(300500000);
                        InstanceService.registerPlayerWithInstance(newInstance, player);
                        TeleportService2.teleportTo(player, 300500000, newInstance.getInstanceId(), 224f, 251f, 125f, (byte) 10, TeleportAnimation.BEAM_ANIMATION);
                        QuestService.addNewSpawn(300500000, player.getInstanceId(), 701503, 250f, 245f, 129f, (byte) 119);
                        QuestService.addNewSpawn(300500000, player.getInstanceId(), 800365, 257f, 246f, 124f, (byte) 119);
                        removeQuestItem(env, 182213250, 1);
                        return defaultCloseDialog(env, 2, 3);
                    }
                }
            } else if (targetId == 800365) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 3) {
                            playQuestMovie(env, 495);
                            return sendQuestDialog(env, 2034);
                        }
                    }
                    case SETPRO4: {
                        //playQuestMovie(env, 495);
                        env.getVisibleObject().getController().delete();
                        TeleportService2.teleportTo(player, 600020000, 1375f, 1452f, 560f, (byte) 0, TeleportAnimation.BEAM_ANIMATION);
                        return defaultCloseDialog(env, 3, 4);
                    }
                }
            } else if (targetId == 205579) {
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 4) {
                            return sendQuestDialog(env, 2375);
                        }
                    }
                    case SET_SUCCEED: {
                        return defaultCloseDialog(env, 4, 5, true, false);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205617) {
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
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 20071);
    }
}
