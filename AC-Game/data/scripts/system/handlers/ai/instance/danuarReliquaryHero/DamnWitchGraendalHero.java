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
package ai.instance.danuarReliquaryHero;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@AIName("damn_witch_graendal_hero")
public class DamnWitchGraendalHero extends AggressiveNpcAI2 {

	private List<Integer> percents = new ArrayList<Integer>();

	@Override
	protected void handleSpawned() {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500743, getObjectId(), 0, 1000);
		addPercent();
		super.handleSpawned();
	}

	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		checkPercentage(getLifeStats().getHpPercentage());
	}

	private void checkPercentage(int hpPercentage) {
		if (hpPercentage > 95 && percents.size() < 3) {
			addPercent();
		}
		for (Integer percent : percents) {
			if (hpPercentage <= percent) {
				switch (percent) {
					case 95:
						//shout();
						skill1();
						break;
					case 75:
						shout2();
						skill3();
						break;
					case 63:
						shout_spawn();
						spawn_support();
						degeneration_skill();
						degeneration();
						break;
				}
				percents.remove(percent);
				break;
			}
		}
	}
	private void addPercent() {
		percents.clear();
		Collections.addAll(percents, new Integer[]{95,75,63});
	}
	private void skill1() {
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 21172, 65, target).useNoAnimationSkill();
		}
	}
	private void skill3() {
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 21172, 65, target).useNoAnimationSkill();
		}
	}
	private void degeneration_skill() {
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 21165, 65, target).useNoAnimationSkill();
		}
	}
	private void shout_spawn() {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500750, getObjectId(), 0, 1000);
	}
  private void spawn_support() {
 		spawn(284379, 264.6672f, 265.9347f, 241.8658f, (byte) 90);
 		spawn(284380, 248.6492f, 265.8888f, 241.8923f, (byte) 90);
 		spawn(284381, 264.6672f, 265.9347f, 241.8658f, (byte) 90);
 		spawn(284382, 248.3278f, 249.7112f, 241.8719f, (byte) 16);
 	}
	private void shout2() {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500738, getObjectId(), 0, 1000);
	}
	private void checkhp(final Npc npc) {
				npc.getLifeStats().setCurrentHpPercent(63);
	}
  private void degeneration() {
 		AI2Actions.deleteOwner(this);
                checkhp((Npc) spawn(855242, 255.5008f, 293.1228f, 253.7146f, (byte) 89));
 	}
	@Override
	protected void handleDespawned() {
		percents.clear();
		super.handleDespawned();
	}
	@Override
	protected void handleDied() {
		percents.clear();
		super.handleDied();
	}
	@Override
	protected void handleBackHome() {
		addPercent();
		super.handleBackHome();
	}
}