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

import com.aionemu.gameserver.controllers.RVController;
import com.aionemu.gameserver.controllers.effect.EffectController;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.rift.RiftLocation;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.model.templates.spawns.SpawnGroup2;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.model.vortex.VortexLocation;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.NpcKnownList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Source
 */
public class RiftManager {

    private static Logger log = LoggerFactory.getLogger(RiftManager.class);
    private static List<Npc> rifts = new ArrayList<Npc>();
    private static Map<String, SpawnTemplate> riftGroups = new HashMap<String, SpawnTemplate>();

    public static void addRiftSpawnTemplate(SpawnGroup2 spawn) {
        if (spawn.hasPool()) {
            SpawnTemplate template = spawn.getSpawnTemplates().get(0);
            riftGroups.put(template.getAnchor(), template);
        } else {
            for (SpawnTemplate template : spawn.getSpawnTemplates()) {
                riftGroups.put(template.getAnchor(), template);
            }
        }
    }

    public void spawnRift(RiftLocation loc) {
        RiftEnum rift = RiftEnum.getRift(loc.getId());
        spawnRift(rift, null, loc);
    }

    public void spawnVortex(VortexLocation loc) {
        RiftEnum rift = RiftEnum.getVortex(loc.getDefendersRace());
        spawnRift(rift, loc, null);
    }

    private void spawnRift(RiftEnum rift, VortexLocation vl, RiftLocation rl) {
        SpawnTemplate masterTemplate = riftGroups.get(rift.getMaster());
        SpawnTemplate slaveTemplate = riftGroups.get(rift.getSlave());

        if (masterTemplate == null || slaveTemplate == null) {
            return;
        }

        int spawned = 0;
        int instanceCount = World.getInstance().getWorldMap(masterTemplate.getWorldId()).getInstanceCount();

        if (slaveTemplate.hasPool()) {
            slaveTemplate = slaveTemplate.changeTemplate(1);
        }

        for (int i = 1; i <= instanceCount; i++) {
            Npc slave = spawnInstance(i, slaveTemplate, new RVController(null, rift));
            Npc master = spawnInstance(i, masterTemplate, new RVController(slave, rift));

            if (rift.isVortex()) {
                vl.setVortexController((RVController) master.getController());
                spawned = vl.getSpawned().size();
                vl.getSpawned().add(master);
                vl.getSpawned().add(slave);
            } else {
                spawned = rl.getSpawned().size();
                rl.getSpawned().add(master);
                rl.getSpawned().add(slave);
            }
        }

        log.info("Rift opened: " + rift.name() + " successfully spawned " + spawned + " Npc.");
    }

    private Npc spawnInstance(int instance, SpawnTemplate template, RVController controller) {
        NpcTemplate masterObjectTemplate = DataManager.NPC_DATA.getNpcTemplate(template.getNpcId());
        Npc npc = new Npc(IDFactory.getInstance().nextId(), controller, template, masterObjectTemplate);

        npc.setKnownlist(new NpcKnownList(npc));
        npc.setEffectController(new EffectController(npc));

        World world = World.getInstance();
        world.storeObject(npc);
        world.setPosition(npc, template.getWorldId(), instance, template.getX(),
                template.getY(), template.getZ(), template.getHeading());
        world.spawn(npc);
        rifts.add(npc);

        return npc;
    }

    public static List<Npc> getSpawned() {
        return rifts;
    }

    public static RiftManager getInstance() {
        return RiftManagerHolder.INSTANCE;
    }

    private static class RiftManagerHolder {

        private static final RiftManager INSTANCE = new RiftManager();
    }
}
