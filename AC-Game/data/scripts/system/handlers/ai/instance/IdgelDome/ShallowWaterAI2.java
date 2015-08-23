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

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.geometry.Point3D;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.MathUtil;


/**
 * @author GiGatR00n v4.7.5.x
 */
@AIName("shallow_water") //855009
public class ShallowWaterAI2 extends NpcAI2 {

    @Override
    protected void handleCreatureSee(Creature creature) {
        checkDistance(this, creature);
    }  

    @Override
    protected void handleCreatureMoved(Creature creature) {
        checkDistance(this, creature);
    } 
    
    /*
     * Way #1 (More Efficient and Accurate using new mathematics formula)
     */
    private void checkDistance(NpcAI2 ai, Creature creature) {
        if (creature instanceof Player && !creature.getLifeStats().isAlreadyDead()) {
        	if (MathUtil.isInAnnulus(creature, new Point3D(264.4382f, 258.58527f, 85.81963f), 30.04288f, 16.70f)) {
	    		if (creature.getZ() < 86) {
	            	if (!creature.getEffectController().hasAbnormalEffect(8853)) {
	            		applyEffect(8853, creature);//SkillId:8853 (Contaminated Ide Pool)
	            	}	            			
	    		}        		
        	}
        }
    }
    
      /*
       * Way #2
       */
//    private void checkDistance(NpcAI2 ai, Creature creature) {
//        if (creature instanceof Player && !creature.getLifeStats().isAlreadyDead()) {
//        	if (MathUtil.isIn3dRangeLimited(getOwner(), creature, 16.70f, 29.32f)) {
//        		if (creature.getZ() < 86) {
//	        		if (!creature.getEffectController().hasAbnormalEffect(8853)) {
//	        			applyEffect(8853, creature);//SkillId:8853 (Contaminated Ide Pool)
//	        		}
//        		}
//        	}
//    	}
//    }  
    
    private void useSkill(int skillId) {
        SkillEngine.getInstance().getSkill(getOwner(), skillId, 65, getOwner().getTarget()).useNoAnimationSkill();
    }
    
    private void applyEffect(int skillId, Creature creature) {
    	SkillEngine.getInstance().applyEffectDirectly(skillId, getOwner(), creature, 0);
    }
}