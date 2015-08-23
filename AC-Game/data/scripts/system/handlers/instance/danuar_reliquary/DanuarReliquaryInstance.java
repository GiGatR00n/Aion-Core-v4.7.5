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

package instance.danuar_reliquary;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.World;

import com.aionemu.gameserver.network.aion.serverpackets.SM_FORCED_MOVE;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.Map;
import java.util.Set;
import java.util.List;

/**
 * @rework Everlight
 * @rework Sayem
 */

@InstanceID(301110000)
public class DanuarReliquaryInstance extends GeneralInstanceHandler {

	private Map < Integer, StaticDoor > doors;
	private int guardGraendalKilled;
	private int IllusionGraendalKilled;
	@SuppressWarnings("unused")
	private boolean isInstanceDestroyed;

	byte CloneKill;
	byte CloneCount;

	@Override
	public void onEnterInstance(Player player) {
		super.onInstanceCreate(instance);
		spawn(284375, 255.57027f, 293.0893f, 253.79536f, (byte) 90); 	//Spawn Cursed Queen Modor - Construct
		spawn(284447, 256.5698f, 257.8559f, 241.9354f, (byte) 0); 		//Spawn Damage Circle - Invisible (Blue Water)
		/**
		 * WTF IS THIS ? Remove NPC ID effect ? LOL :D
		 * player.getEffectController().removeEffect(218611);
		 * player.getEffectController().removeEffect(218610);
		 */
	}

	@Override
	public void onDie(Npc npc) {
		final Npc CQM0 = getNpc(284375); //Cursed Queen Modor - Construct
		final Npc CQM1 = getNpc(231304); //Cursed Queen Modor - Attackable Boss
		final Npc NPC0 = getNpc(284664);
		final Npc NPC1 = getNpc(284380);
		final Npc NPC2 = getNpc(284381);
		final Npc NPC3 = getNpc(284382);
		final Npc NPC4 = getNpc(284663);
		final Npc NPC5 = getNpc(284660);
		final Npc NPC6 = getNpc(284661);
		final Npc NPC7 = getNpc(284662);
		final Npc NPC8 = getNpc(284659);
		final Npc NPC9 = getNpc(284384);
		switch (npc.getNpcId()) {
			case 284377:
			case 284378:
			case 284379:
				guardGraendalKilled++;
				if (guardGraendalKilled == 3) {
					CQM0.getController().onDelete();
					spawn(231304, 256.52f, 257.87f, 241.78f, (byte) 90); //Cursed Queen Modor - Attackable Boss
				}
				despawnNpc(npc);
				break;
			case 284380:
				NPC1.getController().onDelete();
				if (isDead(NPC0) && isDead(NPC1) && isDead(NPC2)) {
					Teleport();
				}
				break;
			case 284381:
				NPC2.getController().onDelete();
				if (isDead(NPC0) && isDead(NPC1) && isDead(NPC2)) {
					Teleport();
				}
				break;
			case 284382:
				NPC3.getController().onDelete();
				if (isDead(NPC3) && isDead(NPC4) && isDead(NPC5)) {
					Teleport2();
				}
				break;
			case 284383:
				CQM1.getController().onAttack(CQM1, 125000, true);
				CloneKill++;
				CloneCount++;
				if (CloneCount == 1 || CloneCount == 2) {
					deleteNpcs(instance.getNpcs(284384));
					switch (Rnd.get(0, 2)) {
						case 0:
							spawn(284384, 284.504f, 262.939f, 248.7541f, (byte) 66);
							spawn(284383, 271.426f, 230.243f, 250.9022f, (byte) 38);
							spawn(284384, 240.130f, 235.219f, 251.1553f, (byte) 17);
							spawn(284384, 232.541f, 263.877f, 248.6566f, (byte) 115);
							spawn(284384, 255f, 293f, 253f, (byte) 88);
							break;
						case 1:
							spawn(284384, 232.426f, 263.818f, 248.6419f, (byte) 115);
							spawn(284384, 271.426f, 230.243f, 250.9022f, (byte) 38);
							spawn(284383, 240.130f, 235.219f, 251.1553f, (byte) 17);
							spawn(284384, 284.504f, 262.939f, 248.7541f, (byte) 66);
							spawn(284384, 255f, 293f, 253f, (byte) 88);
							break;
						case 2:
							spawn(284384, 232.426f, 263.818f, 248.6419f, (byte) 115);
							spawn(284384, 271.426f, 230.243f, 250.9022f, (byte) 38);
							spawn(284384, 240.130f, 235.219f, 251.1553f, (byte) 17);
							spawn(284383, 284.504f, 262.939f, 248.7541f, (byte) 66);
							spawn(284384, 255f, 293f, 253f, (byte) 88);
							break;
					}
				}
				if (CloneKill == 3) {
					FakeTeleportToEnrraged();
					deleteNpcs(instance.getNpcs(284384));
				}
				break;
			case 284384:
				CQM1.getController().onAttack(CQM1, 125000, true);
				break;
			case 231304:
				NPC9.getController().onDelete();
				break;
			case 231305:
				spawn(730843, 255.66669f, 263.78525f, 243.71048f, (byte) 86);
				/*				sendMsg(1401893);
                despawnNpc(npc);
                spawn(730843, 256.2082f, 250.2959f, 241.8779f, (byte) 30); //exit
                spawn(701795, 256.5212f, 258.3195f, 241.7859f, (byte) 89); //treasure box */
				break;
			case 284659:
				NPC8.getController().onDelete();
				if (isDead(NPC6) && isDead(NPC7) && isDead(NPC8)) {
					Teleport2();
				}
				break;
			case 284660:
				NPC5.getController().onDelete();
				if (isDead(NPC3) && isDead(NPC4) && isDead(NPC5)) {
					Teleport2();
				}
				break;
			case 284661:
				NPC6.getController().onDelete();
				if (isDead(NPC6) && isDead(NPC7) && isDead(NPC8)) {
					Teleport2();
				}
				break;
			case 284662:
				NPC7.getController().onDelete();
				if (isDead(NPC6) && isDead(NPC7) && isDead(NPC8)) {
					Teleport2();
				}
				break;
			case 284663:
				NPC4.getController().onDelete();
				if (isDead(NPC3) && isDead(NPC4) && isDead(NPC5)) {
					Teleport2();
				}
				break;
			case 284664:
				NPC0.getController().onDelete();
				if (isDead(NPC0) && isDead(NPC1) && isDead(NPC2)) {
					Teleport();
				}
				break;
		}
	}

	private boolean isDead(Npc npc) {
		return (npc == null || npc.getLifeStats().isAlreadyDead());
	}

	private void Teleport() {
		final Npc CQM1 = getNpc(231304);
		SkillEngine.getInstance().getSkill(CQM1, 21165, 65, CQM1).useNoAnimationSkill();
		ThreadPoolManager.getInstance().schedule(new Runnable() {@Override
			public void run() {
				World.getInstance().updatePosition(CQM1, 256, 257, 242, (byte) 90);
				PacketSendUtility.broadcastPacketAndReceive(CQM1, new SM_FORCED_MOVE(CQM1, CQM1));
			}
		}, 2000);
	}

	private void Teleport2() {
		final Npc CQM2 = getNpc(231305);
		SkillEngine.getInstance().getSkill(CQM2, 21165, 65, CQM2).useNoAnimationSkill();
		ThreadPoolManager.getInstance().schedule(new Runnable() {@Override
			public void run() {
				World.getInstance().updatePosition(CQM2, 256, 257, 242, (byte) 90);
				PacketSendUtility.broadcastPacketAndReceive(CQM2, new SM_FORCED_MOVE(CQM2, CQM2));
			}
		}, 2000);
	}

	private void FakeTeleportToEnrraged() {
		final Npc CQM1 = getNpc(231304);
		SkillEngine.getInstance().getSkill(CQM1, 21165, 65, CQM1).useNoAnimationSkill();
		ThreadPoolManager.getInstance().schedule(new Runnable() {@Override
			public void run() {
				CQM1.getController().onDelete();
				spawn(231305, 256.4457f, 257.6867f, 242.30f, (byte) 90);
			}
		}, 2000);
	}

	@Override
	public void onInstanceDestroy() {
		isInstanceDestroyed = true;
		doors.clear();
	}

	@Override
	public void onInstanceCreate(WorldMapInstance instance) {
		super.onInstanceCreate(instance);
		doors = instance.getDoors();
	}

	private void deleteNpcs(List < Npc > npc) {
		for (Npc NPC: npc) {
			if (NPC != null) NPC.getController().onDelete();
		}
	}

	private void despawnNpc(Npc npc) {
		if (npc != null) {
			npc.getController().onDelete();
		}
	}

	@Override
	public void onDropRegistered(Npc npc) {
		Set < DropItem > dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
		int npcId = npc.getNpcId();
		switch (npcId) {
			case 231304:
			case 231305:
				int index = dropItems.size() + 1;
				for (Player player: instance.getPlayersInside()) {
					if (player.isOnline()) {
						dropItems.add(DropRegistrationService.getInstance().regDropItem(index++, player.getObjectId(), npcId, 188052388, 1)); //Modor's Equipment Box.
					}
				}
				break;
		}
	}

	@Override
	public boolean onDie(final Player player, Creature lastAttacker) {
		PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
		PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
		return true;
	}
}