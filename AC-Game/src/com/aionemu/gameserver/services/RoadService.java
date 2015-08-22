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
package com.aionemu.gameserver.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.road.Road;
import com.aionemu.gameserver.model.templates.road.RoadTemplate;
import com.aionemu.gameserver.world.World;

/**
 * @author SheppeR
 */
public class RoadService {

    Logger log = LoggerFactory.getLogger(RoadService.class);

    private static class SingletonHolder {

        protected static final RoadService instance = new RoadService();
    }

    public static final RoadService getInstance() {
        return SingletonHolder.instance;
    }

    private RoadService() {
        for (RoadTemplate rt : DataManager.ROAD_DATA.getRoadTemplates()) {
            for (Integer instanceId : World.getInstance().getWorldMap(rt.getMap()).getAvailableInstanceIds()) {
                Road r = new Road(rt, instanceId);
                r.spawn();
                log.debug("Added " + r.getName() + " at m=" + r.getWorldId() + ",x=" + r.getX() + ",y=" + r.getY() + ",z=" + r.getZ() + " [" + instanceId + "]");
            }
        }
    }
}
