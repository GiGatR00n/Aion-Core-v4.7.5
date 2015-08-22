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
package com.aionemu.gameserver.ai2.handler;

import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.AISubState;
import com.aionemu.gameserver.ai2.AbstractAI;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.gameobjects.Npc;

/**
 * @author Rolandas
 */
public class FreezeEventHandler {

    public static void onUnfreeze(AbstractAI ai) {
        if (ai.isInSubState(AISubState.FREEZE)) {
            ai.setSubStateIfNot(AISubState.NONE);
            if (ai instanceof NpcAI2) {
                Npc npc = ((NpcAI2) ai).getOwner();
                if (npc.getWalkerGroup() != null) {
                    ai.setStateIfNot(AIState.WALKING);
                    ai.setSubStateIfNot(AISubState.WALK_WAIT_GROUP);
                } else if (npc.getSpawn().getRandomWalk() > 0) {
                    ai.setStateIfNot(AIState.WALKING);
                    ai.setSubStateIfNot(AISubState.WALK_RANDOM);
                }
                npc.updateKnownlist();
            }
            ai.think();
        }
    }

    public static void onFreeze(AbstractAI ai) {
        if (ai.isInState(AIState.WALKING)) {
            WalkManager.stopWalking((NpcAI2) ai);
        }
        ai.setStateIfNot(AIState.IDLE);
        ai.setSubStateIfNot(AISubState.FREEZE);
        ai.think();
        if (ai instanceof NpcAI2) {
            Npc npc = ((NpcAI2) ai).getOwner();
            npc.updateKnownlist();
            npc.getAggroList().clear();
            npc.getEffectController().removeAllEffects();
        }
    }
}
