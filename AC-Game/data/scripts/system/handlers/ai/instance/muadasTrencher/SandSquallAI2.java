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
package ai.instance.muadasTrencher;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.concurrent.Future;

/**
 * @author xTz
 */
@AIName("sand_squall")
public class SandSquallAI2 extends AggressiveNpcAI2 {

    private Future<?> lifeTask;

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        startLifeTask();
        castSkillTask(19896, 500);
        castSkillTask(19894, 500);
        castSkillTask(19894, 2500);
        castSkillTask(20444, 4500);
        castSkillTask(19894, 6500);
        castSkillTask(19894, 8500);
        castSkillTask(19894, 10500);
        castSkillTask(20444, 12500);
        castSkillTask(19894, 14500);
        castSkillTask(19894, 16500);
        castSkillTask(19895, 18500);
    }

    @Override
    protected void handleDespawned() {
        cancelLifeTask();
        super.handleDespawned();
    }

    @Override
    protected void handleDied() {
        cancelLifeTask();
        super.handleDied();
        AI2Actions.deleteOwner(this);
    }

    private void castSkillTask(final int skill, int time) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    WorldPosition p = getPosition();
                    if (p != null) {
                        WorldMapInstance instance = p.getWorldMapInstance();
                        if (instance != null) {
                            SkillEngine.getInstance().getSkill(getOwner(), skill, 60, getOwner()).useNoAnimationSkill();
                        }
                    }
                }
            }
        }, time);
    }

    private void startLifeTask() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    AI2Actions.deleteOwner(SandSquallAI2.this);
                }
            }
        }, 20000);
    }

    private void cancelLifeTask() {
        if (lifeTask != null && !lifeTask.isDone()) {
            lifeTask.cancel(true);
        }
    }

    @Override
    public AIAnswer ask(AIQuestion question) {
        switch (question) {
            case CAN_ATTACK_PLAYER:
                return AIAnswers.POSITIVE;
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
