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
package ai.instance.sauroSupplyBase;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.NpcShoutsService;

import java.util.concurrent.Future;

/**
 * @rework Blackfire
 */
 
@AIName("kurmata")
public class KurmataAI2 extends AggressiveNpcAI2 {

    private int stage = 0;
    private boolean isStart = false;
	private boolean started = false;
	private Future<?> SpawnBeacon;

    @Override
    protected void handleCreatureAggro(Creature creature) {
        super.handleCreatureAggro(creature);
        wakeUp();
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        wakeUp();
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void wakeUp() {
        isStart = true;
    }

    private void checkPercentage(int hpPercentage) {
		if (hpPercentage == 100) {
            cancelTask();
		}
		if (hpPercentage <= 90) {
            stage0();
		}
        if (hpPercentage <= 50 && stage < 1) {
            stage1();
            stage = 1;
        }
        if (hpPercentage <= 40 && stage < 2) {
            stage2();
            stage = 2;
        }
    }
	
	private void stage0() {
        if (isAlreadyDead() || !isStart) {
            return;
        } else {
			if (!started) {
				started = true;
				NpcShoutsService.getInstance().sendMsg(getOwner(), 1501049, getObjectId(), 0, 0);
				ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
					NpcShoutsService.getInstance().sendMsg(getOwner(), 1501050, getObjectId(), 0, 0);
                    float targetX = getOwner().getTarget().getPosition().getX();
					float targetY = getOwner().getTarget().getPosition().getY();
					float targetZ = getOwner().getTarget().getPosition().getZ();
					int targetH = getOwner().getTarget().getPosition().getHeading();
					spawn(284454, targetX, targetY, targetZ, (byte) targetH);   
                    }
                }, 5000);
				
				SpawnBeacon = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
				 @Override
				 public void run() {
						NpcShoutsService.getInstance().sendMsg(getOwner(), 1501049, getObjectId(), 0, 0);
						ThreadPoolManager.getInstance().schedule(new Runnable() {
							@Override
							public void run() {
							NpcShoutsService.getInstance().sendMsg(getOwner(), 1501052, getObjectId(), 0, 0);
							float targetX = getOwner().getTarget().getPosition().getX();
							float targetY = getOwner().getTarget().getPosition().getY();
							float targetZ = getOwner().getTarget().getPosition().getZ();
							int targetH = getOwner().getTarget().getPosition().getHeading();
							spawn(284454, targetX, targetY, targetZ, (byte) targetH);
							Player target = getRandomTarget();
								if (target == null) {
								}   
							}
						}, 5000);
					}
				}, 1, 60000);
			}
        }
    }
	
    private void stage1() {
        int delay = 0;
        if (isAlreadyDead() || !isStart)
            return;
        else {
            SkillEngine.getInstance().getSkill(getOwner(), 20701, 45, getOwner()).useNoAnimationSkill();
        }
    }

    private void stage2() {
        int delay = 20000;
        if (isAlreadyDead() || !isStart)
            return;
        else {
            SkillEngine.getInstance().getSkill(getOwner(), 20858, 45, getOwner()).useNoAnimationSkill();
            scheduleDelayStage2(delay);
        }
    }

    private void scheduleDelayStage2(int delay) {
        if (!isStart && !isAlreadyDead())
            return;
        else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {

                @Override
                public void run() {
                    stage2();
                }
            }, delay);
        }
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        isStart = false;
        stage = 0;
    }
	
	private void cancelTask() {
	  if (SpawnBeacon != null && !SpawnBeacon.isCancelled()) {
	  	SpawnBeacon.cancel(true);
	  }
   }
   
   @Override
    protected void handleDied() {
        super.handleDied();
        isStart = false;
		cancelTask();
        stage = 0;
	}

}