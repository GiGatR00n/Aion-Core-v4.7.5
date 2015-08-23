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
package ai.instance.argentManor;

import ai.GeneralNpcAI2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldPosition;

/**
 * @author xTz
 */
@AIName("pletus_orb")
public class PletusOrbAI2 extends GeneralNpcAI2 {

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    protected void handleMoveArrived() {
        super.handleMoveArrived();
        getOwner().setState(Rnd.get(1, 2) == 1 ? 1 : 64);
        PacketSendUtility.broadcastPacket(getOwner(), new SM_EMOTION(getOwner(), EmotionType.START_EMOTE2, 0, getObjectId()));
        if (!isInRangeMagicalSap()) {
            if (Rnd.get(1, 100) >= 70) {
                WorldPosition p = getPosition();
                spawn(282148, p.getX(), p.getY(), p.getZ(), (byte) 0);
            }
        }
    }

    private boolean isInRangeMagicalSap() {
        for (VisibleObject obj : getKnownList().getKnownObjects().values()) {
            if (obj instanceof Npc) {
                Npc npc = (Npc) obj;
                if (isInRange(obj, 1) && npc.getNpcId() == 282148) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int modifyDamage(int damage) {
        return 1;
    }

    @Override
    public AIAnswer ask(AIQuestion question) {
        switch (question) {
            case CAN_RESIST_ABNORMAL:
                return AIAnswers.POSITIVE;
            default:
                return AIAnswers.NEGATIVE;
        }
    }
}
