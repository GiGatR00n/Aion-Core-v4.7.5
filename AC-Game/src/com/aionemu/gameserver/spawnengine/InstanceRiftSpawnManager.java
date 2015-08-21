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

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ginho1
 */
public class InstanceRiftSpawnManager {

    private static final Logger log = LoggerFactory.getLogger(InstanceRiftSpawnManager.class);
    private static final ConcurrentLinkedQueue<VisibleObject> rifts = new ConcurrentLinkedQueue<VisibleObject>();
    private static final int RIFT_RESPAWN_DELAY = 3600;    // 1 hour
    private static final int RIFT_LIFETIME = 3500;    // 1 hour

    public static void spawnAll() {

        ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                for (RiftEnum rift : RiftEnum.values()) {
                    if (Rnd.get(1, 100) > 30) {
                        continue;
                    }

                    spawnInstanceRift(rift);
                }
            }
        }, 0, RIFT_RESPAWN_DELAY * 1000);
    }

    private static void spawnInstanceRift(RiftEnum rift) {
        log.info("Spawning Instance Rift: " + rift.name());

        SpawnTemplate spawn = SpawnEngine.addNewSpawn(rift.getWorldId(), rift.getNpcId(),
                rift.getX(), rift.getY(), rift.getZ(), (byte) 0, 0);

        if (rift.getStaticId() > 0) {
            spawn.setStaticId(rift.getStaticId());
        }

        VisibleObject visibleObject = SpawnEngine.spawnObject(spawn, 1);

        rifts.add(visibleObject);

        scheduleDelete(visibleObject);
        sendAnnounce(visibleObject);
    }

    private static void scheduleDelete(final VisibleObject visObj) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (visObj != null && visObj.isSpawned()) {
                    visObj.getController().delete();
                    rifts.remove(visObj);
                }
            }
        }, RIFT_LIFETIME * 1000);
    }

    public enum RiftEnum {

        //DraupnirCave(700564, 1617, Race.ELYOS, 210040000, 2528.662f, 2680.882f, 155.050f),
        IndratuFortress(700565, 0, Race.ASMODIANS, 220040000, 1466.8792f, 1947.9192f, 588.06555f);
        private int npc_id;
        private int static_id;
        private Race race;
        private int worldId;
        private float x;
        private float y;
        private float z;

        private RiftEnum(int npc_id, int static_id, Race race, int worldId, float x, float y, float z) {
            this.npc_id = npc_id;
            this.static_id = static_id;
            this.race = race;
            this.worldId = worldId;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getNpcId() {
            return npc_id;
        }

        public int getStaticId() {
            return static_id;
        }

        public Race getRace() {
            return race;
        }

        public int getWorldId() {
            return worldId;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }
    }

    public static void sendInstanceRiftStatus(Player activePlayer) {
        for (VisibleObject visObj : rifts) {
            if (visObj.getWorldId() == activePlayer.getWorldId()) {
                sendMessage(activePlayer, visObj.getObjectTemplate().getTemplateId());
            }
        }
    }

    public static void sendAnnounce(final VisibleObject visObj) {
        if (visObj.isSpawned()) {
            WorldMapInstance worldInstance = visObj.getPosition().getMapRegion().getParent();

            worldInstance.doOnAllPlayers(new Visitor<Player>() {
                @Override
                public void visit(Player player) {
                    if (player.isSpawned()) {
                        sendMessage(player, visObj.getObjectTemplate().getTemplateId());
                    }
                }
            });
        }
    }

    public static void sendMessage(Player player, int npc_id) {
        switch (npc_id) {
            case 700564:
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400276));
                break;
            case 700565:
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400275));
                break;
        }
    }
}
