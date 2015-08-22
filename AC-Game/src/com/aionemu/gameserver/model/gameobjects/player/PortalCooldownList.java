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

import com.aionemu.gameserver.network.aion.serverpackets.SM_INSTANCE_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer
 */
public class PortalCooldownList {

    private Player owner;
    private FastMap<Integer, Long> portalCooldowns;

    /**
     * @param owner
     */
    PortalCooldownList(Player owner) {
        this.owner = owner;
    }

    /**
     * @param worldId * @return
     */
    public boolean isPortalUseDisabled(int worldId) {
        if (portalCooldowns == null || !portalCooldowns.containsKey(worldId)) {
            return false;
        }

        Long coolDown = portalCooldowns.get(worldId);
        if (coolDown == null) {
            return false;
        }

        if (coolDown < System.currentTimeMillis()) {
            portalCooldowns.remove(worldId);
            return false;
        }

        return true;
    }

    /**
     * @param worldId
     * @return
     */
    public long getPortalCooldown(int worldId) {
        if (portalCooldowns == null || !portalCooldowns.containsKey(worldId)) {
            return 0;
        }

        return portalCooldowns.get(worldId);
    }

    public FastMap<Integer, Long> getPortalCoolDowns() {
        return portalCooldowns;
    }

    public void setPortalCoolDowns(FastMap<Integer, Long> portalCoolDowns) {
        this.portalCooldowns = portalCoolDowns;
    }

    /**
     * @param worldId
     * @param time
     */
    public void addPortalCooldown(int worldId, long useDelay) {
        if (portalCooldowns == null) {
            portalCooldowns = new FastMap<Integer, Long>();
        }
        portalCooldowns.put(worldId, useDelay);

        if (owner.isInTeam()) {
            owner.getCurrentTeam().sendPacket(new SM_INSTANCE_INFO(owner, worldId));
        } else {
            PacketSendUtility.sendPacket(owner, new SM_INSTANCE_INFO(owner, worldId));
        }
    }

    /**
     * @param worldId
     */
    public void removePortalCoolDown(int worldId) {
        if (portalCooldowns != null) {
            portalCooldowns.remove(worldId);
        }
    }

    /**
     * @return
     */
    public boolean hasCooldowns() {
        return portalCooldowns != null && portalCooldowns.size() > 0;
    }

    /**
     * @return
     */
    public int size() {
        return portalCooldowns != null ? portalCooldowns.size() : 0;
    }
}
