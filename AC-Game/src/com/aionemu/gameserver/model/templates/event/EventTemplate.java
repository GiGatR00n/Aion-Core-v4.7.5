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
package com.aionemu.gameserver.model.templates.event;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.SpawnsData2;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.Guides.GuideTemplate;
import com.aionemu.gameserver.model.templates.spawns.Spawn;
import com.aionemu.gameserver.model.templates.spawns.SpawnMap;
import com.aionemu.gameserver.model.templates.spawns.SpawnSpotTemplate;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.gametime.DateTimeUtil;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventTemplate")
public class EventTemplate {

    private static Logger log = LoggerFactory.getLogger(EventTemplate.class);
    @XmlElement(name = "event_drops", required = false)
    protected EventDrops eventDrops;
    @XmlElement(name = "quests", required = false)
    protected EventQuestList quests;
    @XmlElement(name = "spawns", required = false)
    protected SpawnsData2 spawns;
    @XmlElement(name = "inventory_drop", required = false)
    protected InventoryDrop inventoryDrop;
    @XmlList
    @XmlElement(name = "surveys", required = false)
    protected List<String> surveys;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "start", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlAttribute(name = "end", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlAttribute(name = "theme", required = false)
    private String theme;
    @XmlTransient
    protected List<VisibleObject> spawnedObjects;
    @XmlTransient
    private Future<?> invDropTask = null;

    public String getName() {
        return name;
    }

    public EventDrops EventDrop() {
        return eventDrops;
    }

    public DateTime getStartDate() {
        return DateTimeUtil.getDateTime(startDate.toGregorianCalendar());
    }

    public DateTime getEndDate() {
        return DateTimeUtil.getDateTime(endDate.toGregorianCalendar());
    }

    public List<Integer> getStartableQuests() {
        if (quests == null) {
            return new ArrayList<Integer>();
        }
        return quests.getStartableQuests();
    }

    public List<Integer> getMaintainableQuests() {
        if (quests == null) {
            return new ArrayList<Integer>();
        }
        return quests.getMaintainQuests();
    }

    public boolean isActive() {
        return getStartDate().isBeforeNow() && getEndDate().isAfterNow();
    }

    public boolean isExpired() {
        return !isActive();
    }

    @XmlTransient
    volatile boolean isStarted = false;

    public void setStarted() {
        isStarted = true;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void Start() {
        if (isStarted) {
            return;
        }

        if (spawns != null && spawns.size() > 0) {
            if (spawnedObjects == null) {
                spawnedObjects = new ArrayList<VisibleObject>();
            }
            for (SpawnMap map : spawns.getTemplates()) {
                DataManager.SPAWNS_DATA2.addNewSpawnMap(map);
                Collection<Integer> instanceIds = World.getInstance().getWorldMap(map.getMapId()).getAvailableInstanceIds();
                for (Integer instanceId : instanceIds) {
                    int spawnCount = 0;
                    for (Spawn spawn : map.getSpawns()) {
                        spawn.setEventTemplate(this);
                        for (SpawnSpotTemplate spot : spawn.getSpawnSpotTemplates()) {
                            SpawnTemplate t = SpawnEngine.addNewSpawn(map.getMapId(), spawn.getNpcId(), spot.getX(), spot.getY(),
                                    spot.getZ(), spot.getHeading(), spawn.getRespawnTime());
                            t.setEventTemplate(this);
                            SpawnEngine.spawnObject(t, instanceId);
                            spawnCount++;
                        }
                    }
                    log.info("Spawned event objects in " + map.getMapId() + " [" + instanceId + "] : " + spawnCount + " (" + this.getName() + ")");
                }
            }
            DataManager.SPAWNS_DATA2.afterUnmarshal(null, null);
            DataManager.SPAWNS_DATA2.clearTemplates();
        }

        if (inventoryDrop != null) {
            invDropTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                        @Override
                        public void visit(Player player) {
                            if (player.getCommonData().getLevel() >= inventoryDrop.getStartLevel()) {
                                ItemService.dropItemToInventory(player, inventoryDrop.getDropItem());
                            }
                        }
                    });
                }
            }, inventoryDrop.getInterval() * 60000, inventoryDrop.getInterval() * 60000);
        }

        if (surveys != null) {
            for (String survey : surveys) {
                GuideTemplate template = DataManager.GUIDE_HTML_DATA.getTemplateByTitle(survey);
                if (template != null) {
                    template.setActivated(true);
                }
            }
        }

        isStarted = true;
    }

    public void Stop() {
        if (!isStarted) {
            return;
        }

        if (spawnedObjects != null) {
            for (VisibleObject o : spawnedObjects) {
                if (o.isSpawned()) {
                    o.getController().delete();
                }
            }
            DataManager.SPAWNS_DATA2.removeEventSpawnObjects(spawnedObjects);
            log.info("Despawned " + spawnedObjects.size() + " event objects (" + this.getName() + ")");
            spawnedObjects.clear();
            spawnedObjects = null;
        }

        if (invDropTask != null) {
            invDropTask.cancel(false);
            invDropTask = null;
        }

        if (surveys != null) {
            for (String survey : surveys) {
                GuideTemplate template = DataManager.GUIDE_HTML_DATA.getTemplateByTitle(survey);
                if (template != null) {
                    template.setActivated(false);
                }
            }
        }

        isStarted = false;
    }

    public void addSpawnedObject(VisibleObject object) {
        if (spawnedObjects == null) {
            spawnedObjects = new ArrayList<VisibleObject>();
        }
        spawnedObjects.add(object);
    }

    /**
     * @return the theme name
     */
    public String getTheme() {
        if (theme != null) {
            return theme.toLowerCase();
        }
        return theme;
    }
}
