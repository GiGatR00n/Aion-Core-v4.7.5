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
package zone;

import com.aionemu.gameserver.controllers.observer.CollisionDieActor;
import com.aionemu.gameserver.geoEngine.GeoWorldLoader;
import com.aionemu.gameserver.geoEngine.math.Matrix3f;
import com.aionemu.gameserver.geoEngine.math.Vector3f;
import com.aionemu.gameserver.geoEngine.scene.Node;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import com.aionemu.gameserver.world.zone.handler.ZoneHandler;
import com.aionemu.gameserver.world.zone.handler.ZoneNameAnnotation;
import javolution.util.FastMap;

import java.io.IOException;

/**
 * @author MrPoke
 */
@ZoneNameAnnotation("CORE_400010000")
public class AbyssCore implements ZoneHandler {

    FastMap<Integer, CollisionDieActor> observed = new FastMap<Integer, CollisionDieActor>();
    private Node geometry;

    public AbyssCore() {
        try {
            this.geometry = (Node) GeoWorldLoader.loadMeshs("data/geo/models/na_ab_lmark_col_01a.mesh").values().toArray()[0];
            this.geometry.setTransform(new Matrix3f(1.15f, 0, 0, 0, 1.15f, 0, 0, 0, 1.15f),
                    new Vector3f(2140.104f, 1925.5823f, 2303.919f), 1f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        geometry.updateModelBound();
    }

    @Override
    public void onEnterZone(Creature creature, ZoneInstance zone) {
        Creature acting = creature.getActingCreature();
        if (acting instanceof Player && !((Player) acting).isGM()) {

            CollisionDieActor observer = new CollisionDieActor(creature, geometry);
            creature.getObserveController().addObserver(observer);
            observed.put(creature.getObjectId(), observer);
        }
    }

    @Override
    public void onLeaveZone(Creature creature, ZoneInstance zone) {
        Creature acting = creature.getActingCreature();
        if (acting instanceof Player && !((Player) acting).isGM()) {
            CollisionDieActor observer = observed.get(creature.getObjectId());
            if (observer != null) {
                creature.getObserveController().removeObserver(observer);
                observed.remove(creature.getObjectId());
            }
        }
    }
}
