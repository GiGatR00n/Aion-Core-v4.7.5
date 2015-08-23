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
package ai.instance.rentusBase;

import ai.GeneralNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.concurrent.Future;

/**
 * @author xTz
 */
@AIName("dancing_flame")
public class DancingFlameAI2 extends GeneralNpcAI2 {

    private Future<?> task;

    private void startTask() {
        task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelTask();
                } else {
                    if (isPlayerInRange()) {
                        WorldPosition p = getPosition();
                        if (getNpcId() == 282996) {
                            spawn(282998, p.getX(), p.getY(), p.getZ(), p.getHeading());
                        } else {
                            spawn(282999, p.getX(), p.getY(), p.getZ(), p.getHeading());
                        }
                    }
                }
            }
        }, 3000, 3000);
    }

    private boolean isPlayerInRange() {
        for (Player player : getKnownList().getKnownPlayers().values()) {
            if (isInRange(player, 30)) {
                return true;
            }
        }
        return false;
    }

    private void cancelTask() {
        if (task != null && !task.isDone()) {
            task.cancel(true);
        }
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        if (getNpcId() == 282996 || getNpcId() == 282997) {
            startTask();
        } else {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    SkillEngine.getInstance().getSkill(getOwner(), getNpcId() == 282998 ? 20536 : 20535, 60, getOwner()).useNoAnimationSkill();
                }
            }, 500);
            starLifeTask();
        }
    }

    private void starLifeTask() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                despawn();
            }
        }, 4000);
    }

    private void despawn() {
        if (!isAlreadyDead()) {
            AI2Actions.deleteOwner(this);
        }
    }

    @Override
    protected void handleDespawned() {
        super.handleDespawned();
        cancelTask();
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        cancelTask();
    }

    @Override
    public AIAnswer ask(AIQuestion question) {
        switch (question) {
            case CAN_ATTACK_PLAYER:
                return AIAnswers.POSITIVE;
            default:
                return AIAnswers.NEGATIVE;
        }
    }
}
