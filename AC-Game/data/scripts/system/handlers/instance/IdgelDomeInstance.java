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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import javolution.util.FastList;

import org.apache.commons.lang.mutable.MutableInt;

import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.configs.main.RateConfig;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.actions.PlayerActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.InstanceScoreType;
import com.aionemu.gameserver.model.instance.instancereward.IdgelDomeReward;
import com.aionemu.gameserver.model.instance.instancereward.InstanceReward;
import com.aionemu.gameserver.model.instance.packetfactory.IdgelDomePacketsHandler;
import com.aionemu.gameserver.model.instance.playerreward.IdgelDomePlayerReward;
import com.aionemu.gameserver.model.instance.playerreward.InstancePlayerReward;
import com.aionemu.gameserver.model.team2.group.PlayerGroupService;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.AutoGroupService;
import com.aionemu.gameserver.services.abyss.AbyssPointsService;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapInstance;


/**
 * @author GiGatR00n v4.7.5.x
 */
@InstanceID(301310000)
public class IdgelDomeInstance extends GeneralInstanceHandler
{
	private final FastList<Future<?>> idgelTask = FastList.newInstance();
	protected AtomicBoolean isInstanceStarted = new AtomicBoolean(false);
    protected IdgelDomeReward idgelDomeReward;
    private float loosingGroupMultiplier = 1;
    private boolean isInstanceDestroyed = false;
    private Race RaceKilledBoss = null;//Used to getting Additional Reward Box
    
    /**
     * Holds Idgel Dome Instance
     */
    private WorldMapInstance IdgelDome;
    
    /**
     * Used to send Idgel Dome Instance Packets e.g. Score, Reward, Revive, PlayersInfo, ...
     */
    private IdgelDomePacketsHandler IdgelDomePackets;
    
    
    @Override
    public void onInstanceCreate(WorldMapInstance instance) 
    {
        super.onInstanceCreate(instance);
        IdgelDome = instance;
        idgelDomeReward = new IdgelDomeReward(mapId, instanceId, IdgelDome);
        idgelDomeReward.setInstanceScoreType(InstanceScoreType.PREPARING);
        
        IdgelDomePackets = new IdgelDomePacketsHandler(mapId, instanceId, instance, idgelDomeReward);
        IdgelDomePackets.startInstanceTask();
        
        /* Used to handling instance timeout event */
        idgelTask.add(ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                stopInstance(idgelDomeReward.getWinningRaceByScore());
            }
        }, idgelDomeReward.getEndTime())); //20 Min.
    }
	
    @Override
    public void onEnterInstance(final Player player) {
        if (!containPlayer(player.getObjectId())) {
            idgelDomeReward.regPlayerReward(player);
        }
        IdgelDomePackets.sendPreparingPacket(player);
    }
	
    protected IdgelDomePlayerReward getPlayerReward(Player player) {
        idgelDomeReward.regPlayerReward(player);
        return (IdgelDomePlayerReward) getPlayerReward(player.getObjectId());
    }
	
    private boolean containPlayer(Integer object) {
        return idgelDomeReward.containPlayer(object);
    }    
    
    @SuppressWarnings("unused")
	protected void reward() 
    {
    	/*
    	 * Elyos & Asmodian PvP and Points
    	 */
        int ElyosPvPKills = getPvpKillsByRace(Race.ELYOS).intValue();
        int ElyosPoints = getPointsByRace(Race.ELYOS).intValue();
        int AsmoPvPKills = getPvpKillsByRace(Race.ASMODIANS).intValue();
        int AsmoPoints = getPointsByRace(Race.ASMODIANS).intValue();
        
        for (Player player : IdgelDome.getPlayersInside()) {
            if (PlayerActions.isAlreadyDead(player)) {
				PlayerReviveService.duelRevive(player);
			}
			IdgelDomePlayerReward playerReward = getPlayerReward(player.getObjectId());
            float abyssPoint = playerReward.getPoints() * RateConfig.IDGEL_DOME_ABYSS_REWARD_RATE;
            float gloryPoint = 50f * RateConfig.IDGEL_DOME_GLORY_REWARD_RATE;
            playerReward.setRewardAp((int) abyssPoint);
			playerReward.setRewardGp((int) gloryPoint);
			
			float PlayerRateModifire = player.getRates().getIdgelDomeBoxRewardRate();
			
            if (player.getRace().equals(idgelDomeReward.getWinningRace())) {
                abyssPoint += idgelDomeReward.CalcBonusAbyssReward(true, isBossKilledBy(player.getRace()));
                gloryPoint += idgelDomeReward.CalcBonusGloryReward(true, isBossKilledBy(player.getRace()));
                playerReward.setBonusAp(idgelDomeReward.CalcBonusAbyssReward(true, isBossKilledBy(player.getRace())));
                playerReward.setBonusGp(idgelDomeReward.CalcBonusGloryReward(true, isBossKilledBy(player.getRace())));
                playerReward.setReward1Count(6f * PlayerRateModifire);//Winner Team always got 6 <Fragmented Ceramium>
                playerReward.setReward2Count(1f * PlayerRateModifire);//Winner Team always got 1 <Idgel Dome Reward Box>
    			playerReward.setReward1(186000243);//Fragmented Ceramium
                playerReward.setReward2(188053030);//Idgel Dome Reward Box
            } else {
                abyssPoint += idgelDomeReward.CalcBonusAbyssReward(false, isBossKilledBy(player.getRace()));
                gloryPoint += idgelDomeReward.CalcBonusGloryReward(false, isBossKilledBy(player.getRace()));
                playerReward.setRewardAp(idgelDomeReward.CalcBonusAbyssReward(false, isBossKilledBy(player.getRace())));
                playerReward.setRewardGp(idgelDomeReward.CalcBonusGloryReward(false, isBossKilledBy(player.getRace())));
                playerReward.setReward1Count(2f * PlayerRateModifire);//Looser Team always got 2 <Fragmented Ceramium>
                playerReward.setReward2Count(0f * PlayerRateModifire);//Winner Team always got 0 <Idgel Dome Reward Box>
    			playerReward.setReward1(186000243);//Fragmented Ceramium
                playerReward.setReward2(0);//Idgel Dome Reward Box
            }

            /*
             * Idgel Dome Tribute Box (Additional Reward)
             * for The Team that killed the boss (Destroyer Kunax)
             */
            if (RaceKilledBoss == player.getRace()) {
            	playerReward.setAdditionalReward(188053032);
            	playerReward.setAdditionalRewardCount(1f * PlayerRateModifire);
            	ItemService.addItem(player, 188053032, (long) playerReward.getAdditionalRewardCount());
            }
            
            playerReward.setRewardCount(PlayerRateModifire);
            ItemService.addItem(player, 186000243, (long) playerReward.getReward1Count());//Fragmented Ceramium
            ItemService.addItem(player, 188053030, (long) playerReward.getReward2Count());//Idgel Dome Reward Box
            AbyssPointsService.addAp(player, (int) abyssPoint);
            AbyssPointsService.addGp(player, (int) gloryPoint);
        }
        
        for (Npc npc : IdgelDome.getNpcs()) {
			npc.getController().onDelete();
		}
        
        ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				if (!isInstanceDestroyed) {
					for (Player player : IdgelDome.getPlayersInside()) {
						onExitInstance(player);
					}
					AutoGroupService.getInstance().unRegisterInstance(instanceId);
				}
			}
		}, 15000);
    }
	
    @Override
    public boolean onReviveEvent(Player player) 
    {
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
        PlayerReviveService.revive(player, 100, 100, false, 0);
        player.getGameStats().updateStatsAndSpeedVisually();
        idgelDomeReward.portToPosition(player);
        
        Race OpponentRace = (player.getRace() == Race.ELYOS) ? Race.ASMODIANS : (player.getRace() == Race.ASMODIANS) ? Race.ELYOS : null;
        if (OpponentRace == null) {
        	return true;
        }
        if (getPointsByRace(player.getRace()).intValue() < getPointsByRace(OpponentRace).intValue()) {
	        /* Applies the BUFF_SHIELD (Underdog's Fervor) to Player for 30-Seconds */
	        getPlayerReward(player.getObjectId()).endResurrectionBuff(player);
	        getPlayerReward(player.getObjectId()).applyResurrectionBuff(player);        	
        }
        
        /* Send only when a PLAYER has been Revived  OnRevive() */
        IdgelDomePackets.sendPlayerRevivedPacket(player);
        
        return true;
    }
	
    @Override
    public boolean onDie(Player player, Creature lastAttacker) {
		PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), false, 0, 8));
        int points = 60;
        
        /* Send only when a PLAYER has been Killed (in PvP or PvE) */
        IdgelDomePackets.sendPlayerDiePacket(player);
        
        if (lastAttacker instanceof Player) {
            if (lastAttacker.getRace() != player.getRace()) {
                InstancePlayerReward playerReward = getPlayerReward(player.getObjectId());
				if (getPointsByRace(lastAttacker.getRace()).compareTo(getPointsByRace(player.getRace())) < 0) {
                    points *= loosingGroupMultiplier;
                } else if (loosingGroupMultiplier == 10 || playerReward.getPoints() == 0) {
                    points = 0;
                }
                updateScore((Player) lastAttacker, player, points, true);
            }
        }
        updateScore(player, player, -points, false);
        return true;
    }
	
    private boolean isBossKilledBy(Race PlayerRace) {
    	if (PlayerRace == RaceKilledBoss) {
    		return true;
    	}
    	return false;
    }
    
    private IdgelDomePlayerReward getPlayerReward(Integer ObjectId) {
        return idgelDomeReward.getPlayerReward(ObjectId);
    }
    
    private MutableInt getPvpKillsByRace(Race race) {
        return idgelDomeReward.getPvpKillsByRace(race);
    }
    
    private MutableInt getPointsByRace(Race race) {
        return idgelDomeReward.getPointsByRace(race);
    }
	
    private void addPointsByRace(Race race, int points) {
        idgelDomeReward.addPointsByRace(race, points);
    }
	
    private void addPvpKillsByRace(Race race, int points) {
        idgelDomeReward.addPvpKillsByRace(race, points);
    }
	
    private void addPointToPlayer(Player player, int points) {
        getPlayerReward(player.getObjectId()).addPoints(points);
    }
	
    private void addPvPKillToPlayer(Player player) {
        getPlayerReward(player.getObjectId()).addPvPKillToPlayer();
    }
	
	private void despawnNpc(Npc npc) {
		if (npc != null) {
			npc.getController().onDelete();
		}
	}
	
    protected void updateScore(Player player, Creature target, int points, boolean pvpKill) 
    {
        if (points == 0) {
            return;
        }
        addPointsByRace(player.getRace(), points);
        List<Player> playersToGainScore = new ArrayList<Player>();
        if (target != null && player.isInGroup2()) {
            for (Player member : player.getPlayerAlliance2().getOnlineMembers()) {
                if (member.getLifeStats().isAlreadyDead()) {
                    continue;
                } if (MathUtil.isIn3dRange(member, target, GroupConfig.GROUP_MAX_DISTANCE)) {
                    playersToGainScore.add(member);
                }
            }
        } else {
            playersToGainScore.add(player);
        }
        for (Player playerToGainScore : playersToGainScore) {
            addPointToPlayer(playerToGainScore, points / playersToGainScore.size());
            if (target instanceof Npc) {
                PacketSendUtility.sendPacket(playerToGainScore, new SM_SYSTEM_MESSAGE(1400237, new DescriptionId(((Npc) target).getObjectTemplate().getNameId() * 2 + 1), points));
            } else if (target instanceof Player) {
                PacketSendUtility.sendPacket(playerToGainScore, new SM_SYSTEM_MESSAGE(1400237, target.getName(), points));
            }
        }
        
        int pointDifference = getPointsByRace(Race.ASMODIANS).intValue() - (getPointsByRace(Race.ELYOS)).intValue();
        if (pointDifference < 0) {
            pointDifference *= -1;
        } if (pointDifference >= 3000) {
            loosingGroupMultiplier = 10;
        } else if (pointDifference >= 1000) {
            loosingGroupMultiplier = 1.5f;
        } else {
            loosingGroupMultiplier = 1;
        } 
        
        if (pvpKill && points > 0) {
            addPvpKillsByRace(player.getRace(), 1);
            addPvPKillToPlayer(player);
        }
        /* Send only when a NPC has been killed  OnNpcDie() */
        IdgelDomePackets.sendNpcScorePacket(player);
    }
	
    @Override
	public void onDie(Npc npc) {
        Player mostPlayerDamage = npc.getAggroList().getMostPlayerDamage();
        if (mostPlayerDamage == null) {
            return;
        }
        int Points = 0;
        int npcId = npc.getNpcId();
        
        switch (npcId) 
        {
        	case 234189: //Sheban Intelligence Unit Stitch.
        	case 234188: //Sheban Intelligence Unit Mongrel.
        	case 234187: //Sheban Intelligence Unit Hunter.
		    case 234186: //Sheban Intelligence Unit Ridgeblade.
            	Points = 120;
            	break;
		    case 234754: //Sheban Elite Medic.
		    case 234753: //Sheban Elite Marauder.
		    case 234752: //Sheban Elite Sniper.
			case 234751: //Sheban Elite Stalwart.
            	Points = 200;
            	break;
			case 234190: //Destroyer Kunax (Ku-Nag The Slayer)
				Points = 6000;
				RaceKilledBoss = mostPlayerDamage.getRace();
				stopInstance(idgelDomeReward.getWinningRaceByScore());
				break;
        }
        updateScore(mostPlayerDamage, npc, Points, false);
    }
	
    protected void stopInstance(Race race) 
    {
        stopInstanceTask();
        idgelDomeReward.setWinningRace(race);
        idgelDomeReward.setInstanceScoreType(InstanceScoreType.END_PROGRESS);
        reward();
        /* Reward Packet */
        IdgelDomePackets.sendScoreTypePacket();//Send 1-Time when Start_Progress and End_Progress
        IdgelDomePackets.sendRewardPacket();//Send Reward Packet  OnBossKilled()|OnTimeOut()
    }
	
    @Override
    public void onInstanceDestroy() 
    {
        stopInstanceTask();
        isInstanceDestroyed = true;
        idgelDomeReward.clear();
    }
	
    private void stopInstanceTask() 
    {
        for (FastList.Node<Future<?>> n = idgelTask.head(), end = idgelTask.tail(); (n = n.getNext()) != end; ) {
            if (n.getValue() != null) {
                n.getValue().cancel(true);
            }
        }
        IdgelDomePackets.ClearTasks();
    }
	
    @Override
    public InstanceReward<?> getInstanceReward() {
        return idgelDomeReward;
    }
	
    @Override
    public void onLeaveInstance(Player player) {
        if (player.isInGroup2()) {
            PlayerGroupService.removePlayer(player);
        }
        /* Player Leave Packet */
        IdgelDomePackets.sendPlayerLeavePacket(player);
    }
	
    @Override
    public void onPlayerLogin(Player player) {
        IdgelDomePackets.sendNpcScorePacket(player);
    }
    
    @Override
    public void onExitInstance(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
    }    
}