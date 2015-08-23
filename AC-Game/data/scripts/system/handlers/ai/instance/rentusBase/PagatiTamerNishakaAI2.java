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
package ai.instance.rentusBase;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("pagati_tamer_nishaka")
public class PagatiTamerNishakaAI2 extends AggressiveNpcAI2 {

    private Future<?> hideTask;
    private AtomicBoolean isHome = new AtomicBoolean(true);

    @Override
    public void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            sendMsg(1500397);
            startHideTask();
        }
    }

    private void cancelPhaseTask() {
        if (hideTask != null && !hideTask.isDone()) {
            hideTask.cancel(true);
        }
    }

    private void startHideTask() {
        hideTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelPhaseTask();
                } else {
                    SkillEngine.getInstance().getSkill(getOwner(), 19660, 60, getOwner()).useNoAnimationSkill();
                    sendMsg(1500398);
                    startEvent(2000, 1500399, 19661);
                    startEvent(6000, 1500399, 19661);
                    startEvent(8000, 1500400, 19662);
                }
            }
        }, 14000, 14000);
    }

    private void startEvent(int time, final int msg, final int skill) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead() && !isHome.get()) {
                    Creature target = getOwner();
                    if (skill == 19661) {
                        VisibleObject npcTarget = target.getTarget();
                        if (npcTarget != null && npcTarget instanceof Creature) {
                            target = (Creature) npcTarget;
                        }
                    }
                    if (target != null && isInRange(target, 5)) {
                        SkillEngine.getInstance().getSkill(getOwner(), skill, 60, target).useNoAnimationSkill();
                    }
                    getEffectController().removeEffect(19660);
                    sendMsg(msg);
                }
            }
        }, time);
    }

    private void sendMsg(int msg) {
        NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), 0, 0);
    }

    @Override
    protected void handleDied() {
        getPosition().getWorldMapInstance().getDoors().get(98).setOpen(true);
        cancelPhaseTask();
        sendMsg(1500401);
        super.handleDied();
    }

    @Override
    protected void handleDespawned() {
        cancelPhaseTask();
        super.handleDespawned();
    }

    @Override
    protected void handleBackHome() {
        getEffectController().removeEffect(19660);
        cancelPhaseTask();
        isHome.set(true);
        super.handleBackHome();
    }
}
