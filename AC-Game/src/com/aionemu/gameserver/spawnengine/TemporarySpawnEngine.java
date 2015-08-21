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
package com.aionemu.gameserver.spawnengine;

import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.templates.spawns.SpawnGroup2;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.model.templates.spawns.TemporarySpawn;
import javolution.util.FastList;
import javolution.util.FastMap;

import java.util.HashSet;

/**
 * @author xTz
 */
public class TemporarySpawnEngine {

    private static final FastList<SpawnGroup2> temporarySpawns = new FastList<SpawnGroup2>();
    private static final FastMap<SpawnGroup2, HashSet<Integer>> tempSpawnInstanceMap = new FastMap<SpawnGroup2, HashSet<Integer>>();

    public static void spawnAll() {
        spawn(true);
    }

    public static void onHourChange() {
        despawn();
        spawn(false);
    }

    private static void despawn() {
        for (SpawnGroup2 spawn : temporarySpawns) {
            for (SpawnTemplate template : spawn.getSpawnTemplates()) {
                if (template.getTemporarySpawn().canDespawn()) {
                    VisibleObject object = template.getVisibleObject();
                    if (object == null) {
                        continue;
                    }
                    if (object instanceof Npc) {
                        Npc npc = (Npc) object;
                        if (!npc.getLifeStats().isAlreadyDead() && template.hasPool()) {
                            spawn.setTemplateUse(npc.getInstanceId(), template, false);
                        }
                        npc.getController().cancelTask(TaskId.RESPAWN);
                    }
                    if (object.isSpawned()) {
                        object.getController().onDelete();
                    }
                }
            }
        }
    }

    private static void spawn(boolean startCheck) {
        for (SpawnGroup2 spawn : temporarySpawns) {
            HashSet<Integer> instances = tempSpawnInstanceMap.get(spawn);
            if (spawn.hasPool()) {
                TemporarySpawn temporarySpawn = spawn.geTemporarySpawn();
                if (temporarySpawn.canSpawn() || (startCheck && spawn.getRespawnTime() != 0 && temporarySpawn.isInSpawnTime())) {
                    for (Integer instanceId : instances) {
                        spawn.resetTemplates(instanceId);
                        for (int pool = 0; pool < spawn.getPool(); pool++) {
                            SpawnTemplate template = spawn.getRndTemplate(instanceId);
                            SpawnEngine.spawnObject(template, instanceId);
                        }
                    }
                }
            } else {
                for (SpawnTemplate template : spawn.getSpawnTemplates()) {
                    TemporarySpawn temporarySpawn = template.getTemporarySpawn();
                    if (temporarySpawn.canSpawn() || (startCheck && !template.isNoRespawn() && temporarySpawn.isInSpawnTime())) {
                        for (Integer instanceId : instances) {
                            SpawnEngine.spawnObject(template, instanceId);
                        }
                    }
                }
            }
        }
    }

    /**
     * @param spawnTemplate
     */
    public static void addSpawnGroup(SpawnGroup2 spawn, int instanceId) {
        temporarySpawns.add(spawn);
        HashSet<Integer> instances = tempSpawnInstanceMap.get(spawn);
        if (instances == null) {
            instances = new HashSet<Integer>();
            tempSpawnInstanceMap.put(spawn, instances);
        }
        instances.add(instanceId);
    }
}
