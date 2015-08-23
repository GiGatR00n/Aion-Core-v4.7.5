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
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.concurrent.Future;

/**
 * @author xTz
 */
@AIName("shirik_burrow")
public class ShirikBurrowAI2 extends AggressiveNpcAI2 {

    private Future<?> lifeTask;

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    protected void handleSpawned() {
        startLifeTask();
        super.handleSpawned();
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

    private void startLifeTask() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    rndSpawn(282535);
                    rndSpawn(282535);
                    AI2Actions.deleteOwner(ShirikBurrowAI2.this);
                }
            }
        }, 25000);
    }

    private void rndSpawn(int npcId) {
        float direction = Rnd.get(0, 199) / 100f;
        int distance = Rnd.get(1, 2);
        float x1 = (float) (Math.cos(Math.PI * direction) * distance);
        float y1 = (float) (Math.sin(Math.PI * direction) * distance);
        WorldPosition p = getPosition();
        Npc npc = (Npc) spawn(npcId, p.getX() + x1, p.getY() + y1, p.getZ(), (byte) 0);
        NpcShoutsService.getInstance().sendMsg(npc, 1500307, npc.getObjectId(), 0, 1000);
    }

    private void cancelLifeTask() {
        if (lifeTask != null && !lifeTask.isDone()) {
            lifeTask.cancel(true);
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
