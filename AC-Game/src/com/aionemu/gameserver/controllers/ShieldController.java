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

import com.aionemu.gameserver.controllers.observer.ActionObserver;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.shield.Shield;
import com.aionemu.gameserver.model.siege.FortressLocation;
import com.aionemu.gameserver.model.siege.SiegeRace;
import com.aionemu.gameserver.services.ShieldService;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.world.World;

/**
 * @author Source
 */
public class ShieldController extends VisibleObjectController<Shield> {

    FastMap<Integer, ActionObserver> observed = new FastMap<Integer, ActionObserver>().shared();

    @Override
    public void see(VisibleObject object) {
        FortressLocation loc = SiegeService.getInstance().getFortress(getOwner().getId());
        Player player = (Player) object;

        if (loc.isUnderShield()) {
            if (loc.getRace() != SiegeRace.getByRace(player.getRace())) {
                ActionObserver observer = ShieldService.getInstance().createShieldObserver(loc.getLocationId(), player);
                if (observer != null) {
                    player.getObserveController().addObserver(observer);
                    observed.put(player.getObjectId(), observer);
                }
            }
        }
    }

    @Override
    public void notSee(VisibleObject object, boolean isOutOfRange) {
        FortressLocation loc = SiegeService.getInstance().getFortress(getOwner().getId());
        Player player = (Player) object;

        if (loc.isUnderShield()) {
            if (loc.getRace() != SiegeRace.getByRace(player.getRace())) {
                ActionObserver observer = observed.remove(player.getObjectId());
                if (observer != null) {
                    if (isOutOfRange) {
                        observer.moved();
                    }

                    player.getObserveController().removeObserver(observer);
                }
            }
        }
    }

    public void disable() {
        for (FastMap.Entry<Integer, ActionObserver> e = observed.head(), mapEnd = observed.tail(); (e = e.getNext()) != mapEnd; ) {
            ActionObserver observer = observed.remove(e.getKey());
            Player player = World.getInstance().findPlayer(e.getKey());
            if (player != null) {
                player.getObserveController().removeObserver(observer);
            }
        }
    }
}
