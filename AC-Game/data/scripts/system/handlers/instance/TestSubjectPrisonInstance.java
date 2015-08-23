package instance;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.summons.UnsummonType;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Romanz
 */
@InstanceID(301000000)
public class TestSubjectPrisonInstance extends GeneralInstanceHandler {

    @Override
    public void onDie(Npc npc) {
        switch (npc.getObjectTemplate().getTemplateId()) {
            case 206288:
            case 206290:
            case 231067:
            case 231068:
            case 231069:
		despawnNpc(npc);
		break;
        }
    }

	private void despawnNpc(Npc npc) {
		if (npc != null) {
			npc.getController().onDelete();
		}
	}
    
    @Override
    public boolean onDie(final Player player, Creature lastAttacker) {
        Summon summon = player.getSummon();
        if (summon != null) {
            summon.getController().release(UnsummonType.UNSPECIFIED);
        }
        PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, lastAttacker == null ? 0 : lastAttacker.getObjectId()), true);
        PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
        return true;
    }
}