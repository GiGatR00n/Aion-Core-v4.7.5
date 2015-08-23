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
package ai.instance.pvpArenas;

import ai.GeneralNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.Future;

/**
 * @author wasacacax
 */
@AIName("lava_floor")
public class LavaFloorAI2 extends GeneralNpcAI2 {

    private Future<?> eventTask;

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        startEventTask();
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

    private void cancelEventTask() {
        if (eventTask != null && !eventTask.isDone()) {
            eventTask.cancel(true);
        }
    }

    private void startEventTask() {
        eventTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelEventTask();
                } else {
                    SkillEngine.getInstance().getSkill(getOwner(), 20580, 60, getOwner()).useNoAnimationSkill();
                }
            }
        }, 1000, 3000);
    }
}
