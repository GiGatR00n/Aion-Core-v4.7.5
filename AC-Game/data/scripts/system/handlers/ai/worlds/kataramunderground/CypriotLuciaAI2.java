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
package ai.worlds.kataramunderground;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;

import ai.AggressiveNpcAI2;

/**
 * @author zxl001
 */
@AIName("cypriotlucia")
public class CypriotLuciaAI2 extends AggressiveNpcAI2 {

	private int stage = 0;

	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		checkLifeStart(getLifeStats().getHpPercentage());
	}

	private void checkLifeStart(int perce) {
		if (perce <= 50 && stage == 0) {
			stage++;
			this.spawn();
		}
		if (perce <= 25 && stage == 1) {
			stage++;
			this.spawn();
		}
	}

	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		stage = 0;
	}

	private void spawn() {
		for (int i = 0; i < 3; i++) {
			float direction = Rnd.get(0, 199) / 100f;
			float x1 = (float) (Math.cos(Math.PI * direction) * 5);
			float y1 = (float) (Math.sin(Math.PI * direction) * 5);
			spawn(284273, getOwner().getX() + x1, getOwner().getY() + y1, getOwner().getZ(), (byte) 0);
		}
	}
	
	@Override
	protected void handleDied() {
		super.handleDied();
		Npc npc = getOwner().getPosition().getWorldMapInstance().getNpc(284273);
		Npc npc1 = getOwner().getPosition().getWorldMapInstance().getNpc(284278);
		if (npc != null && npc.isSpawned())
			npc.getController().delete();
		if (npc1 != null && npc.isSpawned())
			npc1.getController().delete();
	}
}
