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

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.TownDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.TribeClass;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.model.templates.housing.HouseAddress;
import com.aionemu.gameserver.model.templates.housing.HousingLand;
import com.aionemu.gameserver.model.town.Town;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TOWNS_LIST;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.MapRegion;
import com.aionemu.gameserver.world.zone.ZoneInstance;

/**
 * @author ViAl
 */
public class TownService {

    private static final Logger log = LoggerFactory.getLogger(TownService.class);
    private Map<Integer, Town> elyosTowns;
    private Map<Integer, Town> asmosTowns;

    private static class SingletonHolder {

        protected static final TownService instance = new TownService();
    }

    public static final TownService getInstance() {
        return SingletonHolder.instance;
    }

    private TownService() {
        elyosTowns = DAOManager.getDAO(TownDAO.class).load(Race.ELYOS);
        asmosTowns = DAOManager.getDAO(TownDAO.class).load(Race.ASMODIANS);
        if (elyosTowns.size() == 0 && asmosTowns.size() == 0) {
            for (HousingLand land : DataManager.HOUSE_DATA.getLands()) {
                for (HouseAddress address : land.getAddresses()) {
                    if (address.getTownId() == 0) {
                        continue;
                    } else {
                        Race townRace = DataManager.NPC_DATA.getNpcTemplate(land.getManagerNpcId()).getTribe() == TribeClass.GENERAL ? Race.ELYOS
                                : Race.ASMODIANS;
                        if ((townRace == Race.ELYOS && !elyosTowns.containsKey(address.getTownId()))
                                || (townRace == Race.ASMODIANS && !asmosTowns.containsKey(address.getTownId()))) {
                            Town town = new Town(address.getTownId(), townRace);
                            if (townRace == Race.ELYOS) {
                                elyosTowns.put(town.getId(), town);
                            } else {
                                asmosTowns.put(town.getId(), town);
                            }
                            DAOManager.getDAO(TownDAO.class).store(town);
                        }

                    }
                }
            }
        }
        log.info("Loaded " + asmosTowns.size() + " elyos towns.");
        log.info("Loaded " + asmosTowns.size() + " asmodians towns.");
    }

    public Town getTownById(int townId) {
        if (elyosTowns.containsKey(townId)) {
            return elyosTowns.get(townId);
        } else {
            return asmosTowns.get(townId);
        }
    }

    public int getTownResidence(Player player) {
        House house = player.getActiveHouse();
        if (house == null) {
            return 0;
        } else {
            return house.getAddress().getTownId();
        }
    }

    public int getTownIdByPosition(Creature creature) {
        if (creature instanceof Npc) {
            if (((Npc) creature).getTownId() != 0) {
                return ((Npc) creature).getTownId();
            }
        }
        int townId = 0;
        MapRegion region = creature.getPosition().getMapRegion();
        if (region == null) {
            log.warn("TownService: npc " + creature.getName() + " haven't any map region!");
            return 0;
        } else {
            List<ZoneInstance> zones = region.getZones(creature);
            for (ZoneInstance zone : zones) {
                townId = zone.getTownId();
                if (townId > 0) {
                    break;
                }
            }
        }
        return townId;
    }

    public void onEnterWorld(Player player) {
        switch (player.getRace()) {
            case ELYOS:
                if (player.getWorldId() == 700010000) {
                    PacketSendUtility.sendPacket(player, new SM_TOWNS_LIST(elyosTowns));
                }
                break;
            case ASMODIANS:
                if (player.getWorldId() == 710010000) {
                    PacketSendUtility.sendPacket(player, new SM_TOWNS_LIST(asmosTowns));
                }
                break;
        }
    }
}
