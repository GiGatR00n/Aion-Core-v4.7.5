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
package ai.instance.eternalBastion;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.concurrent.Future;


@AIName("pashid_assault_pod")
public class PashidAssaultPodAI2 extends NpcAI2 {
    private Future<?> spawntask;

    @Override
    protected void handleSpawned() {
        StartTimerPodSpawn();
        super.handleSpawned();
    }

    private void cancelSpawnTask() {
        if (spawntask != null && !spawntask.isDone()) {
            spawntask.cancel(true);
        }
    }

    private void StartTimerPodSpawn() {
        spawntask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelSpawnTask();
                } else {
                    spawn_assault();
                }
            }
        }, 10000, 120000);
    }

    private void spawn_assault() {
        float direction = Rnd.get(0, 199) / 100f;
        int distance = Rnd.get(0, 10);
        float x1 = (float) (Math.cos(Math.PI * direction) * distance);
        float y1 = (float) (Math.sin(Math.PI * direction) * distance);
        WorldPosition p = getPosition();
        int rnd = Rnd.get(1, 2);
        switch (rnd) {
            case 1:
                spawn(231105, p.getX() + x1, p.getY() + y1, p.getZ(), p.getHeading());
                spawn(231108, p.getX() + x1, p.getY() + y1, p.getZ(), p.getHeading());
                spawn(231106, p.getX() + x1, p.getY() + y1, p.getZ(), p.getHeading());
                break;
            case 2:
                spawn(231105, p.getX() + x1, p.getY() + y1, p.getZ(), p.getHeading());
                spawn(231108, p.getX() + x1, p.getY() + y1, p.getZ(), p.getHeading());
                spawn(231107, p.getX() + x1, p.getY() + y1, p.getZ(), p.getHeading());
                break;
        }
    }

    @Override
    protected void handleDespawned() {
        cancelSpawnTask();
        super.handleDespawned();
    }

    @Override
    protected void handleDied() {
        cancelSpawnTask();
        super.handleDied();
    }

}