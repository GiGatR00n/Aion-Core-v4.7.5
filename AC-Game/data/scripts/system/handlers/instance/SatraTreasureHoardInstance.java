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
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.flyring.FlyRing;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.CreatureType;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.flyring.FlyRingTemplate;
import com.aionemu.gameserver.model.utils3d.Point3D;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUSTOM_SETTINGS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ritsu Guide:
 * @rework 4.3 Eloann
 */
@InstanceID(300470000)
public class SatraTreasureHoardInstance extends GeneralInstanceHandler {

    private Map<Integer, StaticDoor> doors;
    private boolean isStartTimer = false;
    private List<Npc> firstChest = new ArrayList<Npc>();
    private List<Npc> finalChest = new ArrayList<Npc>();

    @Override
    public void onInstanceDestroy() {
        doors.clear();
    }

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        doors = instance.getDoors();
        doors.get(77).setOpen(true);
        spawnTimerRing();
    }

    private void spawnTimerRing() {
        FlyRing f1 = new FlyRing(new FlyRingTemplate("SATRAS_01", mapId,
                new Point3D(501.13412, 672.4659, 177.10771),
                new Point3D(492.13412, 672.4659, 177.10771),
                new Point3D(496.54834, 671.5966, 184.10771), 8), instanceId);
        f1.spawn();
    }

    @Override
    public boolean onPassFlyingRing(Player player, String flyingRing) {
        if (flyingRing.equals("SATRAS_01")) {
            if (!isStartTimer) {
                isStartTimer = true;
                System.currentTimeMillis();
                PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 600));
                doors.get(77).setOpen(false);
                //Spawn Chest When the timer begin
                firstChest.add((Npc) spawn(701461, 466.246f, 716.57f, 176.398f, (byte) 0));
                firstChest.add((Npc) spawn(701461, 528.156f, 715.66f, 176.398f, (byte) 60));
                firstChest.add((Npc) spawn(701461, 469.17f, 701.632f, 176.398f, (byte) 11));
                firstChest.add((Npc) spawn(701461, 524.292f, 701.063f, 176.398f, (byte) 50));
                firstChest.add((Npc) spawn(701461, 515.439f, 691.87f, 176.398f, (byte) 45));
                firstChest.add((Npc) spawn(701461, 478.623f, 692.772f, 176.398f, (byte) 15));
                changeChestType(CreatureType.PEACE.getId());
                switchWay();
                despawnChest();
            }
        }
        return false;
    }

    @Override
    public void onDie(Npc npc) {
        switch (npc.getNpcId()) {
            case 219296: //Gatekeeper Gourlaz
                int door = Rnd.get(1, 2);
                if (door == 1) {
                    doors.get(84).setOpen(true);
                    sendMsg(1401230);
                } else {
                    doors.get(88).setOpen(true);
                    sendMsg(1401229);
                }
                break;
            case 219299: // muzzled punisher
            case 219300: // punisher unleashed
                spawn(730588, 496.600f, 685.600f, 176.400f, (byte) 30); // Spawn Exit
                instance.doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(Player p) {
                        if (p.isOnline()) {
                            PacketSendUtility.sendPacket(p, new SM_QUEST_ACTION(0, 0));
                        }
                    }
                });
                changeChestType(CreatureType.FRIEND.getId());
                break;
            case 701464: // artifact spawn stronger boss
                Npc boss = getNpc(219299);
                if (boss != null && !boss.getLifeStats().isAlreadyDead()) {
                    spawn(219300, boss.getX(), boss.getY(), boss.getZ(), boss.getHeading());
                    boss.getController().onDelete();
                }
                break;
            case 219298: //Borgalmar
                doors.get(62).setOpen(true);
                doors.get(108).setOpen(true);
                doors.get(118).setOpen(true);
                sendMsg(1401231);
                break;
            case 219297: //Vashinstirg
                doors.get(82).setOpen(true);
                doors.get(86).setOpen(true);
                doors.get(117).setOpen(true);
                sendMsg(1401231);
                break;
        }
    }

    private void switchWay() {
        Npc muzzledPunisher = getNpc(219299);
        int chest = muzzledPunisher == null ? 701463 : 701462;
        finalChest.add((Npc) spawn(chest, 446.962f, 744.254f, 178.071f, (byte) 0, 206));
        finalChest.add((Npc) spawn(chest, 459.856f, 759.960f, 178.071f, (byte) 0, 81));
        finalChest.add((Npc) spawn(chest, 533.697f, 760.551f, 178.071f, (byte) 0, 80));
        finalChest.add((Npc) spawn(chest, 477.382f, 770.049f, 178.071f, (byte) 0, 83));
        finalChest.add((Npc) spawn(chest, 497.030f, 773.931f, 178.071f, (byte) 0, 85));
        finalChest.add((Npc) spawn(chest, 516.508f, 770.646f, 178.071f, (byte) 0, 122));
    }

    @Override
    public void onDropRegistered(Npc npc) {
        Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
        int npcId = npc.getNpcId();
        switch (npcId) {
            case 219299:  //muzzledPunisher
                int index = dropItems.size() + 1;
                for (Player player : instance.getPlayersInside()) {
                    if (player.isOnline()) {
                        dropItems.add(DropRegistrationService.getInstance().regDropItem(index++, player.getObjectId(), npcId, 185000118, 1));
                    }
                }
                break;
        }
    }

    private void despawnChest() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                firstChest.get(0).getController().onDelete();
                finalChest.get(0).getController().onDelete();
            }
        }, 300000);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                firstChest.get(1).getController().onDelete();
                finalChest.get(1).getController().onDelete();
            }
        }, 360000);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                firstChest.get(2).getController().onDelete();
                finalChest.get(2).getController().onDelete();
            }
        }, 420000);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                firstChest.get(3).getController().onDelete();
                finalChest.get(3).getController().onDelete();
            }
        }, 480000);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                firstChest.get(4).getController().onDelete();
                finalChest.get(4).getController().onDelete();
            }
        }, 540000);
    }

    private void changeChestType(final int newType) {
        for (Npc c : instance.getNpcs(701461)) {
            c.setNpcType(newType);
        }
        for (final Player player : instance.getPlayersInside()) {
            player.getKnownList().doOnAllNpcs(new Visitor<Npc>() {
                @Override
                public void visit(Npc npc) {
                    if (npc.getNpcId() == 701461) {
                        PacketSendUtility.sendPacket(player, new SM_CUSTOM_SETTINGS(npc.getObjectId(), 0, newType, 0));
                    }
                }
            });
        }
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);

        PacketSendUtility.sendPacket(player, new SM_DIE(false, false, 0, 8));
        return true;
    }

    @Override
    public void onPlayerLogOut(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }
}
