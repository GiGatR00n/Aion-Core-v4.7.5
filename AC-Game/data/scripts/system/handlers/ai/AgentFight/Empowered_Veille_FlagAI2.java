package ai.AgentFight;

import java.util.*;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.PacketSendUtility;

@AIName("empowered_veille_flag")
public class Empowered_Veille_FlagAI2 extends AggressiveNpcAI2
{
	private String walkerId = "LDF4_ADVANCE_PATHGOD_L_04";
	
	@Override
    protected void handleSpawned() {
        super.handleSpawned();
        getSpawnTemplate().setWalkerId(walkerId);
		WalkManager.startWalking(this);
		getOwner().setState(1);
		PacketSendUtility.broadcastPacket(getOwner(), new SM_EMOTION(getOwner(), EmotionType.START_EMOTE2, 0, getObjectId()));
    }
}