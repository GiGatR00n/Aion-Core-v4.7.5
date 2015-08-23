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
package ai.worlds.tiamaranta;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author xTz
 */
@AIName("protectorate_cavalry_scout")
public class ProtectorateCavalryScoutAI2 extends NpcAI2 {

    private int size;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        spawnEventNpc();
    }

    private void spawnEventNpc() {
        size++;
        int npcId = 0;
        switch (Rnd.get(1, 3)) {
            case 1:
                npcId = 799991;
                break;
            case 2:
                npcId = 799992;
                break;
            case 3:
                npcId = 799993;
                break;
        }
        int msg = 0;
        switch (Rnd.get(1, 3)) {
            case 1:
                msg = 340937;
                break;
            case 2:
                msg = 340955;
                break;
            case 3:
                msg = 0;
                break;
        }
        Npc npc = (Npc) spawn(npcId, 131.34761f, 2770.1194f, 293.92636f, (byte) 100);
        npc.getSpawn().setWalkerId("6000300001");
        WalkManager.startWalking((NpcAI2) npc.getAi2());
        npc.setState(1);
        PacketSendUtility.broadcastPacket(npc, new SM_EMOTION(npc, EmotionType.START_EMOTE2, 0, npc.getObjectId()));
        if (msg != 0) {
            NpcShoutsService.getInstance().sendMsg(npc, msg, npc.getObjectId(), 0, 2000);
        }

    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        if (creature instanceof Npc) {
            Npc npc = (Npc) creature;
            int npcId = npc.getNpcId();
            if (npcId == 799991 || npcId == 799992 || npcId == 799993) {
                int point = npc.getMoveController().getCurrentPoint();
                if (point == 4 && size < 2) {
                    spawnEventNpc();
                } else if (point == 0) {
                    npc.getMoveController().abortMove();
                    npc.getSpawn().setWalkerId(null);
                    WalkManager.stopWalking((NpcAI2) npc.getAi2());
                    NpcActions.delete(npc);
                    size--;
                }
            }
        }
        super.handleCreatureMoved(creature);
    }
}
