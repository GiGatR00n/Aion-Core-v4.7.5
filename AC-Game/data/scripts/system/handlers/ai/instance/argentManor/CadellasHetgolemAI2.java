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
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("cadellas_hetgolem")
public class CadellasHetgolemAI2 extends GeneralNpcAI2 {

    private AtomicBoolean isDestroyed = new AtomicBoolean(false);

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        setStateIfNot(AIState.FOLLOWING);
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    SkillEngine.getInstance().getSkill(getOwner(), 19571, 55, getOwner()).useNoAnimationSkill();
                }
            }
        }, 1000);
    }

    @Override
    protected void handleMoveArrived() {
        super.handleMoveArrived();
        getMoveController().abortMove();
        if (isDestroyed.compareAndSet(false, true)) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500462, getObjectId(), 0, 0);
            SkillEngine.getInstance().getSkill(getOwner(), getHealSkill(), 60, getOwner()).useNoAnimationSkill();
            startDespawnTask();
        }
    }

    private void startDespawnTask() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    AI2Actions.deleteOwner(CadellasHetgolemAI2.this);
                }
            }
        }, 4000);
    }

    private int getHealSkill() {
        switch (getNpcId()) {
            case 282345:
                return 19525;
            case 282346:
                return 19526;
            case 282347:
                return 19527;
            case 282348:
                return 19528;
            case 282349:
                return 19529;
            default:
                return 0;
        }
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
