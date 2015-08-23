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
package ai.instance.esoterrace;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("captain_murugan")
public class CaptainMuruganAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isAggred = new AtomicBoolean(false);
    private Future<?> task;
    private Future<?> specialSkillTask;

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isAggred.compareAndSet(false, true)) {
            startTaskEvent();
        }
    }

    private void startTaskEvent() {
        VisibleObject target = getTarget();
        if (target != null && target instanceof Player) {
            SkillEngine.getInstance().getSkill(getOwner(), 19324, 10, target).useNoAnimationSkill();
        }
        task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelTask();
                } else {
                    sendMsg(1500194);
                    SkillEngine.getInstance().getSkill(getOwner(), 19325, 5, getOwner()).useNoAnimationSkill();
                    if (getLifeStats().getHpPercentage() < 50) {
                        specialSkillTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                if (!isAlreadyDead()) {
                                    sendMsg(1500193);
                                    VisibleObject target = getTarget();
                                    if (target != null && target instanceof Player) {
                                        SkillEngine.getInstance().getSkill(getOwner(), 19324, 10, target).useNoAnimationSkill();
                                    }
                                    specialSkillTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!isAlreadyDead()) {
                                                VisibleObject target = getTarget();
                                                if (target != null && target instanceof Player) {
                                                    SkillEngine.getInstance().getSkill(getOwner(), 19324, 10, target).useNoAnimationSkill();
                                                }
                                            }

                                        }
                                    }, 4000);
                                }

                            }
                        }, 10000);
                    }
                }
            }
        }, 20000, 20000);

    }

    private void cancelTask() {
        if (task != null && !task.isDone()) {
            task.cancel(true);
        }
    }

    private void cancelSpecialSkillTask() {
        if (specialSkillTask != null && !specialSkillTask.isDone()) {
            specialSkillTask.cancel(true);
        }
    }

    @Override
    protected void handleBackHome() {
        cancelTask();
        cancelSpecialSkillTask();
        super.handleBackHome();
    }

    @Override
    protected void handleDespawned() {
        cancelTask();
        cancelSpecialSkillTask();
        super.handleDespawned();
    }

    private void sendMsg(int msg) {
        NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), 0, 0);
    }

    @Override
    protected void handleDied() {
        cancelTask();
        cancelSpecialSkillTask();
        sendMsg(1500195);
        super.handleDied();
    }
}
