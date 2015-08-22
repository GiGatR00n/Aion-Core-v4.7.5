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
package com.aionemu.gameserver.controllers;

import javolution.util.FastMap;

import com.aionemu.gameserver.controllers.observer.FlyRingObserver;
import com.aionemu.gameserver.model.flyring.FlyRing;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author xavier
 */
public class FlyRingController extends VisibleObjectController<FlyRing> {

    FastMap<Integer, FlyRingObserver> observed = new FastMap<Integer, FlyRingObserver>().shared();

    @Override
    public void see(VisibleObject object) {
        Player p = (Player) object;
        FlyRingObserver observer = new FlyRingObserver(getOwner(), p);
        p.getObserveController().addObserver(observer);
        observed.put(p.getObjectId(), observer);
    }

    @Override
    public void notSee(VisibleObject object, boolean isOutOfRange) {
        Player p = (Player) object;
        FlyRingObserver observer = observed.remove(p.getObjectId());
        if (isOutOfRange) {
            observer.moved();
        }
        p.getObserveController().removeObserver(observer);
    }
}
