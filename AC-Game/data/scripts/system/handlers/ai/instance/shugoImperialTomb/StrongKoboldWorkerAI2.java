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
package ai.instance.shugoImperialTomb;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;

@AIName("strong_kobold_worker")
public class StrongKoboldWorkerAI2 extends AggressiveNpcAI2 {
	
	Npc tower;
	Npc towerCenter;
	
	@Override
	protected  void handleDeactivate() {
	}
	
	@Override
	public int modifyDamage(int damage) {
		return 500;
	}
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		towerCenter = getPosition().getWorldMapInstance().getNpc(831130);
		tower = getPosition().getWorldMapInstance().getNpc(831250);
		AI2Actions.targetCreature(StrongKoboldWorkerAI2.this, towerCenter);
		getAggroList().addHate(towerCenter, 100000);
		AI2Actions.targetCreature(StrongKoboldWorkerAI2.this, tower);
		getAggroList().addHate(tower, 100000);
	}
	
	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
	}
	
	@Override
	protected void handleActivate() {
		super.handleActivate();
		towerCenter = getPosition().getWorldMapInstance().getNpc(831130);
		AI2Actions.targetCreature(StrongKoboldWorkerAI2.this, towerCenter);
		tower = getPosition().getWorldMapInstance().getNpc(831250);
		AI2Actions.targetCreature(StrongKoboldWorkerAI2.this, tower);
	}
}
