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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.world.World;

/**
 * @author zhkchi
 * @reworked vlog
 */
public class _20060TheFourHearts extends QuestHandler {

    private final static int questId = 20060;
    private static List<Integer> beasts = new ArrayList<Integer>();

    public _20060TheFourHearts() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcs = {205864, 800020};
        beasts.add(218829);
        beasts.add(218830);
        beasts.add(218831);
        for (int npc : npcs) {
            qe.registerQuestNpc(npc).addOnTalkEvent(questId);
        }
        for (int beast : beasts) {
            qe.registerQuestNpc(beast).addOnKillEvent(questId);
        }
        qe.registerOnLevelUp(questId);
        qe.registerOnQuestTimerEnd(questId);
        qe.registerOnDie(questId);
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
            if (targetId == 205864) { // Skafir
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 0) {
                            return sendQuestDialog(env, 1011);
                        }
                    }
                    case SETPRO1: {
                        return defaultCloseDialog(env, 0, 1); // 1
                    }
                }
            } else if (targetId == 800020) { // Garnon 800081
                switch (dialog) {
                    case QUEST_SELECT: {
                        if (var == 2) {
                            return sendQuestDialog(env, 1693);
                        }
                    }
                    case SELECT_ACTION_1694: {
                        sendQuestDialog(env, 1694);
                        return playQuestMovie(env, 754);
                    }
                    case SETPRO3: {
                        closeDialogWindow(env);
                        Npc garnonGray = (Npc) QuestService.spawnQuestNpc(player.getWorldId(), player.getInstanceId(), 800081,
                                442.279f, 464.349f, 341.520f, (byte) 20);
                        QuestService.questTimerStart(env, 30);
                        spawnAndAttack(player, garnonGray);
                        changeQuestStep(env, 2, 3, false); // 3
                        if (env.getVisibleObject() != null && env.getVisibleObject() instanceof Npc) {
                            Npc npc = (Npc) env.getVisibleObject();
                            if (npc.getNpcId() == 800020) {
                                World.getInstance().getWorldMap(600030000).getWorld().despawn(npc);
                            }
                        }
                        return true;
                    }
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 800020) {
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
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();

        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 1) {
                int weva = qs.getQuestVarById(1);
                int ganur = qs.getQuestVarById(2);
                int zambir = qs.getQuestVarById(3);
                if (beasts.contains(targetId)) {
                    if (weva + ganur + zambir == 2) {
                        qs.setQuestVar(2); // 2
                        updateQuestStatus(env);
                        return true;
                    }
                    return defaultOnKillEvent(env, targetId, 0, 1, beasts.indexOf(targetId) + 1); // i: 1
                }
            }
        }
        return false;
    }

    private void spawnAndAttack(Player player, Npc target) {
        Npc spawn1 = (Npc) QuestService.spawnQuestNpc(600030000, player.getInstanceId(), 218825, 454.845f, 471.380f,
                341.728f, (byte) 0);
        Npc spawn2 = (Npc) QuestService.spawnQuestNpc(600030000, player.getInstanceId(), 218765, 455.157f, 470.027f,
                341.647f, (byte) 0);
        Npc spawn3 = (Npc) QuestService.spawnQuestNpc(600030000, player.getInstanceId(), 218825, 454.616f, 470.859f,
                341.647f, (byte) 0);
        Npc spawn4 = (Npc) QuestService.spawnQuestNpc(600030000, player.getInstanceId(), 218825, 454.415f, 471.770f,
                341.686f, (byte) 0);
        Npc spawn5 = (Npc) QuestService.spawnQuestNpc(600030000, player.getInstanceId(), 218825, 454.273f, 470.475f,
                341.568f, (byte) 0);
        spawn1.getAggroList().addHate(target, 1);
        spawn2.getAggroList().addHate(target, 1);
        spawn3.getAggroList().addHate(target, 1);
        spawn4.getAggroList().addHate(target, 1);
        spawn5.getAggroList().addHate(target, 1);
    }

    @Override
    public boolean onQuestTimerEndEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 3) {
                Npc target1 = null;
                Npc target2 = null;
                Collection<Npc> npcs = World.getInstance().getWorldMap(600030000).getWorld().getNpcs();
                for (Npc npc : npcs) {
                    if (npc.getNpcId() == 800081) {
                        target1 = npc;
                    } else if (npc.getNpcId() == 800020) {
                        target2 = npc;
                    }
                    if (target1 != null && target2 != null) {
                        break;
                    }
                }
                if (target1 != null) {
                    NpcActions.delete(target1);
                }
                if (target2 != null && !target2.isSpawned()) {
                    SpawnEngine.spawnObject(target2.getSpawn(), target2.getInstanceId());
                }
            }
            changeQuestStep(env, 3, 3, true); // reward
            return true;
        }
        return false;
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
		Player player = env.getPlayer();
        PlayerClass playerClass = player.getCommonData().getPlayerClass();
            if (playerClass == PlayerClass.RIDER) {
            return false;
            }
        if (defaultOnLvlUpEvent(env)) {
            int[] ids = {20061, 20062, 20063, 20064, 20065};
            for (int id : ids) {
                QuestEngine.getInstance().onEnterZoneMissionEnd(
                        new QuestEnv(env.getVisibleObject(), env.getPlayer(), id, env.getDialogId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onDieEvent(QuestEnv env) {
        QuestService.questTimerEnd(env);
        return this.onQuestTimerEndEvent(env);
    }
}
