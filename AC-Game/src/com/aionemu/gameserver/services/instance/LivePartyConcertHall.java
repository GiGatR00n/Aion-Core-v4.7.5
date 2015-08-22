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
package com.aionemu.gameserver.services.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.services.CronService;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.EventsConfig;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;


public class LivePartyConcertHall {

	private static final Logger log = LoggerFactory.getLogger(LivePartyConcertHall.class);
	
	private static final String LIVE_PARTY_SPAWN_SCHEDULE = EventsConfig.LIVE_PARTY_SPAWN_SCHEDULE;
	private int maxAllowed = EventsConfig.LIVE_PARTY_MAX_PLAYERS;
	
	private int worldId = 600080000;
	private int counter;
	private String allRace = "ALL";
	private boolean prepared = false;
	private boolean isActive = false;
	
	private FastMap<Integer, VisibleObject> spawnPhase1 = new FastMap<Integer, VisibleObject>();
	private FastMap<Integer, VisibleObject> spawnPhase2 = new FastMap<Integer, VisibleObject>();
	private FastMap<Integer, VisibleObject> spawnPhase3 = new FastMap<Integer, VisibleObject>();
	private FastMap<Integer, VisibleObject> spawnPhase4 = new FastMap<Integer, VisibleObject>();
	private FastMap<Integer, VisibleObject> portalSpawns = new FastMap<Integer, VisibleObject>();
	private List<Player> players = new ArrayList<Player>();
	
	
	public void init() {
		players.clear();
		spawnPhase1.clear();
		spawnPhase2.clear();
		spawnPhase3.clear();
		spawnPhase4.clear();
		portalSpawns.clear();
		
		if (EventsConfig.LIVE_PARTY_ENABLE) 
			setActive(true);
		else
			setActive(false);
		
		if (EventsConfig.LIVE_PARTY_RACE_ALL == "ALL") 
			setRace("ALL");
		else if (EventsConfig.LIVE_PARTY_RACE_ALL == "ELYOS") 
			setRace("ELYOS");
		else if (EventsConfig.LIVE_PARTY_RACE_ALL == "ASMODIANS") 
			setRace("ASMODIANS");
		
		if (isActive()) {
			log.info("Starting Live Party Service...");
			startChecker();
		}
	}
	
	private static class SingletonHolder {
        protected static final LivePartyConcertHall instance = new LivePartyConcertHall();
    }

    public static LivePartyConcertHall getInstance() {
        return SingletonHolder.instance;
    }

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getRace() {
		return allRace;
	}

	public void setRace(String race) {
		this.allRace = race;
	}	
	
	private void stopConcert() {
		for (final Player player : players) {
			PacketSendUtility.sendBrightYellowMessageOnCenter(player, "Live Party Concert Hall Event is now over. Thanks for visiting.");
			PacketSendUtility.sendBrightYellowMessageOnCenter(player, "You will got ported out in 3 Minutes automaticly.");
		}
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				for (final Player player : players) {
					log.info("[Live Party] Porting now all players out...");
					TeleportService2.moveToBindLocation(player, true);
				}
				players.clear();
				log.info("Live Party Concert Event ended.");
			}

		}, 180 * 1000);//3 min
	}	
	
	public void onEnterMap(Player player) {
		if (getRace() == "ELYOS" && player.getRace() != Race.ELYOS) {
			return;
		} else if (getRace() == "ASMODIANS" && player.getRace() != Race.ASMODIANS) {
			return;
		}
		if (counter++ == maxAllowed) {
			return;
		} else {
			counter++;
		}
		player.setAdminNeutral(2); // cuz we wont allow fights
		player.setInLiveParty(true);
		players.add(player);
	}
	
	public void onLeaveMap(Player player) {
		if (counter-- < 0) {
			counter = 0;
		} else {
			counter--;
		}
		player.setAdminNeutral(0);
		player.setInLiveParty(false);
		players.remove(player);
	}
	
	private void prepareConcert() {
		ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (prepared)
					return;
				
					prepared = true;
					spawnPhase1();
					log.info("[Live Party] The show begins...");
			}
			
		}, 1200 * 1000, 1200 * 1000);//20 minutes to start
	}
	
	private ScheduledFuture<?> announceTask(int delayInMinutes) {
        return ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
        		if (prepared) return;
        		World.getInstance().doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(Player object) {
                        PacketSendUtility.sendBrightYellowMessageOnCenter(object, "Live Party Concert Hall Event started. The show will begin soon.");
                    }
                });
            }
        }, delayInMinutes / 2 * 1000 * 60, delayInMinutes / 2 * 1000 * 60);
    }	
	
	private void startChecker() {
		CronService.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
            	// Elyos Portals...
            	portalSpawns.put(831592, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(110010000, 831592, 1453f, 1537f, 573.0719f, (byte) 75), 1));//Portal
            	portalSpawns.put(831592, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(110010000, 831592, 1599f, 1383f, 563.5009f, (byte) 50), 1));//Portal
            	portalSpawns.put(831592, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(110010000, 831592, 1854f, 1418f, 590.242f, (byte) 106), 1));//Portal
            	portalSpawns.put(831592, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(110010000, 831592, 1855f, 1600f, 590.242f, (byte) 10), 1));//Portal
            	
            	// Asmodian Portals...
            	portalSpawns.put(831592, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(120010000, 831592, 1191f, 1262f, 208.125f, (byte) 26), 1));//Portal
            	portalSpawns.put(831592, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(120010000, 831592, 953f, 1404f, 213.016f, (byte) 21), 1));//Portal
            	portalSpawns.put(831592, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(120010000, 831592, 1625f, 1379f, 193.1272f, (byte) 39), 1));//Portal
            	portalSpawns.put(831592, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(120010000, 831592, 1351f, 1362f, 208.125f, (byte) 57), 1));//Portal
            	
            	log.info("[Live Party] Entrance spawned in captial citys...");
            	prepareConcert();
            	announceTask(5);// every 5 min announce
            	ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
        			@Override
        			public void run() {
        				for (VisibleObject vo : portalSpawns.values()) {
        					if (vo != null) {
        						Npc npc = (Npc) vo;
        						if (!npc.getLifeStats().isAlreadyDead()) {
        							npc.getController().onDelete();
        						}
        					}
        				}
        				portalSpawns.clear();
    					log.info("[Live Party] Entrance Portals are removed...");
        			}
        			
        		}, 2400 * 1000, 2400 * 1000);//after 40 min portals will despawn
            }
        }, LIVE_PARTY_SPAWN_SCHEDULE, true);
	}
	
	// You and I
	private void spawnPhase1() {
		log.info("[Live Party] Phase 1 You and I started");
		spawnPhase1.put(831630, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831573, 1551.627441f, 1511.607300f, 567.63f, (byte) 60), 1));//IU
		spawnPhase1.put(831654, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831654, 1532.48f, 1511.5601f, 565.88226f, (byte) 90), 1));//Lightning
		spawnPhase1.put(831633, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831633, 1553.855347f, 1505.208984f, 567.63f, (byte) 60), 1));//Dancer1
		spawnPhase1.put(831634, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831634, 1551.578369f, 1507.780884f, 567.63f, (byte) 60), 1));//Dancer2
		spawnPhase1.put(831635, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831635, 1553.864624f, 1509.456909f, 567.63f, (byte) 60), 1));//Dancer3
		spawnPhase1.put(831637, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831637, 1553.891479f, 1513.959717f, 567.63f, (byte) 60), 1));//Dancer5
		spawnPhase1.put(831638, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831638, 1551.542480f, 1515.475464f, 567.63f, (byte) 60), 1));//Dancer6
		spawnPhase1.put(831639, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831639, 1553.855469f, 1517.878662f, 567.63f, (byte) 60), 1));//Dancer7

		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				for (VisibleObject vo : spawnPhase1.values()) {
					if (vo != null) {
						Npc npc = (Npc) vo;
						if (!npc.getLifeStats().isAlreadyDead()) {
							npc.getController().onDelete();
						}
					}
				}
				spawnPhase1.clear();
				log.info("[Live Party] Phase 1 You and I finished");
				spawnPhase2();
			}

		}, 300 * 1000);//5 min
	
	}
	
	// Good Day
	private void spawnPhase2() {
		log.info("[Live Party] Phase 2 Good Day started");
		spawnPhase2.put(831630, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831573, 1551.627441f, 1511.607300f, 567.63f, (byte) 60), 1));//IU
		spawnPhase2.put(831655, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831655, 1532.48f, 1511.5601f, 565.88226f, (byte) 90), 1));//Lightning
		spawnPhase2.put(831640, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831640, 1551.468872f, 1508.395752f, 567.63f, (byte) 60), 1));//Dancer1
		spawnPhase2.put(831641, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831641, 1553.862183f, 1510.006714f, 567.63f, (byte) 60), 1));//Dancer2
		spawnPhase2.put(831642, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831642, 1553.847412f, 1513.175659f, 567.63f, (byte) 60), 1));//Dancer3
		spawnPhase2.put(831643, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831643, 1551.448608f, 1514.730347f, 567.63f, (byte) 60), 1));//Dancer4
		spawnPhase2.put(831644, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831644, 1551.463379f, 1505.194214f, 567.63f, (byte) 60), 1));//Dancer5
		spawnPhase2.put(831645, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831645, 1553.862183f, 1506.808350f, 567.63f, (byte) 60), 1));//Dancer6
		spawnPhase2.put(831646, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831646, 1553.876953f, 1516.329590f, 567.63f, (byte) 60), 1));//Dancer7
		spawnPhase2.put(831647, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831647, 1551.463379f, 1517.884399f, 567.63f, (byte) 60), 1));//Dancer8

		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				for (VisibleObject vo : spawnPhase2.values()) {
					if (vo != null) {
						Npc npc = (Npc) vo;
						if (!npc.getLifeStats().isAlreadyDead()) {
							npc.getController().onDelete();
						}
					}
				}
				spawnPhase2.clear();
				log.info("[Live Party] Phase 2 Good Day finished");
				spawnPhase3();
			}

		}, 300 * 1000);//5 min 
		
	}
	
	// Theme Song
	private void spawnPhase3() {
		log.info("[Live Party] Phase 3 Theme Song started");
		spawnPhase3.put(831630, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831573, 1551.627441f, 1511.607300f, 567.63f, (byte) 60), 1));//IU
		spawnPhase3.put(831656, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831656, 1532.48f, 1511.5601f, 565.88226f, (byte) 90), 1));//Lightning
		spawnPhase3.put(831648, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831648, 1551.003296f, 1504.785889f, 567.63f, (byte) 60), 1));//Surama
		spawnPhase3.put(831649, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831649, 1555.196167f, 1513.182861f, 567.63f, (byte) 60), 1));//Kahrun
		spawnPhase3.put(831650, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831650, 1555.168823f, 1509.818237f, 567.63f, (byte) 60), 1));//Kromede
		spawnPhase3.put(831651, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831651, 1552.657959f, 1507.320190f, 567.63f, (byte) 60), 1));//Tiamat
		spawnPhase3.put(831652, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831652, 1552.663696f, 1515.728027f, 567.63f, (byte) 60), 1));//Israpel
		spawnPhase3.put(831653, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831653, 1550.960449f, 1518.190186f, 567.63f, (byte) 60), 1));//Vasharti

		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				for (VisibleObject vo : spawnPhase3.values()) {
					if (vo != null) {
						Npc npc = (Npc) vo;
						if (!npc.getLifeStats().isAlreadyDead()) {
							npc.getController().onDelete();
						}
					}
				}
				spawnPhase3.clear();
				log.info("[Live Party] Phase 3 Theme Song finished");
				spawnPhase4();
			}

		}, 300 * 1000);//5 min 
	}
	
	// New Song 
	// Random chance to see this nice thing :D
	private void spawnPhase4() {
		int chance = Rnd.get(1, 100);
		if (chance < 50) {
			stopConcert();
		}	
		log.info("[Live Party] Phase 4 New Song started");
		spawnPhase4.put(831630, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831573, 1551.627441f, 1511.607300f, 567.63f, (byte) 60), 1));//IU
		spawnPhase4.put(831656, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831656, 1532.48f, 1511.5601f, 565.88226f, (byte) 90), 1));//Lightning
		spawnPhase4.put(831655, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831655, 1532.48f, 1511.5601f, 565.88226f, (byte) 90), 1));//Lightning
		spawnPhase4.put(831654, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831654, 1532.48f, 1511.5601f, 565.88226f, (byte) 90), 1));//Lightning
		spawnPhase4.put(831617, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831617, 1553.855347f, 1505.208984f, 567.63f, (byte) 60), 1));//Dancer1
		spawnPhase4.put(831618, SpawnEngine.spawnObject(SpawnEngine.addNewSingleTimeSpawn(worldId, 831618, 1551.578369f, 1507.780884f, 567.63f, (byte) 60), 1));//Dancer2

		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				for (VisibleObject vo : spawnPhase4.values()) {
					if (vo != null) {
						Npc npc = (Npc) vo;
						if (!npc.getLifeStats().isAlreadyDead()) {
							npc.getController().onDelete();
						}
					}
				}
				spawnPhase4.clear();
				log.info("[Live Party] Phase 4 New Song finished");
				stopConcert();
			}
		}, 300 * 1000);//5 min 
	}
}