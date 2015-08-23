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
package ai.instance.elementisForest;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.AbstractAI;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Luzien
 */
@AIName("terma")
public class TrappedTermaAI2 extends NpcAI2 {

    private boolean isMove;

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (!isMove) {
            isMove = true;
            moveToDead();
        }
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        Npc freeTerma = (Npc) spawn(205495, 455.94f, 537.06f, 132.6f, (byte) 0);
        spawn(701009, 451.706f, 534.313f, 131.979f, (byte) 0);
        NpcShoutsService.getInstance().sendMsg(freeTerma, 1500444, freeTerma.getObjectId(), 0, 3000);
    }

    private void moveToDead() {
        ((AbstractAI) getOwner().getAi2()).setStateIfNot(AIState.WALKING);
        WalkManager.startWalking((NpcAI2) getOwner().getAi2());
        getOwner().setState(1);
        getOwner().getMoveController().moveToPoint(455.93f, 537.2f, 132.55f);
        PacketSendUtility.broadcastPacket(getOwner(), new SM_EMOTION(getOwner(), EmotionType.START_EMOTE2, 0, getOwner().getObjectId()));
        dead();
    }

    private void dead() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                getOwner().getController().die();
            }
        }, 16000);
    }

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    public int modifyDamage(int damage) {
        return 1;
    }

    @Override
    protected AIAnswer pollInstance(AIQuestion question) {
        switch (question) {
            case SHOULD_DECAY:
                return AIAnswers.NEGATIVE;
            case SHOULD_RESPAWN:
                return AIAnswers.NEGATIVE;
            case SHOULD_REWARD:
                return AIAnswers.NEGATIVE;
            default:
                return null;
        }
    }
}
