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

import com.aionemu.gameserver.ai2.AI2Logger;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.manager.EmoteManager;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.geometry.Point3D;

/**
 * @author ATracer
 */
public class ReturningEventHandler {

    /**
     * @param npcAI
     */
    public static void onNotAtHome(NpcAI2 npcAI) {
        if (npcAI.isLogging()) {
            AI2Logger.info(npcAI, "onNotAtHome");
        }
        if (npcAI.setStateIfNot(AIState.RETURNING)) {
            if (npcAI.isLogging()) {
                AI2Logger.info(npcAI, "returning and restoring");
            }
            EmoteManager.emoteStartReturning(npcAI.getOwner());
        }
        if (npcAI.isInState(AIState.RETURNING)) {
            Npc npc = (Npc) npcAI.getOwner();
            if (npc.hasWalkRoutes()) {
                WalkManager.startWalking(npcAI);
            } else {
                Point3D prevStep = npcAI.getOwner().getMoveController().recallPreviousStep();
                npcAI.getOwner().getMoveController().moveToPoint(prevStep.getX(), prevStep.getY(), prevStep.getZ());
            }
        }
    }

    /**
     * @param npcAI
     */
    public static void onBackHome(NpcAI2 npcAI) {
        if (npcAI.isLogging()) {
            AI2Logger.info(npcAI, "onBackHome");
        }
        npcAI.getOwner().getMoveController().clearBackSteps();
        if (npcAI.setStateIfNot(AIState.IDLE)) {
            EmoteManager.emoteStartIdling(npcAI.getOwner());
            ThinkEventHandler.thinkIdle(npcAI);
        }
        Npc npc = (Npc) npcAI.getOwner();
        npc.getController().onReturnHome();
    }
}
