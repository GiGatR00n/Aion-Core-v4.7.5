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
package ai.instance.shugoImperialTomb;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import java.util.concurrent.Future;

/**
 * @author Swig
 */
@AIName("defensetower") //831130, 831250, 831251
public class DefenseTowerAI2 extends AggressiveNpcAI2 {

    private Future<?> task;

    @Override
    public boolean canThink() {
        return false;
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage > 50 && hpPercentage <= 100) {
            SkillEngine.getInstance().applyEffectDirectly(21097, getOwner(), getOwner(), 0);
        }
        if (hpPercentage > 25 && hpPercentage <= 50) {
            SkillEngine.getInstance().applyEffectDirectly(21098, getOwner(), getOwner(), 0);
        }
        if (hpPercentage >= 0 && hpPercentage <= 25) {
            SkillEngine.getInstance().applyEffectDirectly(21099, getOwner(), getOwner(), 0);
        }
    }

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        SkillEngine.getInstance().applyEffectDirectly(21097, getOwner(), getOwner(), 0);
        task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                AI2Actions.useSkill(DefenseTowerAI2.this, 20954);
            }
        }, 2000, 2000);
    }

    @Override
    public void handleDespawned() {
        task.cancel(true);
        super.handleDespawned();
    }

    @Override
    public void handleBackHome() {
    }

    @Override
    public int modifyDamage(int damage) {
        return 1;
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
