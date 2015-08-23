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

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;

import ai.NoActionAI2;


/**
 * @author zxl001
 */
@AIName("coffin")
public class CoffinAI2 extends NoActionAI2 {
	
	@Override
	protected void handleDied() {
		super.handleDied();
		spawn(284262, 382.3388f, 892.4802f, 559.375f, (byte) 1);
		getOwner().getController().delete();
	}
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		Npc npc = getOwner().getPosition().getWorldMapInstance().getNpc(284262);
		if (npc != null && npc.isSpawned())
			npc.getController().delete();
	}

	@Override
	public int modifyDamage(int damage) {
		return 1;
	}
}
