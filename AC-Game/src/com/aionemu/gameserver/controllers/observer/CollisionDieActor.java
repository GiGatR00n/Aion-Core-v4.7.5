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
package com.aionemu.gameserver.controllers.observer;

import com.aionemu.gameserver.configs.main.GeoDataConfig;
import com.aionemu.gameserver.geoEngine.collision.CollisionIntention;
import com.aionemu.gameserver.geoEngine.collision.CollisionResult;
import com.aionemu.gameserver.geoEngine.collision.CollisionResults;
import com.aionemu.gameserver.geoEngine.scene.Spatial;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Rolandas
 */
public class CollisionDieActor extends AbstractCollisionObserver implements IActor {

    private boolean isEnabled = true;

    public CollisionDieActor(Creature creature, Spatial geometry) {
        super(creature, geometry, CollisionIntention.MATERIAL.getId());
    }

    @Override
    public void setEnabled(boolean enable) {
        isEnabled = enable;
    }

    @Override
    public void onMoved(CollisionResults collisionResults) {
        if (isEnabled && collisionResults.size() != 0) {
            if (GeoDataConfig.GEO_MATERIALS_SHOWDETAILS && creature instanceof Player) {
                Player player = (Player) creature;
                if (player.isGM()) {
                    CollisionResult result = collisionResults.getClosestCollision();
                    PacketSendUtility.sendMessage(player, "Entered " + result.getGeometry().getName());
                }
            }
            act();
        }
    }

    @Override
    public void act() {
        if (isEnabled) {
            creature.getController().die();
        }
    }

    @Override
    public void abort() {
        // Nothing to do
    }
}
