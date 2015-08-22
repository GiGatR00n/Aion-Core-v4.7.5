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

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/**
 * @author vlog
 */
public class _20062WatchingWaiting extends QuestHandler {

    private final static int questId = 20062;

    public _20062WatchingWaiting() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerOnLevelUp(questId);
        qe.registerQuestNpc(800018).addOnTalkEvent(questId);
        qe.registerQuestItem(182212559, questId);
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
            if (targetId == 800018) { // Garnon
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        } else if (var == 1) {
                            return sendQuestDialog(env, 1352);
                        }
                    }
                    case SETPRO1: {
                        return defaultCloseDialog(env, 0, 1); // 1
                    }
                    case CHECK_USER_HAS_QUEST_ITEM: {
                        return checkQuestItems(env, 1, 2, false, 10000, 10001, 182212559, 1);
                    }
                    case FINISH_DIALOG: {
                        return closeDialogWindow(env);
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 800018) { // Garnon
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
    public HandlerResult onItemUseEvent(QuestEnv questEnv, Item item) {
        Player player = questEnv.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 2) {
                if (player.isInsideZone(item.getItemTemplate().getUseArea())) {
                    removeQuestItem(questEnv, 182212559, 1);
                    Npc npc1 = (Npc) QuestService.spawnQuestNpc(player.getWorldId(), player.getInstanceId(), 218822, player.getPosition().getX(),
                            player.getPosition().getY(), player.getPosition().getZ(), player.getPosition().getHeading());
                    Npc npc2 = (Npc) QuestService.spawnQuestNpc(player.getWorldId(), player.getInstanceId(), 218822, player.getPosition().getX(),
                            player.getPosition().getY(), player.getPosition().getZ(), player.getPosition().getHeading());
                    npc1.getAggroList().addHate(player, 1);
                    npc2.getAggroList().addHate(player, 1);
                    changeQuestStep(questEnv, 2, 2, true); // reward
                    return HandlerResult.SUCCESS;
                }
            }
        }
        return HandlerResult.FAILED;
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env);
    }
}
