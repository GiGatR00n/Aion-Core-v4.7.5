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

package ai;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.geoEngine.collision.CollisionIntention;
import com.aionemu.gameserver.geoEngine.math.Vector3f;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PositionUtil;
import com.aionemu.gameserver.world.geo.GeoService;

/**
 *
 * @author Steve
 * @AE implement Wasacacax
 */
@AIName("fear_dummy")
public class FearDummyAI2 extends GeneralNpcAI2 {

	@Override
	public int modifyDamage(int damage) {
		return 1;
	}

	@Override
	protected void handleAttack(Creature creature) {
		float x = getOwner().getX();
		float y = getOwner().getY();
		if (!MathUtil.isNearCoordinates(getOwner(), creature, 40)) {
			return;
		}
		byte moveAwayHeading = PositionUtil.getMoveAwayHeading(creature, getOwner());
		double radian = Math.toRadians(MathUtil.convertHeadingToDegree(moveAwayHeading));
		float maxDistance = getOwner().getGameStats().getMovementSpeedFloat();
		float x1 = (float) (Math.cos(radian) * maxDistance);
		float y1 = (float) (Math.sin(radian) * maxDistance);
		byte intentions = (byte) (CollisionIntention.PHYSICAL.getId() | CollisionIntention.DOOR.getId());
		Vector3f closestCollision = GeoService.getInstance().getClosestCollision(getOwner(), x + x1, y + y1, getOwner().getZ(), true, intentions);
		if (getOwner().isFlying()) {
			closestCollision.setZ(getOwner().getZ());
		}
		getOwner().getMoveController().resetMove();
		getOwner().getMoveController().moveToPoint(closestCollision.getX(), closestCollision.getY(), closestCollision.getZ());
	}

}
