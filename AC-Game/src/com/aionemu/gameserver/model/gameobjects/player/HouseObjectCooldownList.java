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
package com.aionemu.gameserver.model.gameobjects.player;

import javolution.util.FastMap;

/**
 * @author Rolandas
 */
public class HouseObjectCooldownList {

    private FastMap<Integer, Long> houseObjectCooldowns;

    HouseObjectCooldownList(Player owner) {
    }

    public boolean isCanUseObject(int objectId) {
        if (houseObjectCooldowns == null || !houseObjectCooldowns.containsKey(objectId)) {
            return true;
        }

        Long coolDown = houseObjectCooldowns.get(objectId);
        if (coolDown == null) {
            return true;
        }

        if (coolDown < System.currentTimeMillis()) {
            houseObjectCooldowns.remove(objectId);
            return true;
        }

        return false;
    }

    public long getHouseObjectCooldown(int objectId) {
        if (houseObjectCooldowns == null || !houseObjectCooldowns.containsKey(objectId)) {
            return 0;
        }

        return houseObjectCooldowns.get(objectId);
    }

    public FastMap<Integer, Long> getHouseObjectCooldowns() {
        return houseObjectCooldowns;
    }

    public void setHouseObjectCooldowns(FastMap<Integer, Long> houseObjectCooldowns) {
        this.houseObjectCooldowns = houseObjectCooldowns;
    }

    public void addHouseObjectCooldown(int objectId, int delay) {
        if (houseObjectCooldowns == null) {
            houseObjectCooldowns = new FastMap<Integer, Long>();
        }

        long nextUseTime = System.currentTimeMillis() + (delay * 1000);
        houseObjectCooldowns.put(objectId, nextUseTime);
    }

    public int getReuseDelay(int objectId) {
        if (isCanUseObject(objectId)) {
            return 0;
        }
        long cd = getHouseObjectCooldown(objectId);
        int delay = (int) ((cd - System.currentTimeMillis()) / 1000);
        return delay;
    }
}
