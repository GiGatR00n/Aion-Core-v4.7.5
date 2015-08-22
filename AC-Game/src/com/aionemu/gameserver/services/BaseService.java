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

import java.util.Map;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.base.BaseLocation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_NPC_INFO;
import com.aionemu.gameserver.services.base.Base;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Source
 */
public class BaseService {

    private static final Logger log = LoggerFactory.getLogger(BaseService.class);
    private final Map<Integer, Base<?>> active = new FastMap<Integer, Base<?>>().shared();
    private Map<Integer, BaseLocation> bases;

    public void initBaseLocations() {
        log.info("Loading Bases...");
        bases = DataManager.BASE_DATA.getBaseLocations();
        log.info("Loaded " + bases.size() + " bases.");
    }

    public void initBases() {
        log.info("Init Bases...");
        for (BaseLocation base : getBaseLocations().values()) {
            start(base.getId());
        }
    }

    public Map<Integer, BaseLocation> getBaseLocations() {
        return bases;
    }

    public BaseLocation getBaseLocation(int id) {
        return bases.get(id);
    }

    public void start(final int id) {
        final Base<?> base;

        synchronized (this) {
            if (active.containsKey(id)) {
                return;
            }
            base = new Base<BaseLocation>(getBaseLocation(id));
            active.put(id, base);
        }

        base.start();
    }

    public void stop(int id) {
        if (!isActive(id)) {
            log.info("Trying to stop not active base:" + id);
            return;
        }

        Base<?> base;
        synchronized (this) {
            base = active.remove(id);
        }

        if (base == null || base.isFinished()) {
            log.info("Trying to stop null or finished base:" + id);
            return;
        }

        base.stop();
        start(id);
    }

    public void capture(int id, Race race) {
        if (!isActive(id)) {
            log.info("Detecting not active base capture.");
            return;
        }

        getActiveBase(id).setRace(race);
        stop(id);
        broadcastUpdate(getBaseLocation(id));
    }

    public boolean isActive(int id) {
        return active.containsKey(id);
    }

    public Base<?> getActiveBase(int id) {
        return active.get(id);
    }

    public void onEnterBaseWorld(Player player) {
        for (BaseLocation baseLocation : getBaseLocations().values()) {
            if (baseLocation.getWorldId() == player.getWorldId() && isActive(baseLocation.getId())) {
                Base<?> base = getActiveBase(baseLocation.getId());
                PacketSendUtility.sendPacket(player, new SM_NPC_INFO(base.getFlag(), player));
                player.getController().updateNearbyQuests();
            }
        }
    }

    public void broadcastUpdate(final BaseLocation baseLocation) {
        World.getInstance().getWorldMap(baseLocation.getWorldId()).getMainWorldMapInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                if (isActive(baseLocation.getId())) {
                    Base<?> base = getActiveBase(baseLocation.getId());
                    PacketSendUtility.sendPacket(player, new SM_NPC_INFO(base.getFlag(), player));
                    player.getController().updateNearbyQuests();
                }
            }
        });
    }

    public static BaseService getInstance() {
        return BaseServiceHolder.INSTANCE;
    }

    private static class BaseServiceHolder {

        private static final BaseService INSTANCE = new BaseService();
    }
}
