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
package instance;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.controllers.effect.PlayerEffectController;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import com.aionemu.gameserver.world.zone.ZoneName;

import java.util.List;
import java.util.Map;

/**
 * @author xTz
 */
@InstanceID(300280000)
public class RentusBaseInstance extends GeneralInstanceHandler {

    private Map<Integer, StaticDoor> doors;
    private boolean isInstanceDestroyed;
    private int umahtasEchoKilled;
    private int karianasEchoKilled;
    private int upadisEchoKilled;
    private int ragnarokKilled;

    @Override
    public void onDie(final Npc npc) {
        if (isInstanceDestroyed) {
            return;
        }

        switch (npc.getNpcId()) {
            case 282543:
                umahtasEchoKilled++;
                if (umahtasEchoKilled == 2) {
                    spawn(217315, 759.46f, 636.45f, 157f, (byte) 7);
                    sendMsg(1500424, instance.getNpc(799671).getObjectId(), false, 0);
                    despawnNpcs(instance.getNpcs(282546));
                    ThreadPoolManager.getInstance().schedule(new Runnable() {
                        @Override
                        public void run() {
                            if (!isInstanceDestroyed) {
                                spawn(282465, 759.46f, 636.45f, 157f, (byte) 0);
                            }
                        }
                    }, 3000);
                }
                break;
            case 282544:
                karianasEchoKilled++;
                if (karianasEchoKilled == 2) {
                    spawn(217316, 258.09f, 671.51f, 170f, (byte) 17);
                    spawn(282465, 258.09f, 671.51f, 170f, (byte) 0);
                    sendMsg(1500430, instance.getNpc(799672).getObjectId(), false, 0);
                    despawnNpcs(instance.getNpcs(282547));
                    Npc merops = instance.getNpc(799672);
                    merops.getSpawn().setWalkerId("3002800002");
                    WalkManager.startWalking((NpcAI2) merops.getAi2());
                    despawnMerops(instance.getNpc(799672));
                }
                break;
            case 282545:
                upadisEchoKilled++;
                if (upadisEchoKilled == 2) {
                    despawnNpcs(instance.getNpcs(282548));
                }
                break;
            case 217315:
                spawn(282626, 728.910f, 632.190f, 157f, (byte) 0);
                doors.get(82).setOpen(true);
                Npc merops = instance.getNpc(799671);
                despawnMerops(merops);
                sendMsg(1500420, merops.getObjectId(), false, 0);
                final Npc ariana1 = instance.getNpc(799668);
                if (ariana1 != null) {
                    SkillEngine.getInstance().getSkill(ariana1, 19921, 60, ariana1).useNoAnimationSkill();
                }
                break;
            case 217307:
                doors.get(236).setOpen(true);
                instance.doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(Player player) {
                        removeEffects(player);
                    }
                });
                startZantarazEvent();
                break;
            case 217313:
                spawn(730401, 193.6f, 436.5f, 262f, (byte) 86);
                Npc ariana = (Npc) spawn(799670, 183.736f, 391.392f, 260.571f, (byte) 26);
                NpcShoutsService.getInstance().sendMsg(ariana, 1500417, ariana.getObjectId(), 0, 5000);
                NpcShoutsService.getInstance().sendMsg(ariana, 1500418, ariana.getObjectId(), 0, 8000);
                NpcShoutsService.getInstance().sendMsg(ariana, 1500419, ariana.getObjectId(), 0, 11000);
                spawnEndEvent(800227, "3002800003", 2000);
                spawnEndEvent(800227, "3002800004", 2000);
                spawnEndEvent(800228, "3002800007", 4000);
                spawnEndEvent(800227, "3002800005", 6000);
                spawnEndEvent(800228, "3002800006", 8000);
                spawnEndEvent(800229, "3002800008", 10000);
                spawnEndEvent(800229, "3002800009", 10000);
                spawnEndEvent(800230, "30028000010", 12000);
                spawnEndEvent(800230, "30028000011", 12000);
                break;
            case 282394:
                spawn(282395, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading());
                despawnNpc(npc);
                break;
            case 283000:
            case 283001:
                despawnNpc(npc);
                break;
            case 217301:
                ragnarokKilled++;
                if (ragnarokKilled == 4) {
                    ragnarokKilled = 0;
                    if (instance.getNpcs(282382).size() < 50) { // security
                        spawn(282382, npc.getX(), npc.getY(), npc.getZ(), npc.getHeading());
                    }
                }
                despawnNpc(npc);
                break;
            case 217299:
                final float x = npc.getX();
                final float y = npc.getY();
                final float z = npc.getZ();
                final byte h = npc.getHeading();
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        if (!isInstanceDestroyed) {
                            if (x > 0 && y > 0 && z > 0) {
                                spawn(217300, x, y, z, h);
                            }
                        }
                    }
                }, 4000);
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        despawnNpc(npc);
                    }
                }, 2000);
                break;
        }
    }

    private void startWalk(Npc npc, String walkId) {
        npc.getSpawn().setWalkerId(walkId);
        WalkManager.startWalking((NpcAI2) npc.getAi2());
        npc.setState(1);
        PacketSendUtility.broadcastPacket(npc, new SM_EMOTION(npc, EmotionType.START_EMOTE2, 0, npc.getObjectId()));
    }

    private void startZantarazEvent() {
        final Npc ariana = (Npc) spawn(799667, 674.73f, 625.42f, 156f, (byte) 0);
        final Npc priest1 = (Npc) spawn(800198, 679.6f, 634.59f, 156f, (byte) 0);
        final Npc priest2 = (Npc) spawn(800198, 683.3f, 623.59f, 156f, (byte) 0);
        final Npc warrior1 = (Npc) spawn(800196, 684f, 632.61f, 156f, (byte) 0);
        final Npc warrior2 = (Npc) spawn(800196, 687.56f, 622.07f, 156f, (byte) 0);
        final Npc ranger1 = (Npc) spawn(800197, 690.47f, 631.58f, 156f, (byte) 0);
        final Npc ranger2 = (Npc) spawn(800197, 683.09f, 618.9f, 156f, (byte) 0);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                startWalk(ariana, "30028000017");
                startWalk(priest1, "30028000018");
                startWalk(priest2, "30028000019");
                startWalk(warrior1, "30028000020");
                startWalk(warrior2, "30028000021");
                startWalk(ranger1, "30028000022");
                startWalk(ranger2, "30028000023");
            }
        }, 1000);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                final Npc invisible = (Npc) spawn(282601, 621.4345f, 617.3071f, 154.125f, (byte) 0);
                invisible.getKnownList().doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(Player player) {
                        PacketSendUtility.sendPacket(player, new SM_PLAY_MOVIE(0, 0, 482, 65536, invisible.getObjectId()));
                    }
                });
                stopWalk(priest1);
                stopWalk(priest2);
                stopWalk(warrior1);
                stopWalk(warrior2);
                stopWalk(ranger1);
                stopWalk(ranger2);
                NpcActions.delete(invisible);
                NpcActions.delete(ariana);
            }
        }, 12000);
    }

    private void stopWalk(Npc npc) {
        npc.getSpawn().setWalkerId(null);
        WalkManager.stopWalking((NpcAI2) npc.getAi2());
    }

    private void spawnEndEvent(int npcId, String walkern, int time) {
        sp(npcId, 193.39548f, 435.56158f, 260.57135f, (byte) 86, time, walkern);
    }

    private void despawnMerops(final Npc merops) {
        if (merops != null) {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (!isInstanceDestroyed) {
                        if (merops != null) {
                            SkillEngine.getInstance().getSkill(merops, 19358, 60, merops).useNoAnimationSkill();
                            if (merops.getNpcId() == 799671) {
                                despawnNpc(instance.getNpc(701155));
                                sendMsg(1500425, merops.getObjectId(), false, 0);
                                spawn(282465, 744.2147f, 634.5343f, 155.69595f, (byte) 0);
                                despawnNpc(instance.getNpc(282626));
                            }
                            despawnNpc(merops);
                        }
                    }
                }
            }, merops.getNpcId() == 799671 ? 3000 : 5000);

        }
    }

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        doors = instance.getDoors();
        if (Rnd.get(1, 100) > 70) {
            switch (Rnd.get(1, 4)) {
                case 1:
                    spawn(218572, 538.6639f, 477.2887f, 145.82251f, (byte) 90);
                    break;
                case 2:
                    spawn(218572, 377.38275f, 461.9165f, 138.54454f, (byte) 60);
                    break;
                case 3:
                    spawn(218572, 317.74368f, 623.0686f, 150.33286f, (byte) 45);
                    break;
                case 4:
                    spawn(218572, 316.23618f, 726.1624f, 163.5f, (byte) 40);
                    break;
            }
        }
    }

    @Override
    public void onLeaveInstance(Player player) {
        removeEffects(player);
    }

    @Override
    public void onPlayerLogOut(Player player) {
        removeEffects(player);
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }

    private void removeEffects(Player player) {
        PlayerEffectController effectController = player.getEffectController();
        effectController.removeEffect(19376);
        effectController.removeEffect(19350);
        effectController.removeEffect(20027);
        effectController.removeEffect(20037);
        effectController.removeEffect(20031);
    }

    private boolean canUseTank() {
        Npc zantaraz = instance.getNpc(217307);
        if (zantaraz != null && !NpcActions.isAlreadyDead(zantaraz)) {
            return true;
        }
        return false;
    }

    @Override
    public void handleUseItemFinish(Player player, Npc npc) {
        switch (npc.getNpcId()) {
            case 701151:
                SkillEngine.getInstance().getSkill(npc, 19909, 60, npc).useNoAnimationSkill();
                despawnNpc(npc);
                break;
            case 701152:
                SkillEngine.getInstance().getSkill(npc, 19910, 60, npc).useNoAnimationSkill();
                despawnNpc(npc);
                break;
            case 218611:
                if (canUseTank()) {
                    SkillEngine.getInstance().getSkill(npc, 20027, 60, player).useNoAnimationSkill();
                    NpcActions.scheduleRespawn(npc);
                }
                despawnNpc(npc);
                break;
            case 218610:
                if (canUseTank()) {
                    SkillEngine.getInstance().getSkill(npc, 19350, 60, player).useNoAnimationSkill();
                    NpcActions.scheduleRespawn(npc);
                }
                despawnNpc(npc);
                break;
            case 701097:
                despawnNpc(npc);
                break;
            case 701100:
                if (instance.getNpc(799543) == null) {
                    spawn(799543, 506.303f, 613.902f, 158.179f, (byte) 0);
                }
                break;
        }
    }

    private void despawnNpcs(List<Npc> npcs) {
        for (Npc npc : npcs) {
            npc.getController().onDelete();
        }
    }

    private void sp(final int npcId, final float x, final float y, final float z, final byte h, final int time,
                    final String walkern) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isInstanceDestroyed) {
                    Npc npc = (Npc) spawn(npcId, x, y, z, h);
                    npc.getSpawn().setWalkerId(walkern);
                    startEndWalker(npc);
                    unSetEndWalker(npc);
                }
            }
        }, time);
    }

    private void startEndWalker(final Npc npc) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isInstanceDestroyed) {
                    WalkManager.startWalking((NpcAI2) npc.getAi2());
                    npc.setState(1);
                    PacketSendUtility.broadcastPacket(npc, new SM_EMOTION(npc, EmotionType.START_EMOTE2, 0, npc.getObjectId()));
                }
            }
        }, 3000);
    }

    private void unSetEndWalker(final Npc npc) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isInstanceDestroyed) {
                    stopWalk(npc);
                }
            }
        }, 8000);
    }

    private void despawnNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }

    @Override
    public void onEnterZone(Player player, ZoneInstance zone) {
        if (zone.getAreaTemplate().getZoneName() == ZoneName.get("RESIDENTIAL_ZONE_300280000")) {
            removeEffects(player);
        }
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);

        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }
}
