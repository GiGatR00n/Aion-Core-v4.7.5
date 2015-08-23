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
package ai.instance.idgelResearchCenter;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;

@AIName("stone_jar_idle")
public class StoneJarIdleAI2 extends NpcAI2 {

	@Override
	protected void handleDied() {
            		super.handleDied();
            		if (Rnd.get(1, 3) == 1) {
			spawnRemains();
		}
	}
        
	private void spawnRemains() {
                spawn(284026, getOwner().getX()+1, getOwner().getY()+1, getOwner().getZ(), (byte) 0);
                spawn(284026, getOwner().getX()+2, getOwner().getY()+2, getOwner().getZ(), (byte) 0);
                spawn(284026, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
                spawn(284026, getOwner().getX()-1, getOwner().getY()-1, getOwner().getZ(), (byte) 0);
                spawn(284026, getOwner().getX()-2, getOwner().getY()-2, getOwner().getZ(), (byte) 0);
                AI2Actions.deleteOwner(this);
	}
        


}