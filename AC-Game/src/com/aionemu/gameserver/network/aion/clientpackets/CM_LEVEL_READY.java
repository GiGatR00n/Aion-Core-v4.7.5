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
package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.configs.main.EventsConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Pet;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.StorageType;
import com.aionemu.gameserver.model.templates.windstreams.Location2D;
import com.aionemu.gameserver.model.templates.windstreams.WindstreamTemplate;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUBE_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_HOUSE_OBJECTS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INSTANCE_COUNT_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPGRADE_ARCADE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_WINDSTREAM_ANNOUNCE;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.services.BaseService;
import com.aionemu.gameserver.services.FastTrackService;
import com.aionemu.gameserver.services.SerialKillerService;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.services.TownService;
import com.aionemu.gameserver.services.WeatherService;
import com.aionemu.gameserver.services.instance.LivePartyConcertHall;
import com.aionemu.gameserver.services.rift.RiftInformer;
import com.aionemu.gameserver.spawnengine.InstanceRiftSpawnManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapType;

/**
 * Client is saying that level[map] is ready.
 *
 * @author -Nemesiss-
 * @author Kwazar
 */
public class CM_LEVEL_READY extends AionClientPacket {

	public CM_LEVEL_READY(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	@Override
	protected void readImpl() {
		PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
	}

	@Override
	protected void runImpl() {
		Player activePlayer = getConnection().getActivePlayer();

		if (activePlayer.getHouseRegistry() != null) {
			sendPacket(new SM_HOUSE_OBJECTS(activePlayer));
		}
		if (activePlayer.isInInstance()) {
			sendPacket(new SM_INSTANCE_COUNT_INFO(activePlayer.getWorldId(), activePlayer.getInstanceId()));
		}
		sendPacket(new SM_PLAYER_INFO(activePlayer, false));
		activePlayer.getController().startProtectionActiveTask();
		sendPacket(new SM_MOTION(activePlayer.getObjectId(), activePlayer.getMotions().getActiveMotions()));

		WindstreamTemplate template = DataManager.WINDSTREAM_DATA.getStreamTemplate(activePlayer.getPosition().getMapId());
		Location2D location;
		if (template != null) {
			for (int i = 0; i < template.getLocations().getLocation().size(); i++) {
				location = template.getLocations().getLocation().get(i);
				sendPacket(new SM_WINDSTREAM_ANNOUNCE(location.getFlyPathType().getId(), template.getMapid(), location.getId(),
					location.getState()));
			}
		}
		location = null;
		template = null;

		/**
		 * Spawn player into the world.
		 */
		 // If already spawned, despawn before spawning into the world
		 if (activePlayer.isSpawned()) {
			 World.getInstance().despawn(activePlayer);
		 }
		 World.getInstance().spawn(activePlayer);

		 activePlayer.getController().refreshZoneImpl();

		 // SM_SHIELD_EFFECT, SM_ABYSS_ARTIFACT_INFO3
		 if (activePlayer.isInSiegeWorld()) {
			 SiegeService.getInstance().onEnterSiegeWorld(activePlayer);
		 }

		 //SM_NPC_INFO Bases
		 BaseService.getInstance().onEnterBaseWorld(activePlayer);

		 // SM_SERIAL_KILLER
		 SerialKillerService.getInstance().onEnterMap(activePlayer);

		 // because fortress buff should only be avaible in fortress locations
		 SiegeService.getInstance().fortressBuffRemove(activePlayer);

		 // SM_RIFT_ANNOUNCE
		 RiftInformer.sendRiftsInfo(activePlayer);
		 InstanceRiftSpawnManager.sendInstanceRiftStatus(activePlayer);

		 //SM_UPGRADE_ARCADE
		 if (EventsConfig.ENABLE_EVENT_ARCADE && activePlayer.getLevel() >= 50) {
	            sendPacket(new SM_UPGRADE_ARCADE(true));
	        }

		 // SM_NEARBY_QUESTS
		 activePlayer.getController().updateNearbyQuests();

		 if (activePlayer.isOnFastTrack()) {
			 if (activePlayer.FAST_TRACK_TYPE == 1) {
				 activePlayer.FAST_TRACK_TYPE = 2;
			 } else if (activePlayer.FAST_TRACK_TYPE == 2) {
				 FastTrackService.getInstance().handleMoveBack(activePlayer);
			 }
		 }

		 /**
		  * Loading weather for the player's region
		  */
		 WeatherService.getInstance().loadWeather(activePlayer);

		 QuestEngine.getInstance().onEnterWorld(new QuestEnv(null, activePlayer, 0, 0));

		 activePlayer.getController().onEnterWorld();

		 // zone channel message (is this must be here?)
		 if (!WorldMapType.getWorld(activePlayer.getWorldId()).isPersonal()) {
			 sendPacket(new SM_SYSTEM_MESSAGE(1390122, activePlayer.getPosition().getInstanceId()));
		 }

		 activePlayer.getEffectController().updatePlayerEffectIcons();
		 sendPacket(SM_CUBE_UPDATE.cubeSize(StorageType.CUBE, activePlayer));

		 Pet pet = activePlayer.getPet();
		 if (pet != null && !pet.isSpawned()) {
			 World.getInstance().spawn(pet);
		 }
		 activePlayer.setPortAnimation(0);

		 TownService.getInstance().onEnterWorld(activePlayer);

		 // Live Party Concert Hall
		 if (activePlayer.getWorldId() == 600080000) {
			 LivePartyConcertHall.getInstance().onEnterMap(activePlayer);
		 } else if (activePlayer.isInLiveParty()) {
			 LivePartyConcertHall.getInstance().onLeaveMap(activePlayer);
		 }
	}
}
