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

package ai.instance.katalamAge;

import java.util.concurrent.Future;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author zxl001
 */
@AIName("freud")
public class FreudAI2 extends AggressiveNpcAI2 {

	private Future<?> task;

	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		if (task == null)
			startTask();
	}

	@Override
	protected void handleDied() {
		super.handleDied();
		if (task != null)
			task.cancel(true);
	}

	@Override
	protected void handleDespawned() {
		super.handleDespawned();
		if (task != null)
			task.cancel(false);
	}

	private void startTask() {
		task = ThreadPoolManager.getInstance().schedule(new Runnable() {

			@Override
			public void run() {
				Npc bossNpc = getPosition().getWorldMapInstance().getNpc(231073);
				if (bossNpc != null && !bossNpc.getLifeStats().isAlreadyDead()) {
					SkillEngine.getInstance().getSkill(getOwner(), 21371, 56, bossNpc).useSkill();
					SkillEngine.getInstance().getSkill(getOwner(), 21258, 56, bossNpc).useSkill();
					buffTask();
				}
			}
		}, 30000);
	}
	
	private void buffTask() {
		task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				startBuff();
			}
		}, 15000, 15000);
	}

	private void startBuff() {
		Npc bossNpc = getPosition().getWorldMapInstance().getNpc(231073);
		if (bossNpc != null && !bossNpc.getLifeStats().isAlreadyDead()) {
			SkillEngine.getInstance().getSkill(getOwner(), 21257, 56, bossNpc).useNoAnimationSkill();
		}
	}
}
