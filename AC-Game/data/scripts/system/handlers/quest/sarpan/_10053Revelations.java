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
package quest.sarpan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.world.World;

/**
 * @author vlog
 */
public class _10053Revelations extends QuestHandler {

    private static final int questId = 10053;
    private static List<Integer> mobs = new ArrayList<Integer>();

    static {
        mobs.add(218760);
        mobs.add(218762);
        mobs.add(218761);
        mobs.add(218763);
    }

    public _10053Revelations() {
        super(questId);
    }

    @Override
    public void register() {
        int[] npcIds = {205987, 730493, 205795, 205535};
        for (int npcId : npcIds) {
            qe.registerQuestNpc(npcId).addOnTalkEvent(questId);
        }
        for (int mob : mobs) {
            qe.registerQuestNpc(mob).addOnKillEvent(questId);
        }
        qe.registerOnLevelUp(questId);
        qe.registerOnEnterZoneMissionEnd(questId);
        qe.registerOnEnterWorld(questId);
        qe.registerOnQuestTimerEnd(questId);
        qe.registerOnInvisibleTimerEnd(questId);
        qe.registerOnDie(questId);
    }

    @Override
    public boolean onZoneMissionEndEvent(QuestEnv env) {
        return defaultOnZoneMissionEndEvent(env);
    }

    @Override
    public boolean onLvlUpEvent(QuestEnv env) {
        return defaultOnLvlUpEvent(env, 10052, true);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs == null) {
            return false;
        }
        DialogAction dialog = env.getDialog();
        int targetId = env.getTargetId();

        if (qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            switch (targetId) {
                case 205987: { // Garnon
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
                    break;
                }
                case 730493: { // Protector's Seal
                    switch (dialog) {
                        case USE_OBJECT: {
                            if (var == 2) {
                                changeQuestStep(env, 2, 3, false); // 3
                                QuestService.questTimerStart(env, 180); // 3 minutes
                                QuestService.invisibleTimerStart(env, 170); // 2:50 minutes invisible timer for dragon spawn
                                // doesent work. maybe should be 2:40?
                                spawn(player);
                                return true;
                            }
                        }
                    }
                    break;
                }
                case 205795: { // Oriata should be spawned after timer ends?
                    switch (dialog) {
                        case QUEST_SELECT: {
                            if (var == 4) {
                                return sendQuestDialog(env, 2375);
                            } else if (var == 5) {
                                playQuestMovie(env, 709);
                                return sendQuestDialog(env, 2716);
                            }
                        }
                        case CHECK_USER_HAS_QUEST_ITEM: {
                            return checkQuestItems(env, 5, 5, true, 10000, 10001); // reward
                        }
                        case SETPRO5: {
                            return defaultCloseDialog(env, 4, 5); // 5
                        }
                        case FINISH_DIALOG: {
                            return closeDialogWindow(env);
                        }
                    }
                    break;
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205535) { // Killios
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
    public boolean onEnterWorldEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (player.getWorldId() == 300330000) {
                if (var == 1) {
                    QuestService.addNewSpawn(300330000, player.getInstanceId(), 730493, (float) 250.348, (float) 245.210, (float) 126.270, (byte) 60);
                    changeQuestStep(env, 1, 2, false); // 2
                    return true;
                } else if (var >= 2 && var < 4) {
                    changeQuestStep(env, var, 1, false); // 1
                    return true;
                } else if (var == 4 || var == 5) {
                    QuestService.addNewSpawn(300330000, player.getInstanceId(), 205795, (float) 241.348, (float) 245.879, (float) 125.475, (byte) 56);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onDieEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (player.getWorldId() == 300330000) {
                if (var == 1) {
                    changeQuestStep(env, 1, 2, false); // 2
                    return true;
                }
            } else {
                if (var >= 2 && var < 4) {
                    changeQuestStep(env, var, 1, false); // 1
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKillEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 3) {
                int targetId = env.getTargetId();
                if (mobs.contains(targetId)) {
                    spawn(player);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onQuestTimerEndEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 3) {
                QuestService.addNewSpawn(300330000, player.getInstanceId(), 205795, (float) 241.348, (float) 245.879, (float) 125.475, (byte) 56);
                changeQuestStep(env, 3, 4, false); // 4
                return playQuestMovie(env, 707);
            }
        }
        return false;
    }

    @Override
    public boolean onInvisibleTimerEndEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 3) {
                spawnHesibanata(player);
                return true;
            }
        }
        return false;
    }

    private void spawn(Player player) {
        int mobToSpawn = mobs.get(Rnd.get(0, 3));
        float x = 0;
        float y = 0;
        final float z = 124.942f;
        switch (mobToSpawn) {
            case 218760: {
                x = 250.081f;
                y = 268.308f;
                break;
            }
            case 218762: {
                x = 273.354f;
                y = 244.489f;
                break;
            }
            case 218761: {
                x = 272.994f;
                y = 244.674f;
                break;
            }
            case 218763: {
                x = 250.800f;
                y = 222.782f;
                break;
            }
        }
        Npc spawn = (Npc) QuestService.spawnQuestNpc(300330000, player.getInstanceId(), mobToSpawn, x, y, z, (byte) 0);
        Collection<Npc> allNpcs = World.getInstance().getNpcs();
        Npc target = null;
        for (Npc npc : allNpcs) {
            if (npc.getNpcId() == 730493) {
                target = npc;
            }
        }
        if (target != null) {
            spawn.getAggroList().addHate(target, 1);
        }
    }

    private void spawnHesibanata(Player player) {
        Npc spawn = (Npc) QuestService.spawnQuestNpc(300330000, player.getInstanceId(), 218890, 250.970f, 221.711f, 124.942f, (byte) 0);
        spawn.getAggroList().addHate(player, 1);
    }
}
