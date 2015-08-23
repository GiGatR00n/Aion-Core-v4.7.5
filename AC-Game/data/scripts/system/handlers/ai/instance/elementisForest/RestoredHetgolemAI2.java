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

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AIState;
import com.aionemu.gameserver.ai2.AISubState;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.actions.NpcActions;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("restored_hetgolem")
public class RestoredHetgolemAI2 extends AggressiveNpcAI2 {

    private Future<?> lifeTask;
    private AtomicBoolean isStartEvent = new AtomicBoolean(false);

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    getMoveController().abortMove();
                    setSubStateIfNot(AISubState.WALK_RANDOM);
                    setStateIfNot(AIState.WALKING);
                    float direction = Rnd.get(0, 199) / 100f;
                    float x1 = (float) (Math.cos(Math.PI * direction) * 8);
                    float y1 = (float) (Math.sin(Math.PI * direction) * 8);
                    WorldPosition p = getPosition();
                    if (p != null && p.getWorldMapInstance() != null) {
                        getMoveController().moveToPoint(p.getX() + x1, p.getY() + y1, p.getZ());
                        getOwner().setState(1);
                        PacketSendUtility.broadcastPacket(getOwner(), new SM_EMOTION(getOwner(), EmotionType.START_EMOTE2, 0, getObjectId()));
                    }
                }
            }
        }, 3000);
        startLifeTask();
    }

    private void startLifeTask() {
        lifeTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    spawnEvent();
                }
            }
        }, 5000);
    }

    private void cancelTask() {
        if (lifeTask != null && !lifeTask.isDone()) {
            lifeTask.cancel(true);
        }
    }

    private void spawnEvent() {
        if (isStartEvent.compareAndSet(false, true)) {
            WorldPosition p = getPosition();
            if (p != null && p.getWorldMapInstance() != null) {
                spawn(282308, p.getX(), p.getY(), p.getZ(), p.getHeading());
                Npc npc = (Npc) spawn(282465, p.getX(), p.getY(), p.getZ(), p.getHeading());
                NpcActions.delete(npc);
            }
            AI2Actions.deleteOwner(this);
        }
    }

    @Override
    protected void handleDespawned() {
        cancelTask();
        super.handleDespawned();
    }

    @Override
    public void handleDied() {
        cancelTask();
        spawnEvent();
        super.handleDied();
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
