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
package ai.instance.raksang;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("the_flamelord")
public class TheFlamelordAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isAggred = new AtomicBoolean(false);
    private List<Integer> percents = new ArrayList<Integer>();
    private Future<?> phaseTask;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        addPercent();
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 90:
                        startPhaseTask();
                        break;
                    case 40:
                    case 30:
                    case 20:
                    case 10:
                        startPhaseEvent(percent);
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    private void startPhaseEvent(final int percent) {
        cancelPhaseTask();
        sendMsg(1401120);
        SkillEngine.getInstance().getSkill(getOwner(), 19980, 46, getOwner()).useNoAnimationSkill();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    switch (percent) {
                        case 40:
                            moveExecutor(282451);
                            break;
                        case 30:
                            moveExecutor(282451);
                            moveExecutor(282452);
                            break;
                        case 20:
                            moveExecutor(282451);
                            moveExecutor(282452);
                            moveExecutor(282453);
                            break;
                        case 10:
                            moveExecutor(282451);
                            moveExecutor(282452);
                            moveExecutor(282453);
                            moveExecutor(282454);
                            break;
                    }
                    SkillEngine.getInstance().getSkill(getOwner(), 19924, 44, getOwner()).useNoAnimationSkill();
                    cancelPhaseTask();
                    startPhaseTask();
                }
            }
        }, 5000);
    }

    private void moveExecutor(final int executorId) {
        final Npc npc = (Npc) spawn(executorId, 802.845f, 964.903f, 792.102f, (byte) 0);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    int targetId = 0;
                    switch (executorId) {
                        case 282451:
                            targetId = 701062;
                            break;
                        case 282452:
                            targetId = 701063;
                            break;
                        case 282453:
                            targetId = 701064;
                            break;
                        case 282454:
                            targetId = 701065;
                            break;
                    }
                    Npc target = getPosition().getWorldMapInstance().getNpc(targetId);
                    if (target != null) {
                        npc.setTarget(target);
                        npc.getMoveController().moveToTargetObject();
                    }
                }
            }
        }, 1500);
    }

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{90, 40, 30, 20, 10});
    }

    private void startPhaseTask() {
        phaseTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelPhaseTask();
                } else {
                    SkillEngine.getInstance().getSkill(getOwner(), 19925, 44, getOwner()).useNoAnimationSkill();
                    sendMsg(1401119);
                }
            }
        }, 3000, 30000);
    }

    private void cancelPhaseTask() {
        if (phaseTask != null && !phaseTask.isDone()) {
            phaseTask.cancel(true);
        }
    }

    @Override
    protected void handleDied() {
        percents.clear();
        cancelPhaseTask();
        getPosition().getWorldMapInstance().getDoors().get(118).setOpen(true);
        sendMsg(1401121);
        super.handleDied();
    }

    @Override
    protected void handleDespawned() {
        percents.clear();
        cancelPhaseTask();
        super.handleDespawned();
    }

    private void sendMsg(int msg) {
        NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), Rnd.get(0, 1) == 1 ? true : false, 0, 0);
    }

    @Override
    protected void handleAttack(Creature creature) {
        if (isAggred.compareAndSet(false, true)) {
            sendMsg(1401118);
        }
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    @Override
    protected void handleBackHome() {
        addPercent();
        cancelPhaseTask();
        isAggred.set(false);
        super.handleBackHome();
    }
}
