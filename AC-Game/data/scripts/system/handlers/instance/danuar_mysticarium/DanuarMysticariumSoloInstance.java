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
package instance.danuar_mysticarium;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.InstanceScoreType;
import com.aionemu.gameserver.model.instance.instancereward.DanuarMysticariumReward;
import com.aionemu.gameserver.model.instance.playerreward.DanuarPlayerReward;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INSTANCE_SCORE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author Eloann
 */
@InstanceID(300480000)
public class DanuarMysticariumSoloInstance extends GeneralInstanceHandler {

    private long startTime;
    private Future<?> instanceTimer;
    private Map<Integer, StaticDoor> doors;
    private DanuarMysticariumReward instanceReward;
    private boolean isInstanceDestroyed;
    private int rank;

    protected DanuarPlayerReward getPlayerReward(Player player) {
        Integer object = player.getObjectId();
        if (instanceReward.getPlayerReward(object) == null) {
            addPlayerToReward(player);
        }
        return (DanuarPlayerReward) instanceReward.getPlayerReward(object);
    }

    private void addPlayerToReward(Player player) {
        instanceReward.addPlayerReward(new DanuarPlayerReward(player.getObjectId()));
    }

    private boolean containPlayer(Integer object) {
        return instanceReward.containPlayer(object);
    }

    @Override
    public void onDie(Npc npc) {
        Player player = npc.getAggroList().getMostPlayerDamage();
        int rewardetPoints = 0;
        switch (npc.getNpcId()) {
            case 230062: // Einsaldir The Cursed
            case 230063: // Living Idgel
            case 230064: // Dread Witherthorn
                rewardetPoints = 150;
                break;
            case 230066: // Sheban Intelligence Warden
            case 230067: // Sheban Intelligence Researcher
            case 230078: // Sheban Intelligence Warden
            case 230079: // Sheban Intelligence Researcher
                rewardetPoints = 285;
                break;
            case 230074: // Idgel Trigger
                rewardetPoints = 1125;
                despawnNpc(npc);
                break;
            case 230051: // Kippy The Destroyer
            case 230052: // Manumumu Reborn
            case 230053: // Krukel The Infernal
            case 230054: // Idewarped Ginseng
            case 230055: // Panoptes The Watchful
            case 230056: // Undying Ntuamu
            case 230057: // Brashuna The Disgraced
            case 230058: // Brhekman The Failed
                rewardetPoints = 2010;
                break;
            case 230080: // Sheban intelligence Inspector
            case 230081: // Sheban Intelligence Captain
                rewardetPoints = 3051;
                break;
            case 272762: // Sturdy Balaur Pickets
                despawnNpc(npc);
                break;
        }
        if (!isInstanceDestroyed && instanceReward.getInstanceScoreType().isStartProgress()) {
            instanceReward.addNpcKill();
            instanceReward.addPoints(rewardetPoints);
            sendSystemMsg(player, npc, rewardetPoints);
            sendPacket(rewardetPoints);
        }
        switch (npc.getNpcId()) {
            case 230080: // Sheban intelligence Inspector
            case 230081: // Sheban Intelligence Captain
                stopInstance(player);
                break;
            case 233253: // Berserk Chairman Nautius
            case 233254: // Frenzied Chairman Nautius
                spawn(701572, 558.2879f, 414.86456f, 96.81003f, (byte) 40, 67); // Exit
                break;
        }
    }

    private int getNpcBonus(int npcId) {
        switch (npcId) {
            case 831145:
            case 831146:
                return 500;
        }
        return 0;
    }

    @Override
    public void handleUseItemFinish(Player player, Npc npc) {
        if (!instanceReward.isStartProgress()) {
            return;
        }
        int rewardetPoints = getNpcBonus(npc.getNpcId());
        instanceReward.addPoints(rewardetPoints);
        sendSystemMsg(player, npc, rewardetPoints);
        sendPacket(rewardetPoints);
    }

    protected void sendSystemMsg(Player player, Creature creature, int rewardPoints) {
        int nameId = creature.getObjectTemplate().getNameId();
        DescriptionId name = new DescriptionId(nameId * 2 + 1);
        PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400237, nameId == 0 ? creature.getName() : name, rewardPoints));
    }

    private int getTime() {
        long result = System.currentTimeMillis() - startTime;
        if (result < 30000) {
            return (int) (30000 - result);
        } else if (result < 1800000) {
            return (int) (1800000 - (result - 30000));
        }
        return 0;
    }

    private void sendPacket(final int point) {
        instance.doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                PacketSendUtility.sendPacket(player, new SM_INSTANCE_SCORE(getTime(), instanceReward, null));
            }
        });
    }

    private int checkRank(int totalPoints) {
        int timeRemain = getTime();

        if (timeRemain >= 1080000 && totalPoints >= 29911) {
            rank = 1; // S
        } else if (timeRemain >= 960000 && totalPoints >= 21682) {
            rank = 2; // A
        } else if (timeRemain >= 840000 && totalPoints >= 14824) {
            rank = 3; // B
        } else if (timeRemain >= 360000 && totalPoints >= 9338) {
            rank = 4; // C
        } else if (timeRemain >= 120000 && totalPoints >= 6595) {
            rank = 5; // D
        } else if (timeRemain > 1) {
            rank = 6; // F
        } else {
            rank = 8;
        }

        return rank;
    }

    @Override
    public void onEnterInstance(final Player player) {
        Integer object = player.getObjectId();
        if (!containPlayer(object)) {
            addPlayerToReward(player);
        }
        if (instanceTimer == null) {
            startTime = System.currentTimeMillis();
            instanceTimer = ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    openDoor(3);
                    sendMsg(1401658);
                    instanceReward.setInstanceScoreType(InstanceScoreType.START_PROGRESS);
                    sendPacket(0);
                }
            }, 30000);
        }
        sendPacket(0);
    }

    protected void stopInstance(Player player) {
        stopInstanceTask();
        instanceReward.setRank(checkRank(instanceReward.getPoints()));
        instanceReward.setInstanceScoreType(InstanceScoreType.END_PROGRESS);
        doReward(player);
        sendPacket(0);
    }

    private void stopInstanceTask() {
        if (instanceTimer != null) {
            instanceTimer.cancel(true);
        }
    }

    @Override
    public void onInstanceDestroy() {
        if (instanceTimer != null) {
            instanceTimer.cancel(false);
        }
        isInstanceDestroyed = true;
        doors.clear();
    }

    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
        instanceReward = new DanuarMysticariumReward(mapId, instanceId);
        instanceReward.setInstanceScoreType(InstanceScoreType.PREPARING);
        doors = instance.getDoors();

        /**
         * Final Boss : The final boss can appear as either a Mage or Fighter
         * balaur, and gives 3010 points when killed. When the Final Boss is
         * dead, the instance ends and your Grade, stats, and rewards will be
         * presented.
         */
        int npcId = 0;
        switch (Rnd.get(1, 2)) {
            case 1:
                npcId = 230080; // Sheban intelligence Inspector
                break;
            case 2:
                npcId = 230081; // Sheban intelligence Captain
                break;
        }
        spawn(npcId, 520.6471f, 468.8483f, 95.58755f, (byte) 40);
    }

    public void doReward(Player player) {
        if (!instanceReward.isRewarded()) {
            instanceReward.setRewarded();
            switch (rank) {
                case 1: // S
                    instanceReward.setBasicAP(1402);
                    instanceReward.setSillusCrest(12);
                    instanceReward.setCeramiumMedal(1);
                    instanceReward.setFavorableBundle(1);
                    spawnRankS_NautiusBoss();
                    break;
                case 2: // A
                    instanceReward.setBasicAP(1020);
                    instanceReward.setSillusCrest(8);
                    instanceReward.setCeramiumFragments(3);
                    instanceReward.setValorBundle(1);
                    break;
                case 3: // B
                    instanceReward.setBasicAP(892);
                    instanceReward.setSillusCrest(7);
                    instanceReward.setMithrilMedal(1);
                    break;
                case 4: // C
                    instanceReward.setBasicAP(765);
                    instanceReward.setSillusCrest(6);
                    break;
                case 5: // D
                    instanceReward.setBasicAP(382);
                    instanceReward.setSillusCrest(3);
                    break;
                case 6: // F
                    break;
            }
            AbyssPointsService.addAp(player, instanceReward.getBasicAP());
            ItemService.addItem(player, 186000239, instanceReward.getSillusCrest());
            ItemService.addItem(player, 186000242, instanceReward.getCeramiumMedal());
            ItemService.addItem(player, 152012578, instanceReward.getCeramiumFragments());
            ItemService.addItem(player, 186000147, instanceReward.getMithrilMedal());
            ItemService.addItem(player, 188052543, instanceReward.getFavorableBundle());
            ItemService.addItem(player, 188052547, instanceReward.getValorBundle());
        }
    }

    private void despawnNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }

    protected void openDoor(int doorId) {
        StaticDoor door = doors.get(doorId);
        if (door != null) {
            door.setOpen(true);
        }
    }

    private void spawnRankS_NautiusBoss() {
        int npcId = 0;
        switch (Rnd.get(1, 2)) {
            case 1:
                npcId = 233253; // Berserk Chairman Nautius
                break;
            case 2:
                npcId = 233254; // Frenzied Chairman Nautius
                break;
        }
        spawn(npcId, 546.451f, 431.18f, 94.78f, (byte) 46);
    }

    @Override
    public void onDropRegistered(Npc npc) {
        Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
        int npcId = npc.getNpcId();
        switch (npcId) {
            case 230066:
                dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npc.getNpcId(), 185000127, 1));
                break;
            case 230080:
            case 230081:
                dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npc.getNpcId(), 185000165, 1));
        }
    }

    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);

        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }

    @Override
    public void onPlayerLogOut(Player player) {
        cleanItems(player);
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }

    public void cleanItems(Player player) {
        Storage storage = player.getInventory(); // Idgel Storage Key
        storage.decreaseByItemId(185000127, storage.getItemCountByItemId(185000127));
        storage.decreaseByItemId(185000165, storage.getItemCountByItemId(185000165));
    }

    @Override
    public void onLeaveInstance(Player player) {
        cleanItems(player);
    }
}
