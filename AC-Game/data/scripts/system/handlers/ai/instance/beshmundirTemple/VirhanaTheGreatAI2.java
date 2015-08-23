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
package ai.instance.beshmundirTemple;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.services.NpcShoutsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Antraxx
 */
@AIName("virhana")
public class VirhanaTheGreatAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    private Future<?> taskRage;
    private Future<?> taskRageExec;
    private int count;
    protected List<Integer> percents = new ArrayList<Integer>();

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{50});
    }

    private synchronized void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 50:
                        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500065, getObjectId(), 0, 1000);
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    private void cancelTasks() {
        if ((taskRageExec != null) && !taskRageExec.isDone()) {
            taskRageExec.cancel(true);
        }
        if ((taskRage != null) && !taskRage.isDone()) {
            taskRage.cancel(true);
        }
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500064, getObjectId(), 0, 1000);
            scheduleRage();
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    @Override
    protected void handleDied() {
        cancelTasks();
        super.handleDied();
        percents.clear();
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        addPercent();
    }

    @Override
    protected void handleBackHome() {
        cancelTasks();
        super.handleBackHome();
        isHome.set(true);
        addPercent();
    }

    private void scheduleRage() {
        if (isAlreadyDead() || isHome.equals(true)) {
            return;
        }
        AI2Actions.useSkill(this, 19121);
        taskRage = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                startRage();
            }
        }, 70000);
    }

    private void startRage() {
        if (isAlreadyDead() || isHome.equals(true)) {
            return;
        }
        if (count < 12) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500066, getObjectId(), 0, 1000);
            AI2Actions.useSkill(this, 18897);
            count++;
            taskRageExec = ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    startRage();
                }
            }, 10000);
        } else { // restart after a douzen casts
            count = 0;
            scheduleRage();
        }
    }
}
