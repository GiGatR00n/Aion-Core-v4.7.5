package ai.AgentFight;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.services.BaseService;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.PacketSendUtility;

@AIName("empowered_veille")
public class Empowered_VeilleAI2 extends AggressiveNpcAI2
{
	private String walkerId = "LDF4_ADVANCE_NPCPATHGOD_L_04";
	
	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
	}
	
	@Override
    protected void handleSpawned() {
        super.handleSpawned();
        getSpawnTemplate().setWalkerId(walkerId);
		WalkManager.startWalking(this);
		getOwner().setState(1);
		PacketSendUtility.broadcastPacket(getOwner(), new SM_EMOTION(getOwner(), EmotionType.START_EMOTE2, 0, getObjectId()));
    }
	
	@Override
	protected void handleDied() {
		BaseService.getInstance().capture(90, Race.ASMODIANS);
		super.handleDied();
	}
}