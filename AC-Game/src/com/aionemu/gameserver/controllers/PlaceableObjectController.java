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

import com.aionemu.gameserver.controllers.observer.ActionObserver;
import com.aionemu.gameserver.controllers.observer.ObserverType;
import com.aionemu.gameserver.model.gameobjects.HouseObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.housing.PlaceableHouseObject;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_HOUSE_OBJECT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_HOUSE_OBJECT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import javolution.util.FastMap;

/**
 * @author Rolandas
 */
public class PlaceableObjectController<T extends PlaceableHouseObject> extends VisibleObjectController<HouseObject<T>> {

    FastMap<Integer, ActionObserver> observed = new FastMap<Integer, ActionObserver>().shared();

    @Override
    public void see(VisibleObject object) {
        Player p = (Player) object;
        ActionObserver observer = new ActionObserver(ObserverType.MOVE);
        p.getObserveController().addObserver(observer);
        observed.put(p.getObjectId(), observer);
        PacketSendUtility.sendPacket(p, new SM_HOUSE_OBJECT(getOwner()));
    }

    @Override
    public void notSee(VisibleObject object, boolean isOutOfRange) {
        Player p = (Player) object;
        ActionObserver observer = observed.remove(p.getObjectId());
        if (isOutOfRange) {
            observer.moved();
            PacketSendUtility.sendPacket(p, new SM_DELETE_HOUSE_OBJECT(getOwner().getObjectId()));
        }
        p.getObserveController().removeObserver(observer);
    }

    @Override
    public void onDespawn() {
        getOwner().onDespawn();
    }

    @Override
    public void delete() {
        if (getOwner().isSpawned()) {
            World.getInstance().despawn(getOwner(), false);
        }
        World.getInstance().removeObject(getOwner());
    }

    public void onDialogRequest(Player player) {
        if (!MathUtil.isInRange(getOwner(), player, getOwner().getObjectTemplate().getTalkingDistance() + 2)) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_HOUSING_OBJECT_TOO_FAR_TO_USE);
            return;
        }
        getOwner().onDialogRequest(player);
    }
}
