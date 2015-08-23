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
package instance.void_cube;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.InstanceScoreType;
import com.aionemu.gameserver.model.instance.instancereward.VoidCubeReward;
import com.aionemu.gameserver.model.instance.playerreward.InstancePlayerReward;
import com.aionemu.gameserver.model.instance.playerreward.VoidCubePlayerReward;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.*;
import java.util.concurrent.Future;

/**
 * @author Eloann
 */
@InstanceID(301180000)
public class VoidCubeLegionInstance extends GeneralInstanceHandler {

    private long startTime;
    private Future<?> instanceTimer;
    @SuppressWarnings("unused")
    private boolean isInstanceDestroyed;
    private Map<Integer, StaticDoor> doors;
    private VoidCubeReward instanceReward;

    protected VoidCubePlayerReward getPlayerReward(Player player) {
        Integer object = player.getObjectId();
        if (instanceReward.getPlayerReward(object) == null) {
            addPlayerToReward(player);
        }
        return (VoidCubePlayerReward) instanceReward.getPlayerReward(object);
    }

    private void addPlayerToReward(Player player) {
        instanceReward.addPlayerReward(new VoidCubePlayerReward(player.getObjectId()));
    }

    @SuppressWarnings("unused")
    private boolean containPlayer(Integer object) {
        return instanceReward.containPlayer(object);
    }

    @Override
    public void onDie(Npc npc) {
        int points = 0;
        int npcId = npc.getNpcId();
        switch (npcId) {
            case 230084: // Defected Pashid Combatant.
            case 230094: // Undead Pashid Defector.
                points = 25;
                break;
            case 230087: // Spectral Legionary.
                points = 50;
                break;
            case 230095: // Undead Pashid Defector Adjutant.
            case 230097: // Restless Pashid Adjutant.
                points = 75;
                break;
            case 230090: // Sheban Intelligence Elite Magus.
            case 230091: // Sheban Intelligence Elite Archmage.
            case 230098: // Void Golem.
            case 230099: // Void Orbis.
                points = 150;
                break;
            case 230085: // Eldritch Cannon.
                points = 200;
                break;
            case 230088: // Defected Pashid Healer.
            case 230089: // Defected Pashid Outrider.
                points = 225;
                break;
            case 230086: // Pashid Raider Adjutant.
            case 230096: // Restless Pashid Trooper.
                points = 450;
                break;
            case 230355: // Informant Formash.
                points = 900;
                break;
            case 230092: // Enraged Barukan.
            case 230093: // Furious Barukan.
                points = 1000;
                break;
        }
        if (instanceReward.getInstanceScoreType().isStartProgress()) {
            instanceReward.addNpcKill();
            instanceReward.addPoints(points);
            sendPacket(npc.getObjectTemplate().getNameId(), points);
        }
    }

    private int getTime() {
        long result = System.currentTimeMillis() - startTime;
        if (result < 60000) {
            return (int) (60000 - result);
        } else if (result < 1500000) { // 25 Minutes.
            return (int) (1500000 - (result - 60000));
        }
        return 0;
    }

    private void sendPacket(final int nameId, final int point) {
        instance.doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                if (nameId != 0) {
                    PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400237, new DescriptionId(nameId * 2 + 1), point));
                }
                PacketSendUtility.sendPacket(player, new SM_INSTANCE_SCORE(getTime(), instanceReward, null));
            }
        });
    }

    private int checkRank(int totalPoints) {
        int rank = 0;
        if (totalPoints > 10000) { // Rank S.
            rank = 1;
        } else if (totalPoints > 6800) { // Rank A
            rank = 2;
        } else if (totalPoints > 5700) { // Rank B
            rank = 3;
        } else if (totalPoints > 3900) { // Rank C
            rank = 4;
        } else if (totalPoints > 1800) { // Rank D
            rank = 5;
        } else if (totalPoints > 900) { // Rank F
            rank = 6;
        } else {
            rank = 8;
        }
        return rank;
    }

    @Override
    public void onEnterInstance(final Player player) {
        if (instanceTimer == null) {
            startTime = System.currentTimeMillis();
            instanceTimer = ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    openFirstDoors();
                    sendMsg(1401766); // Void Cube Start.
                    instanceReward.setInstanceScoreType(InstanceScoreType.START_PROGRESS);
                    sendPacket(0, 0);
                }
            }, 60000);
        }
        stopInstanceAfterTime(player);
        sendPacket(0, 0);
    }

    public void stopInstanceAfterTime(final Player player) {
        instanceTimer = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                stopInstance(player);
                spawn(730729, 254.687775f, 276.070099f, 57.071781f, (byte) 0); // Void Cube Exit
            }
        }, 1560000); // 25 Minutes.
    }

    protected void stopInstance(Player player) {
        stopInstanceTask();
        instanceReward.setRank(checkRank(instanceReward.getPoints()));
        instanceReward.setInstanceScoreType(InstanceScoreType.END_PROGRESS);
        doReward(player);
        sendPacket(0, 0);
    }

    private void stopInstanceTask() {
        if (instanceTimer != null) {
            instanceTimer.cancel(true);
        }
    }

    @Override
    public void doReward(Player player) {
        for (Player p : instance.getPlayersInside()) {
            InstancePlayerReward playerReward = getPlayerReward(p);
            float rewardSillus = (0.1f * instanceReward.getPoints()) / 800;
            float rewardCeramium = (0.1f * instanceReward.getPoints()) / 1200;
            float rewardAp = (playerReward.getPoints() / 15) * 2;
            if (!instanceReward.isRewarded()) {
                instanceReward.setRewarded();
                instanceReward.setScoreAP((int) rewardAp);
                AbyssPointsService.addAp(player, (int) rewardAp); // Abyss Points.
                if (instanceReward.getPoints() >= 5700) {
                    instanceReward.setSillus((int) rewardSillus);
                    ItemService.addItem(player, 186000239, (int) rewardSillus); // Sillus Crest.
                    instanceReward.setCeramium((int) rewardCeramium);
                    ItemService.addItem(player, 186000242, (int) rewardCeramium); // Ceramium Medal.
                }
                if (instanceReward.getPoints() >= 10000) {
                    instanceReward.setFavorable(1);
                    ItemService.addItem(player, 188052543, 1); // Favorable Reward Bundle.
                }
            }
        }
    }

    protected void openFirstDoors() {
        openDoor(26);
    }

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        instanceReward = new VoidCubeReward(mapId, instanceId);
        instanceReward.setInstanceScoreType(InstanceScoreType.PREPARING);
        doors = instance.getDoors();
    }

    @Override
    public void onInstanceDestroy() {
        isInstanceDestroyed = true;
        instanceReward.clear();
    }

    protected void openDoor(int doorId) {
        StaticDoor door = doors.get(doorId);
        if (door != null) {
            door.setOpen(true);
        }
    }

    @Override
    public void onPlayerLogOut(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }

    @Override
    public void onExitInstance(Player player) {
        if (instanceReward.getInstanceScoreType().isEndProgress()) {
            TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
        }
    }

    @Override
    public boolean onReviveEvent(Player player) {
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
        PlayerReviveService.revive(player, 100, 100, false, 0);
        player.getGameStats().updateStatsAndSpeedVisually();
        return TeleportService2.teleportTo(player, mapId, instanceId, 181f, 261f, 310f, (byte) 0);
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }
}
