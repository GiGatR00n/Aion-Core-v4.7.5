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
package ai.instance.steelRose;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author zxl001
 */
@AIName("artillerist")
public class RoseArtilleristAI2 extends AggressiveNpcAI2 {
	
	private Npc cannon = null;
	private Future<?> task;
	private int attackCount = 0;
	private AtomicBoolean isStartedEvent = new AtomicBoolean(false);
	
	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		if (isStartedEvent.compareAndSet(false, true)) {
			cannonAttackTask(creature);
		}
	}
	
	private void cannonAttackTask(final Creature creature) {
		
		task = ThreadPoolManager.getInstance().schedule(new Runnable() {
			
			@Override
			public void run() {
				if(++attackCount % 5 == 0)
					cannonAttack(creature);
				else 
					spawnevent(creature);
				cannonAttackTask(creature);
			}
		}, 30 * 1000);
	}
	
	private void spawnevent(Creature creature) {
			NpcShoutsService.getInstance().sendMsg(getOwner(), 1500880, getObjectId(), 26, 0);
			cannon = getOwner().getPosition().getWorldMapInstance().getNpc(231017);
			Npc bnb1 = (Npc) spawn(231018, 754.08154f, 510.0203f, 1012.389f, (byte) 65);
			bnb1.getAggroList().addHate(creature, 100000);
			Npc bnb2 = (Npc) spawn(231018, 753.6996f, 507.61172f, 1012.389f, (byte) 65);
			bnb2.getAggroList().addHate(creature, 100000);
	}
	
	private void cannonAttack(final Creature creature) {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500879, getObjectId(), 26, 0);
		cannon = getOwner().getPosition().getWorldMapInstance().getNpc(231016);
		SkillEngine.getInstance().getSkill(cannon, 20385, 55, cannon).useNoAnimationSkill();
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			
			@Override
			public void run() {
				creature.getController().onAttack(cannon, 20385, 5000, true);
			}
		}, cannon.getCastingSkill().getSkillTemplate().getDuration());
	}
	
	@Override
	protected void handleDied() {
		task.cancel(true);
	}
}
