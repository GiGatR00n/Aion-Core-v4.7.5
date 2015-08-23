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
package ai.instance.darkPoeta;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author Ritsu
 */
@AIName("spectral_tree")
public class Noah_sFuriousShadeAI2 extends AggressiveNpcAI2 {

    private Future<?> skillTask;
    private Future<?> skill2Task;
    protected List<Integer> percents = new ArrayList<Integer>();

    @Override
    public void handleSpawned() {
        super.handleSpawned();
        addPercent();
        useSkill();
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        for (Integer percent : percents) {
            if (hpPercentage <= percent) {
                switch (percent) {
                    case 30:
                        AI2Actions.useSkill(this, 18529);
                        useSkillTree();
                        break;
                }
                percents.remove(percent);
                break;
            }
        }
    }

    private void useSkill() {
        if (Rnd.get(2) > 0) {
            SkillEngine.getInstance().getSkill(getOwner(), 16822, 50, getOwner()).useSkill();
        }
    }

    private void useSkillTree() {
        skillTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                SkillEngine.getInstance().getSkill(getOwner(), 17736, 50, getTarget()).useSkill();
                skill2Task = ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        SkillEngine.getInstance().getSkill(getOwner(), 18531, 50, getTarget()).useSkill();
                    }
                }, 8000);
            }
        }, 7000);
    }

    private void addPercent() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{30});
    }

    private void cancelTask() {
        if (skillTask != null && !skillTask.isDone()) {
            skillTask.cancel(true);
        } else if (skill2Task != null && !skill2Task.isDone()) {
            skill2Task.cancel(true);
        }
    }

    @Override
    protected void handleBackHome() {
        addPercent();
        cancelTask();
        super.handleBackHome();
    }

    @Override
    protected void handleDespawned() {
        percents.clear();
        cancelTask();
        super.handleDespawned();
    }

    @Override
    protected void handleDied() {
        percents.clear();
        cancelTask();
        super.handleDied();
    }
}
