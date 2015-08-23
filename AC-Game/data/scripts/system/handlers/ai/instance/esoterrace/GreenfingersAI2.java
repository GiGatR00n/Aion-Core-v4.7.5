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
package ai.instance.esoterrace;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("greenfingers")
public class GreenfingersAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isDestroyed = new AtomicBoolean(false);
    private int walkPosition;
    private int helperSkill;

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        switch (getNpcId()) {
            case 282176:
                walkPosition = 24;
                helperSkill = 19271;
                break;
            case 282177:
                walkPosition = 26;
                helperSkill = 18751;
                break;
            case 282178:
                walkPosition = 40;
                helperSkill = 16634;
                break;
        }
    }

    @Override
    protected void handleMoveArrived() {
        super.handleMoveArrived();
        int point = getOwner().getMoveController().getCurrentPoint();
        if (walkPosition == point) {
            if (isDestroyed.compareAndSet(false, true)) {
                getSpawnTemplate().setWalkerId(null);
                WalkManager.stopWalking(this);
                Npc boss = getPosition().getWorldMapInstance().getNpc(217185);
                if (boss != null) {
                    SkillEngine.getInstance().getSkill(getOwner(), helperSkill, 55, boss).useNoAnimationSkill();
                }
                startDespawnTask();
            }
        }
    }

    private void startDespawnTask() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    AI2Actions.deleteOwner(GreenfingersAI2.this);
                }
            }
        }, 3000);
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
