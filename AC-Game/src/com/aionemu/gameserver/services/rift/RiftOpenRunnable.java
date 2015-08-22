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
package com.aionemu.gameserver.services.rift;

import com.aionemu.gameserver.model.rift.RiftLocation;
import com.aionemu.gameserver.services.RiftService;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.Map;

/**
 * @author Source
 */
public class RiftOpenRunnable implements Runnable {

    private final int worldId;
    private final boolean guards;

    public RiftOpenRunnable(int worldId, boolean guards) {
        this.worldId = worldId;
        this.guards = guards;
    }

    @Override
    public void run() {
        Map<Integer, RiftLocation> locations = RiftService.getInstance().getRiftLocations();
        for (RiftLocation loc : locations.values()) {
            if (loc.getWorldId() == worldId) {
                RiftService.getInstance().openRifts(loc, guards);
            }
        }

        // Scheduled rifts close
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                RiftService.getInstance().closeRifts();
            }
        }, RiftService.getInstance().getDuration() * 3540 * 1000);
        // Broadcast rift spawn on map
        RiftInformer.sendRiftsInfo(worldId);
    }
}
