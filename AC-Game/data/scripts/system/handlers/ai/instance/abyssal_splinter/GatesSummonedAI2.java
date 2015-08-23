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

import ai.GeneralNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.manager.EmoteManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.Future;

/**
 * @author Ritsu
 */
@AIName("gatessummoned")
public class GatesSummonedAI2 extends GeneralNpcAI2 {

    private Future<?> eventTask;
    private boolean canThink = true;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        startMove();
    }

    @Override
    public boolean canThink() {
        return canThink;
    }

    @Override
    protected void handleDied() {
        cancelEventTask();
        super.handleDied();
    }

    @Override
    protected void handleDespawned() {
        cancelEventTask();
        super.handleDespawned();
    }

    @Override
    protected void handleMoveArrived() {
        super.handleMoveArrived();
        startEventTask();
    }

    private void startMove() {
        canThink = false;
        EmoteManager.emoteStopAttacking(getOwner());
        setStateIfNot(AIState.FOLLOWING);
        getOwner().setState(1);
        AI2Actions.targetCreature(this, getPosition().getWorldMapInstance().getNpc(216960));
        getMoveController().moveToTargetObject();
    }

    private void cancelEventTask() {
        if (eventTask != null && !eventTask.isDone()) {
            eventTask.cancel(true);
        }
    }

    private void startEventTask() {
        eventTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Npc boss = getPosition().getWorldMapInstance().getNpc(216960);
                if (isAlreadyDead() && getOwner() == null) {
                    cancelEventTask();
                } else {
                    if (Rnd.get(1) == 0) {
                        SkillEngine.getInstance().getSkill(getOwner(), 19257, 55, boss).useNoAnimationSkill();
                    } else {
                        SkillEngine.getInstance().getSkill(getOwner(), 19281, 55, boss).useNoAnimationSkill();
                    }
                }
            }
        }, 5000, 30000);

    }
}
