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

import ai.ActionItemNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.controllers.effect.EffectController;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("explosive_drana_crystal")
public class ExplosiveDranaCrystalAI2 extends ActionItemNpcAI2 {

    private AtomicBoolean isUsed = new AtomicBoolean(false);
    private Future<?> lifeTask;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        startLifeTask();
    }

    @Override
    protected void handleUseItemFinish(Player player) {
        if (isUsed.compareAndSet(false, true)) {
            WorldPosition p = getPosition();
            Npc boss = p.getWorldMapInstance().getNpc(217308);
            if (boss != null && !NpcActions.isAlreadyDead(boss)) {
                EffectController ef = boss.getEffectController();
                if (ef.hasAbnormalEffect(19370)) {
                    ef.removeEffect(19370);
                } else if (ef.hasAbnormalEffect(19371)) {
                    ef.removeEffect(19371);
                } else if (ef.hasAbnormalEffect(19372)) {
                    ef.removeEffect(19372);
                }
            }
            Npc npc = (Npc) spawn(282530, p.getX(), p.getY(), p.getZ(), p.getHeading());
            Npc invisibleNpc = (Npc) spawn(282529, p.getX(), p.getY(), p.getZ(), p.getHeading());
            SkillEngine.getInstance().getSkill(npc, 19373, 60, npc).useNoAnimationSkill();
            SkillEngine.getInstance().getSkill(invisibleNpc, 19654, 60, invisibleNpc).useNoAnimationSkill();
            NpcActions.delete(invisibleNpc);
            AI2Actions.deleteOwner(this);
        }
    }

    private void cancelLifeTask() {
        if (lifeTask != null && !lifeTask.isDone()) {
            lifeTask.cancel(true);
        }
    }

    private void startLifeTask() {
        lifeTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    AI2Actions.deleteOwner(null);
                }
            }
        }, 60000);
    }

    @Override
    protected void handleDied() {
        cancelLifeTask();
        super.handleDied();
    }

    @Override
    protected void handleDespawned() {
        cancelLifeTask();
        super.handleDespawned();
    }
}
