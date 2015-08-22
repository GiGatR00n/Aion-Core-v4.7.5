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
package com.aionemu.gameserver.model.templates.housing;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Land", propOrder = {"addresses", "buildings", "sale", "fee", "caps"})
public class HousingLand {

    @XmlElementWrapper(name = "addresses", required = true)
    @XmlElement(name = "address")
    protected List<HouseAddress> addresses;
    @XmlElementWrapper(name = "buildings", required = true)
    @XmlElement(name = "building")
    protected List<Building> buildings;
    @XmlElement(required = true)
    protected Sale sale;
    @XmlElement(required = true)
    protected long fee;
    @XmlElement(required = true)
    protected BuildingCapabilities caps;
    @XmlAttribute(name = "sign_nosale", required = true)
    protected int signNosale;
    @XmlAttribute(name = "sign_sale", required = true)
    protected int signSale;
    @XmlAttribute(name = "sign_waiting", required = true)
    protected int signWaiting;
    @XmlAttribute(name = "sign_home", required = true)
    protected int signHome;
    @XmlAttribute(name = "manager_npc", required = true)
    protected int managerNpc;
    @XmlAttribute(name = "teleport_npc", required = true)
    protected int teleportNpc;
    @XmlAttribute(required = true)
    protected int id;

    public List<HouseAddress> getAddresses() {
        return addresses;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public Building getDefaultBuilding() {
        for (Building building : buildings) {
            if (building.isDefault()) {
                return building;
            }
        }
        return buildings.get(0); // fail
    }

    public Sale getSaleOptions() {
        return sale;
    }

    public long getMaintenanceFee() {
        return fee;
    }

    public BuildingCapabilities getCapabilities() {
        return caps;
    }

    public int getNosaleSignNpcId() {
        return signNosale;
    }

    public int getSaleSignNpcId() {
        return signSale;
    }

    public void setSignSale(int value) {
        this.signSale = value;
    }

    public int getWaitingSignNpcId() {
        return signWaiting;
    }

    public int getHomeSignNpcId() {
        return signHome;
    }

    public int getManagerNpcId() {
        return managerNpc;
    }

    public int getTeleportNpcId() {
        return teleportNpc;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
