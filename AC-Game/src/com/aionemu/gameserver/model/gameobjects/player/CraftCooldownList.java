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
 * @author synchro2
 */
public class CraftCooldownList {

    private FastMap<Integer, Long> craftCooldowns;

    CraftCooldownList(Player owner) {
    }

    public boolean isCanCraft(int delayId) {
        if (craftCooldowns == null || !craftCooldowns.containsKey(delayId)) {
            return true;
        }

        Long coolDown = craftCooldowns.get(delayId);
        if (coolDown == null) {
            return true;
        }

        if (coolDown < System.currentTimeMillis()) {
            craftCooldowns.remove(delayId);
            return true;
        }

        return false;
    }

    public long getCraftCooldown(int delayId) {
        if (craftCooldowns == null || !craftCooldowns.containsKey(delayId)) {
            return 0;
        }

        return craftCooldowns.get(delayId);
    }

    public FastMap<Integer, Long> getCraftCoolDowns() {
        return craftCooldowns;
    }

    public void setCraftCoolDowns(FastMap<Integer, Long> craftCoolDowns) {
        this.craftCooldowns = craftCoolDowns;
    }

    public void addCraftCooldown(int delayId, int delay) {
        if (craftCooldowns == null) {
            craftCooldowns = new FastMap<Integer, Long>();
        }

        long nextUseTime = System.currentTimeMillis() + (delay * 1000);
        craftCooldowns.put(delayId, nextUseTime);
    }
}
