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

import com.aionemu.gameserver.model.templates.event.EventTemplate;
import com.aionemu.gameserver.spawnengine.SpawnHandlerType;

import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xTz
 * @modified Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Spawn")
public class Spawn {

    @XmlAttribute(name = "npc_id", required = true)
    private int npcId;
    @XmlAttribute(name = "respawn_time")
    private Integer respawnTime = 0;
    @XmlAttribute(name = "pool")
    private Integer pool = 0;
    @XmlAttribute(name = "difficult_id")
    private byte difficultId;
    @XmlAttribute(name = "custom")
    private Boolean isCustom = false;
    @XmlAttribute(name = "handler")
    private SpawnHandlerType handler;
    @XmlElement(name = "temporary_spawn")
    private TemporarySpawn temporaySpawn;
    @XmlElement(name = "spot")
    private List<SpawnSpotTemplate> spawnTemplates;
    @XmlTransient
    private EventTemplate eventTemplate;

    public Spawn() {
    }

    public Spawn(int npcId, int respawnTime, SpawnHandlerType handler) {
        this.npcId = npcId;
        this.respawnTime = respawnTime;
        this.handler = handler;
    }

    void beforeMarshal(Marshaller marshaller) {
        if (pool == 0) {
            pool = null;
        }
        if (isCustom == false) {
            isCustom = null;
        }
    }

    void afterMarshal(Marshaller marshaller) {
        if (isCustom == null) {
            isCustom = false;
        }
        if (pool == null) {
            pool = 0;
        }
    }

    public int getNpcId() {
        return npcId;
    }

    public int getPool() {
        return pool;
    }

    public TemporarySpawn getTemporarySpawn() {
        return temporaySpawn;
    }

    public int getRespawnTime() {
        return respawnTime;
    }

    public SpawnHandlerType getSpawnHandlerType() {
        return handler;
    }

    public List<SpawnSpotTemplate> getSpawnSpotTemplates() {
        if (spawnTemplates == null) {
            spawnTemplates = new ArrayList<SpawnSpotTemplate>();
        }
        return spawnTemplates;
    }

    public void addSpawnSpot(SpawnSpotTemplate template) {
        getSpawnSpotTemplates().add(template);
    }

    public boolean isCustom() {
        return isCustom == null ? false : isCustom;
    }

    public void setCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public boolean isEventSpawn() {
        return eventTemplate != null;
    }

    public EventTemplate getEventTemplate() {
        return eventTemplate;
    }

    public void setEventTemplate(EventTemplate eventTemplate) {
        this.eventTemplate = eventTemplate;
    }

    public byte getDifficultId() {
        return difficultId;
    }
}
