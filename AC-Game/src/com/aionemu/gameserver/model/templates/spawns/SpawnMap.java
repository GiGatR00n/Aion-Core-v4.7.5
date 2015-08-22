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
package com.aionemu.gameserver.model.templates.spawns;

import com.aionemu.gameserver.model.templates.spawns.basespawns.BaseSpawn;
import com.aionemu.gameserver.model.templates.spawns.beritraspawns.BeritraSpawn;
import com.aionemu.gameserver.model.templates.spawns.riftspawns.RiftSpawn;
import com.aionemu.gameserver.model.templates.spawns.siegespawns.SiegeSpawn;
import com.aionemu.gameserver.model.templates.spawns.vortexspawns.VortexSpawn;

import javax.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xTz
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "SpawnMap")
public class SpawnMap {

    @XmlElement(name = "spawn")
    private List<Spawn> spawns;
    @XmlElement(name = "base_spawn")
    private List<BaseSpawn> baseSpawns;
    @XmlElement(name = "rift_spawn")
    private List<RiftSpawn> riftSpawns;
    @XmlElement(name = "siege_spawn")
    private List<SiegeSpawn> siegeSpawns;
    @XmlElement(name = "vortex_spawn")
    private List<VortexSpawn> vortexSpawns;
    @XmlElement(name = "beritra_spawn")
	private List<BeritraSpawn> beritraSpawns;
    @XmlAttribute(name = "map_id")
    private int mapId;

    public SpawnMap() {
    }

    public SpawnMap(int mapId) {
        this.mapId = mapId;
    }

    public int getMapId() {
        return mapId;
    }

    public List<Spawn> getSpawns() {
        if (spawns == null) {
            spawns = new ArrayList<Spawn>();
        }
        return spawns;
    }

    public void addSpawns(Spawn spawns) {
        getSpawns().add(spawns);
    }

    public void removeSpawns(Spawn spawns) {
        getSpawns().remove(spawns);
    }

    public List<BaseSpawn> getBaseSpawns() {
        if (baseSpawns == null) {
            baseSpawns = new ArrayList<BaseSpawn>();
        }
        return baseSpawns;
    }

    public void addBaseSpawns(BaseSpawn spawns) {
        getBaseSpawns().add(spawns);
    }

    public List<RiftSpawn> getRiftSpawns() {
        if (riftSpawns == null) {
            riftSpawns = new ArrayList<RiftSpawn>();
        }
        return riftSpawns;
    }

    public void addRiftSpawns(RiftSpawn spawns) {
        getRiftSpawns().add(spawns);
    }

    public List<SiegeSpawn> getSiegeSpawns() {
        if (siegeSpawns == null) {
            siegeSpawns = new ArrayList<SiegeSpawn>();
        }
        return siegeSpawns;
    }

    public void addSiegeSpawns(SiegeSpawn spawns) {
        getSiegeSpawns().add(spawns);
    }

    public List<VortexSpawn> getVortexSpawns() {
        if (vortexSpawns == null) {
            vortexSpawns = new ArrayList<VortexSpawn>();
        }
        return vortexSpawns;
    }

    public void addVortexSpawns(VortexSpawn spawns) {
        getVortexSpawns().add(spawns);
    }
    
    public List<BeritraSpawn> getBeritraSpawns() {
		if (beritraSpawns == null) {
			beritraSpawns = new ArrayList<BeritraSpawn>();
		}
		return beritraSpawns;
	}
	
	public void addBeritraSpawns(BeritraSpawn spawns) {
		getBeritraSpawns().add(spawns);
	}
}
