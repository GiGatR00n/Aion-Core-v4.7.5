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
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;

import java.util.concurrent.Future;

/**
 * @author xTz
 */
@AIName("tualis_drained_minion")
public class TualisDrainedMinionAI2 extends AggressiveNpcAI2 {

    private Future<?> lifeTask;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        startLifeTask();
    }

    private void startLifeTask() {
        lifeTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    AI2Actions.deleteOwner(TualisDrainedMinionAI2.this);
                }
            }
        }, 30000);
    }

    private void cancelTask() {
        if (lifeTask != null && !lifeTask.isDone()) {
            lifeTask.cancel(true);
        }
    }

    @Override
    protected void handleDied() {
        cancelTask();
        super.handleDied();
    }

    @Override
    protected void handleDespawned() {
        cancelTask();
        super.handleDespawned();
    }
}
