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
package com.aionemu.gameserver.geoEngine.scene.mesh;

import com.aionemu.gameserver.geoEngine.collision.Collidable;
import com.aionemu.gameserver.geoEngine.collision.CollisionResults;
import com.aionemu.gameserver.geoEngine.math.Ray;
import com.aionemu.gameserver.geoEngine.scene.Geometry;
import com.aionemu.gameserver.geoEngine.scene.Mesh;

import java.util.BitSet;

/**
 * @author MrPoke, Rolandas
 */
public class DoorGeometry extends Geometry {

    BitSet instances = new BitSet();
    private boolean foundTemplate = false;

    public DoorGeometry(String name, Mesh mesh) {
        super(name, mesh);
    }

    public void setDoorState(int instanceId, boolean isOpened) {
        instances.set(instanceId, isOpened);
    }

    @Override
    public int collideWith(Collidable other, CollisionResults results) {
        if (foundTemplate && instances.get(results.getInstanceId())) {
            return 0;
        }
        if (other instanceof Ray) {
            // no collision if inside arena spheres, so just check volume
            return getWorldBound().collideWith(other, results);
        }
        return super.collideWith(other, results);
    }

    public boolean isFoundTemplate() {
        return foundTemplate;
    }

    public void setFoundTemplate(boolean foundTemplate) {
        this.foundTemplate = foundTemplate;
    }

    @Override
    public void updateModelBound() {
        // duplicate call distorts world bounds, thus do only once
        if (worldBound == null) {
            mesh.updateBound();
            worldBound = getModelBound().transform(cachedWorldMat, worldBound);
        }
    }
}
