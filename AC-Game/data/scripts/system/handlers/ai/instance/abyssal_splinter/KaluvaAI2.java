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
package ai.instance.abyssal_splinter;

import ai.SummonerAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.manager.EmoteManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.ai.Percentage;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Luzien
 */
@AIName("kaluva")
public class KaluvaAI2 extends SummonerAI2 {

    private boolean canThink = true;

    @Override
    protected void handleIndividualSpawnedSummons(Percentage percent) {
        spawn();
        canThink = false;
        EmoteManager.emoteStopAttacking(getOwner());
        setStateIfNot(AIState.FOLLOWING);
        getOwner().setState(1);
        PacketSendUtility.broadcastPacket(getOwner(), new SM_EMOTION(getOwner(), EmotionType.START_EMOTE2, 0, getObjectId()));
        AI2Actions.targetCreature(this, getPosition().getWorldMapInstance().getNpc(281902));
        getMoveController().moveToTargetObject();
    }

    @Override
    protected void handleMoveArrived() {
        if (canThink == false) {
            Npc egg = getPosition().getWorldMapInstance().getNpc(281902);
            if (egg != null) {
                SkillEngine.getInstance().getSkill(getOwner(), 19223, 55, egg).useNoAnimationSkill();
            }

            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    canThink = true;
                    Creature creature = getAggroList().getMostHated();
                    if (creature == null || !getOwner().canSee(creature) || NpcActions.isAlreadyDead(creature)) {
                        setStateIfNot(AIState.FIGHT);
                        think();
                    } else {
                        getOwner().setTarget(creature);
                        getOwner().getGameStats().renewLastAttackTime();
                        getOwner().getGameStats().renewLastAttackedTime();
                        getOwner().getGameStats().renewLastChangeTargetTime();
                        getOwner().getGameStats().renewLastSkillTime();
                        setStateIfNot(AIState.FIGHT);
                        think();
                    }
                }
            }, 2000);
        }
        super.handleMoveArrived();
    }

    private void spawn() {
        switch (Rnd.get(1, 4)) {
            case 1:
                spawn(281902, 663.322021f, 556.731995f, 424.295013f, (byte) 64);
                break;
            case 2:
                spawn(281902, 644.0224f, 523.9641f, 423.09103f, (byte) 32);
                break;
            case 3:
                spawn(281902, 611.008f, 539.73395f, 423.25034f, (byte) 119);
                break;
            case 4:
                spawn(281902, 628.4426f, 585.4443f, 424.31854f, (byte) 93);
                break;
        }
    }

    @Override
    public boolean canThink() {
        return canThink;
    }
}
