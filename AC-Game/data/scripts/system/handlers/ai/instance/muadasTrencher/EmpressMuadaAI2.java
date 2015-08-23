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
package ai.instance.muadasTrencher;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.manager.EmoteManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.geometry.Point3D;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("empress_muada")
public class EmpressMuadaAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean startedEvent = new AtomicBoolean(false);
    private AtomicBoolean isAggred = new AtomicBoolean(false);
    private AtomicBoolean sandSquallEvent = new AtomicBoolean(false);
    private Future<?> phaseTask;
    private Future<?> enrageTask;
    private boolean think = true;

    @Override
    public boolean canThink() {
        return think;
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isAggred.compareAndSet(false, true)) {
            startEnrageTask();
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage <= 75) {
            if (startedEvent.compareAndSet(false, true)) {
                startPhaseTask();
            }
        }
        if (hpPercentage <= 50) {
            if (startedEvent.compareAndSet(true, false)) {
                cancelPhaseTask();
                removeHelpers();
            }
        }
        if (hpPercentage <= 25) {
            if (sandSquallEvent.compareAndSet(false, true)) {
                think = false;
                EmoteManager.emoteStopAttacking(getOwner());
                SkillEngine.getInstance().getSkill(getOwner(), 20499, 2, getOwner()).useNoAnimationSkill();
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        if (!isAlreadyDead()) {
                            SkillEngine.getInstance().getSkill(getOwner(), 19893, 2, getOwner()).useNoAnimationSkill();
                            spawn(282533, 523.1f, 541.1f, 106.7f, (byte) 0);
                            NpcShoutsService.getInstance().sendMsg(getOwner(), 1401300);
                            ThreadPoolManager.getInstance().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    if (!isAlreadyDead()) {
                                        think = true;
                                        Creature creature = getAggroList().getMostHated();
                                        if (creature == null || creature.getLifeStats().isAlreadyDead() || !getOwner().canSee(creature)) {
                                            setStateIfNot(AIState.FIGHT);
                                            think();
                                        } else {
                                            getMoveController().abortMove();
                                            getOwner().setTarget(creature);
                                            getOwner().getGameStats().renewLastAttackTime();
                                            getOwner().getGameStats().renewLastAttackedTime();
                                            getOwner().getGameStats().renewLastChangeTargetTime();
                                            getOwner().getGameStats().renewLastSkillTime();
                                            setStateIfNot(AIState.FIGHT);
                                            handleMoveValidate();
                                            SkillEngine.getInstance().getSkill(getOwner(), 19897, 2, getOwner()).useNoAnimationSkill();
                                        }
                                    }
                                }
                            }, 20000);
                        }
                    }
                }, 3500);
            }
        }
    }

    private void startEnrageTask() {
        enrageTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    SkillEngine.getInstance().getSkill(getOwner(), 19859, 2, getOwner()).useNoAnimationSkill();
                    enrageTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (!isAlreadyDead()) {
                                SkillEngine.getInstance().getSkill(getOwner(), 20089, 2, getOwner()).useNoAnimationSkill();

                            }
                        }
                    }, 15000);
                }
            }
        }, 1200000); // 20 min
    }

    private void startPhaseTask() {
        phaseTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelPhaseTask();
                } else {
                    SkillEngine.getInstance().getSkill(getOwner(), 19898, 2, getOwner()).useNoAnimationSkill();
                    for (int i = 0; i < 3; i++) {
                        NpcShoutsService.getInstance().sendMsg(getOwner(), 1401299);
                        Point3D p = getRndPos();
                        spawn(282556, p.getX(), p.getY(), p.getZ(), (byte) 0);
                        Npc npc1 = (Npc) spawn(282535, p.getX(), p.getY(), p.getZ(), (byte) 0);
                        Npc npc2 = (Npc) spawn(282535, p.getX(), p.getY(), p.getZ(), (byte) 0);
                        NpcShoutsService.getInstance().sendMsg(npc1, 1500307, npc1.getObjectId(), 0, 1000);
                        NpcShoutsService.getInstance().sendMsg(npc2, 1500307, npc2.getObjectId(), 0, 1000);
                    }
                }
            }
        }, 1000, 45000);
    }

    private void deleteNpcs(List<Npc> npcs) {
        for (Npc npc : npcs) {
            if (npc != null) {
                npc.getController().onDelete();
            }
        }
    }

    private void removeHelpers() {
        WorldPosition p = getPosition();
        if (p != null) {
            WorldMapInstance instance = p.getWorldMapInstance();
            if (instance != null) {
                deleteNpcs(instance.getNpcs(282556));
                deleteNpcs(instance.getNpcs(282535));
                deleteNpcs(instance.getNpcs(282539));
            }
        }
    }

    private Point3D getRndPos() {
        float direction = Rnd.get(0, 199) / 100f;
        int distance = Rnd.get(2, 5);
        float x1 = (float) (Math.cos(Math.PI * direction) * distance);
        float y1 = (float) (Math.sin(Math.PI * direction) * distance);
        WorldPosition p = getPosition();
        return new Point3D(p.getX() + x1, p.getY() + y1, p.getZ());
    }

    private void cancelPhaseTask() {
        if (phaseTask != null && !phaseTask.isDone()) {
            phaseTask.cancel(true);
        }
    }

    private void cancelEnrageTask() {
        if (enrageTask != null && !enrageTask.isDone()) {
            enrageTask.cancel(true);
        }
    }

    @Override
    protected void handleBackHome() {
        cancelEnrageTask();
        cancelPhaseTask();
        getEffectController().removeEffect(19859);
        removeHelpers();
        think = true;
        isAggred.set(false);
        sandSquallEvent.set(false);
        startedEvent.set(false);
        super.handleBackHome();
    }

    @Override
    protected void handleDespawned() {
        cancelEnrageTask();
        cancelPhaseTask();
        super.handleDespawned();
    }

    @Override
    protected void handleDied() {
        removeHelpers();
        cancelEnrageTask();
        cancelPhaseTask();
        super.handleDied();
    }
}
