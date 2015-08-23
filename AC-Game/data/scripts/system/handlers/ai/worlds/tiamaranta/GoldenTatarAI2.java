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
package ai.worlds.tiamaranta;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.manager.EmoteManager;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldPosition;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("golden_tatar")
public class GoldenTatarAI2 extends AggressiveNpcAI2 {

    private List<Integer> percents = new ArrayList<Integer>();
    private AtomicBoolean isAggred = new AtomicBoolean(false);
    private Future<?> phaseTask;
    private Future<?> thinkTask;
    private Future<?> specialSkillTask;
    private boolean think = true;
    private int curentPercent = 100;

    @Override
    public boolean canThink() {
        return think;
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        addPercent();
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isAggred.compareAndSet(false, true)) {
            startSpecialSkillTask();
            sendMsg(1500499);
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void sendMsg(int msg) {
        NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), false, 0, 0);
    }

    private synchronized void checkPercentage(int hpPercentage) {
        curentPercent = hpPercentage;
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 90:
                    case 70:
                    case 44:
                    case 23:
                        cancelspecialSkillTask();
                        think = false;
                        EmoteManager.emoteStopAttacking(getOwner());
                        SkillEngine.getInstance().getSkill(getOwner(), 20483, 60, getOwner()).useNoAnimationSkill();
                        sendMsg(1500501);
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                if (!isAlreadyDead()) {
                                    SkillEngine.getInstance().getSkill(getOwner(), 20216, 60, getOwner()).useNoAnimationSkill();
                                    startThinkTask();
                                    for (int i = 0; i < 6; i++) {
                                        rndSpawn(282746);
                                    }
                                }
                            }
                        }, 3500);
                        break;
                    case 84:
                    case 79:
                    case 75:
                    case 72:
                    case 67:
                    case 63:
                    case 59:
                    case 53:
                    case 47:
                    case 43:
                    case 39:
                    case 35:
                    case 30:
                    case 26:
                    case 21:
                    case 16:
                    case 11:
                    case 6:
                        startPhaseTask();
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    private void startThinkTask() {
        thinkTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
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
                        cancelspecialSkillTask();
                        startSpecialSkillTask();
                    }
                }
            }
        }, 20000);
    }

    private void startPhaseTask() {
        SkillEngine.getInstance().getSkill(getOwner(), 20481, 60, getOwner()).useNoAnimationSkill();
        sendMsg(1500500);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    deleteNpcs(282743);
                    for (int i = 0; i < 8; i++) {
                        rndSpawn(282743);
                    }
                    cancelspecialSkillTask();
                    startSpecialSkillTask();
                }
            }
        }, 4000);
    }

    private void startSpecialSkillTask() {
        specialSkillTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    SkillEngine.getInstance().getSkill(getOwner(), 20223, 60, getOwner()).useNoAnimationSkill();
                    specialSkillTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (!isAlreadyDead()) {
                                SkillEngine.getInstance().getSkill(getOwner(), 20224, 60, getOwner()).useNoAnimationSkill();
                                specialSkillTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!isAlreadyDead()) {
                                            SkillEngine.getInstance().getSkill(getOwner(), 20224, 60, getOwner()).useNoAnimationSkill();
                                            if (curentPercent <= 63) {
                                                specialSkillTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (!isAlreadyDead()) {
                                                            SkillEngine.getInstance().getSkill(getOwner(), 20480, 60, getOwner()).useNoAnimationSkill();
                                                            sendMsg(1500502);
                                                            ThreadPoolManager.getInstance().schedule(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    if (!isAlreadyDead()) {
                                                                        deleteNpcs(282744);
                                                                        rndSpawn(282744);
                                                                        rndSpawn(282744);
                                                                    }
                                                                }
                                                            }, 2000);
                                                        }
                                                    }
                                                }, 21000);
                                            }
                                        }
                                    }
                                }, 3500);
                            }
                        }
                    }, 1500);
                }
            }
        }, 12000);
    }

    private void deleteNpcs(final int npcId) {
        if (getKnownList() != null) {
            getKnownList().doOnAllNpcs(new Visitor<Npc>() {
                @Override
                public void visit(Npc npc) {
                    if (npc.getNpcId() == npcId) {
                        NpcActions.delete(npc);
                    }
                }
            });
        }
    }

    private void cancelspecialSkillTask() {
        if (specialSkillTask != null && !specialSkillTask.isDone()) {
            specialSkillTask.cancel(true);
        }
    }

    private void cancelPhaseTask() {
        if (phaseTask != null && !phaseTask.isDone()) {
            phaseTask.cancel(true);
        }
    }

    private void cancelThinkTask() {
        if (thinkTask != null && !thinkTask.isDone()) {
            thinkTask.cancel(true);
        }
    }

    private void rndSpawn(int npcId) {
        float direction = Rnd.get(0, 199) / 100f;
        int distance = Rnd.get(1, 25);
        float x1 = (float) (Math.cos(Math.PI * direction) * distance);
        float y1 = (float) (Math.sin(Math.PI * direction) * distance);
        WorldPosition p = getPosition();
        spawn(npcId, 538.0332f + x1, 2789.2104f + y1, 78.95826f, p.getHeading());
    }

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{90, 84, 79, 75, 72, 70, 67, 63, 59, 53, 47, 44, 43, 39, 35, 30, 26, 23, 21, 16, 11, 6});
    }

    @Override
    protected void handleDespawned() {
        cancelspecialSkillTask();
        cancelThinkTask();
        cancelPhaseTask();
        percents.clear();
        super.handleDespawned();
    }

    @Override
    protected void handleDied() {
        sendMsg(1500503);
        cancelspecialSkillTask();
        cancelThinkTask();
        cancelPhaseTask();
        percents.clear();
        deleteNpcs(282746);
        deleteNpcs(282743);
        deleteNpcs(282744);
        super.handleDied();
    }

    @Override
    protected void handleBackHome() {
        think = true;
        cancelspecialSkillTask();
        cancelThinkTask();
        cancelPhaseTask();
        addPercent();
        curentPercent = 100;
        deleteNpcs(282746);
        deleteNpcs(282743);
        deleteNpcs(282744);
        isAggred.set(false);
        super.handleBackHome();
    }
}
