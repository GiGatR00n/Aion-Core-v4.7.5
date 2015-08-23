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
package ai.instance.IdgelDome;

import java.util.concurrent.Future;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.ThreadPoolManager;


/**
 * @author GiGatR00n v4.7.5.x
 */
@AIName("danuar_icon") //702300, 702301
public class DanuarIconAI2 extends AggressiveNpcAI2 {

	private Future<?> task;
	
	@Override
	public void handleDespawned() {
		cancelTask();
		super.handleDespawned();
	}	
	
    @Override
    protected void handleDied() {
    	cancelTask();
        super.handleDied();
    } 	
	
    @Override
    protected void handleCreatureSee(Creature creature) {
        checkDistance(this, creature);
    }

    @Override
    protected void handleCreatureNotSee(Creature creature) {
        if (task != null) {
        	cancelTask();
            task = null;
        }
    }    
    
    @Override
    protected void handleCreatureMoved(Creature creature) {
        checkDistance(this, creature);
    }  
    
    private void checkDistance(NpcAI2 ai, Creature creature) 
    {
        if (creature instanceof Player && creature.getRace() != getOwner().getRace() && !creature.getLifeStats().isAlreadyDead()) {
        	if (task == null) {
	            if (MathUtil.isIn3dRange(getOwner(), creature, 30)) {
	            	AI2Actions.targetCreature(this, creature);
	            	Fire();
	            }
        	}
        }
    }
    
    private void Fire() 
    {
        final Player pTarget = (Player) getOwner().getTarget();
        task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (pTarget == null || pTarget.getLifeStats().isAlreadyDead()) {
                    cancelTask();
                } else {
                	useSkill(21420); //SkillId:21420 (Wide Area Bombardment)
                }
            }
        }, 0, 3300);
        getOwner().getController().addTask(TaskId.SKILL_USE, task);
    }    
    
    private void useSkill(int skillId) {
        SkillEngine.getInstance().getSkill(getOwner(), skillId, 65, getOwner().getTarget()).useSkill();
    }
    
    private void cancelTask() {
        if (task != null && !task.isDone()) {
        	task.cancel(true);
        }
    }
}