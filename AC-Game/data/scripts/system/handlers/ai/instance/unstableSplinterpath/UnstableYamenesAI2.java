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
package ai.instance.unstableSplinterpath;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.manager.EmoteManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldMapInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Ritsu, Luzien
 * @edit Cheatkiller
 */
@AIName("unstableyamennes")
public class UnstableYamenesAI2 extends AggressiveNpcAI2 {

    private boolean top;
    private List<Integer> percents = new ArrayList<Integer>();
    private Future<?> portalTask = null;
    private AtomicBoolean isStart = new AtomicBoolean(false);

    @Override
    protected void handleSpawned() {
        addPercent();
        top = true;
        NpcShoutsService.getInstance().sendMsg(getOwner(), 1400732);
        super.handleSpawned();
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isStart.compareAndSet(false, true)) {
            startTasks();
        }
    }

    private void startTasks() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    EmoteManager.emoteStopAttacking(getOwner());
                    SkillEngine.getInstance().getSkill(getOwner(), 19098, 55, getOwner()).useSkill();
                }
            }
        }, 600000);

        portalTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelTask();
                } else {
                    spawnPortal();
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            WorldMapInstance instance = getPosition().getWorldMapInstance();
                            deleteNpcs(instance.getNpcs(219586));
                            Npc boss = getOwner();
                            EmoteManager.emoteStopAttacking(getOwner());
                            SkillEngine.getInstance().getSkill(boss, 19282, 55, getTarget()).useSkill();
                            spawn(219586, boss.getX() + 10, boss.getY() - 10, boss.getZ(), (byte) 0);
                            spawn(219586, boss.getX() - 10, boss.getY() + 10, boss.getZ(), (byte) 0);
                            spawn(219586, boss.getX() + 10, boss.getY() + 10, boss.getZ(), (byte) 0);
                            boss.clearAttackedCount();
                            NpcShoutsService.getInstance().sendMsg(getOwner(), 1400729);
                        }
                    }, 3000);
                }
            }
        }, 60000, 60000);
    }

    private void spawnPortal() {
        Npc portalA = getPosition().getWorldMapInstance().getNpc(219580);
        Npc portalB = getPosition().getWorldMapInstance().getNpc(219579);
        Npc portalC = getPosition().getWorldMapInstance().getNpc(219567);
        if (portalA == null && portalB == null && portalC == null) {
            if (!top) {
                NpcShoutsService.getInstance().sendMsg(getOwner(), 1400637);
                spawn(219567, 288.10f, 741.95f, 216.81f, (byte) 3);
                spawn(219579, 375.05f, 750.67f, 216.82f, (byte) 59);
                spawn(219580, 341.33f, 699.38f, 216.86f, (byte) 59);
                top = true;
            } else {
                NpcShoutsService.getInstance().sendMsg(getOwner(), 1400637);
                spawn(219567, 303.69f, 736.35f, 198.7f, (byte) 0);
                spawn(219579, 335.19f, 708.92f, 198.9f, (byte) 35);
                spawn(219580, 360.23f, 741.07f, 198.7f, (byte) 0);
                top = false;
            }
        }
    }

    private void deleteNpcs(List<Npc> npcs) {
        for (Npc npc : npcs) {
            if (npc != null) {
                npc.getController().onDelete();
            }
        }
    }

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{100});
    }

    private void cancelTask() {
        if (portalTask != null && !portalTask.isDone()) {
            portalTask.cancel(true);
        }
    }

    @Override
    protected void handleBackHome() {
        addPercent();
        top = true;
        cancelTask();
        isStart.set(false);
        super.handleBackHome();
    }

    @Override
    protected void handleDespawned() {
        percents.clear();
        cancelTask();
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        deleteNpcs(instance.getNpcs(219586));
        super.handleDespawned();
    }

    @Override
    protected void handleDied() {
        percents.clear();
        cancelTask();
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        deleteNpcs(instance.getNpcs(219586));
        super.handleDied();
    }
}
