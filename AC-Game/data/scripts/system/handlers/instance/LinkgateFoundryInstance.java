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
import com.aionemu.gameserver.controllers.effect.PlayerEffectController;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import com.aionemu.gameserver.world.zone.ZoneName;

import java.util.*;

/**
 * @rework Blackfire
 */
@InstanceID(301270000)
public class LinkgateFoundryInstance extends GeneralInstanceHandler
{
    private long startTime;
    protected boolean isInstanceDestroyed = false;
    boolean moviePlayed = false;
    private boolean isStartTimer = false;
    private boolean isElyos = false;
    private int keys;


    @Override
    public void onInstanceCreate(WorldMapInstance instance) {
        super.onInstanceCreate(instance);
    }
    
    @Override
    public void onEnterInstance(Player player) {
        super.onInstanceCreate(instance);
        if (player.getRace() == Race.ELYOS) {
        	isElyos = true;
        	spawn(206361, 348.00464f, 252.13882f, 311.36136f, (byte) 10);//Ketesivius
        	spawn(855087, 226.8177f, 256.8576f, 312.37897f, (byte) 0);
        } else {
        	isElyos = false;
        	spawn(206362, 348.00464f, 252.13882f, 311.36136f, (byte) 10);//Aitu
        	spawn(855088, 226.8177f, 256.8576f, 312.37897f, (byte) 0);
        }
    }
    
    @Override
    public void onEnterZone(Player player, ZoneInstance zone) {
        if (zone.getAreaTemplate().getZoneName() == ZoneName.get("IDLDF4RE_01_ITEMUSEAREA")) {
            instance.doOnAllPlayers(new Visitor<Player>() {
                @Override
                public void visit(final Player player) {
                    if (player.isOnline()) {
                        if (isStartTimer) {
                            long time = System.currentTimeMillis() - startTime;
                            if (time < 1200000) {
                                PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 1200 - (int) time / 1000));
                            }
                        }
                        if (!isStartTimer) {
                            isStartTimer = true;
                            System.currentTimeMillis();
                            PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 1200)); //20 Minutes.
                            PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1402453));
                        }
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                if (getNpc(233887) != null ) {
                                    despawnNpcs(instance.getNpcs(233887));
                                }
                                if (getNpc(233888) != null ) {
									despawnNpcs(instance.getNpcs(233888));
                                }
								if (getNpc(233889) != null ) {
									despawnNpcs(instance.getNpcs(233889));
                                }
								if (getNpc(233890) != null ) {
									despawnNpcs(instance.getNpcs(233890));
                                }
								if (getNpc(233891) != null ) {
									despawnNpcs(instance.getNpcs(233891));
                                }
								if (getNpc(233892) != null ) {
									despawnNpcs(instance.getNpcs(233892));
                                }
								if (getNpc(233893) != null ) {
									despawnNpcs(instance.getNpcs(233893));
                                }
								if (getNpc(233894) != null ) {
									despawnNpcs(instance.getNpcs(233894));
                                }
								if (getNpc(233895) != null ) {
									despawnNpcs(instance.getNpcs(233895));
                                }
								if (getNpc(233896) != null ) {
									despawnNpcs(instance.getNpcs(233896));
                                }
								if (getNpc(233897) != null ) {
									despawnNpcs(instance.getNpcs(233897));
                                }
							}	
                        }, 1201000);
                    }
                }
            });
        }
    }
    
    @Override
	 public void onDie(Npc npc) {
		 switch (npc.getNpcId()) {
			 case 233887:
				 keys++;
				 break;
			 case 233898:
			 case 234990:
			 case 234991:
				 if (isElyos) {
					 spawn(702338, 246.74345f, 258.35843f, 312.32327f, (byte) 10);
				 } else {
					 spawn(702389, 246.74345f, 258.35843f, 312.32327f, (byte) 10);
				 }
				 break;
		 }
		 
	 }
    
    @Override
	 public void handleUseItemFinish(Player player, Npc npc) {
		 switch (npc.getNpcId()) {
			 case 234193:
			 case 804578:
				 despawnNpc(npc);
				 break;
			 case 804629:
				 if (keys >= 1 && keys <= 5) {
					 spawn(234990, 246.74345f, 258.35843f, 312.32327f, (byte) 10);
					 TeleportService2.teleportTo(player, 301270000, 255, 260, 312);
					 despawnNpc(npc);
				 } else if (keys >= 5 && keys < 7) {
					 spawn(233898, 246.74345f, 258.35843f, 312.32327f, (byte) 10);
					 TeleportService2.teleportTo(player, 301270000, 255, 260, 312);
					 despawnNpc(npc);
				 } else if (keys >= 7) {
					 spawn(234991, 246.74345f, 258.35843f, 312.32327f, (byte) 10);
					 TeleportService2.teleportTo(player, 301270000, 255, 260, 312);
					 despawnNpc(npc);
				 } else {
					 spawn(234990, 246.74345f, 258.35843f, 312.32327f, (byte) 10);
					 TeleportService2.teleportTo(player, 301270000, 255, 260, 312);
					 despawnNpc(npc);
				 }
		 }
	 }
    
    @Override
	 public void onDropRegistered(Npc npc) {
	     Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
	     int npcId = npc.getNpcId();
	     switch (npcId) {
	        case 233887: // Key Keeper
	            dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 185000196, 1)); // Abyss Gap Sealing Key
	            break;
			case 233898: //Volatile Belsagos.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 188053331, 1)); //Linkgate Foundry Small Ancient Manastone Bundle
				break;
			case 234990: //Wounded Belsagos.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 188053332, 1)); //Linkgate Foundry Heavy Ancient Manastone Bundle
				break;
			case 234991: //Furious Belsagos.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 188053333, 1)); //Linkgate Foundry Noble Ancient Manastone Bundle
				break;
			case 233891: //Thecynon Bruiser.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 164000292, 1)); //Research Luminary Cloaking Scroll.
				break;
			case 234992: //Linkgate Foundry Supply Chest.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 188053238, 1)); //Scroll Bundle (Linkgate Foundry).
				break;
			case 234993: //Linkgate Foundry Supply Chest.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 188053239, 1)); //Scroll Bundle (Linkgate Foundry).
				break;	 
			case 234194: //Linkgate Foundry Supply Chest.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 188053238, 1)); //Scroll Bundle (Linkgate Foundry).
				break;
			case 234195: //Linkgate Foundry Supply Chest.
				dropItems.add(DropRegistrationService.getInstance().regDropItem(1, 0, npcId, 188053239, 1)); //Scroll Bundle (Linkgate Foundry).
				break;	 
	    }
	}

    private void removeEffects(Player player) {
        PlayerEffectController effectController = player.getEffectController();
        effectController.removeEffect(0);
    }

    @Override
    public void onPlayerLogOut(Player player) {
        removeEffects(player);
    }
    
    @Override
    public void onLeaveInstance(Player player) {
		PacketSendUtility.sendPacket(player, new SM_QUEST_ACTION(0, 0)); //cancel timer
        removeEffects(player);
		onInstanceDestroy();
    }
    
    @Override
    public void onInstanceDestroy() {
        isInstanceDestroyed = true;
    }
    
    @Override
    public void onExitInstance(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
		onInstanceDestroy();
    }
    
    protected void despawnNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }
    
    protected void despawnNpcs(List<Npc> npcs) {
        for (Npc npc: npcs) {
            npc.getController().onDelete();
        }
    }
    
    @Override
    protected Npc getNpc(int npcId) {
        if (!isInstanceDestroyed) {
            return instance.getNpc(npcId);
        }
        return null;
    }
    
    protected List<Npc> getNpcs(int npcId) {
        if (!isInstanceDestroyed) {
            return instance.getNpcs(npcId);
        }
        return null;
    }
    
    @Override
    public boolean onReviveEvent(Player player) {
        PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
        PlayerReviveService.revive(player, 100, 100, false, 0);
        player.getGameStats().updateStatsAndSpeedVisually();
        return TeleportService2.teleportTo(player, mapId, instanceId, 364.14f, 259.84f, 311.4f, (byte) 60);
    }
    
    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }
}