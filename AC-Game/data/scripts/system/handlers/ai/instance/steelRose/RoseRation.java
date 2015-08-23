/*
 * This file is part of aion-lightning <aion-lightning.com>.
 *
 *  aion-lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-lightning.  If not, see <http://www.gnu.org/licenses/>.
 */
package ai.instance.steelRose;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;

import ai.ActionItemNpcAI2;

/**
 * @author zxl001
 */
@AIName("ration")
public class RoseRation extends ActionItemNpcAI2 {

	@Override
	protected void handleDialogStart(Player player) {
		handleUseItemStart(player);
	}
	
	@Override
	protected void handleUseItemStart(final Player player) {
		super.handleUseItemStart(player);
	}
	
	@Override
	protected void handleUseItemFinish(Player player) {
		SkillEngine.getInstance().getSkill(getOwner(), 19272, 1, player).useSkill();
		getOwner().getController().delete();
	}
}
