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
package ai.instance.tiamatStrongHold;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Cheatkiller
 */
@AIName("invincibleshabokan")
public class InvincibleShabokanAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    private Future<?> skillTask;
    private boolean isFinalBuff;

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            startSkillTask();
        }
        if (!isFinalBuff && getOwner().getLifeStats().getHpPercentage() <= 25) {
            isFinalBuff = true;
            AI2Actions.useSkill(this, 20941);
        }
    }

    private void startSkillTask() {
        skillTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelTask();
                } else {
                    chooseRandomEvent();
                }
            }
        }, 5000, 30000);
    }

    private void cancelTask() {
        if (skillTask != null && !skillTask.isCancelled()) {
            skillTask.cancel(true);
        }
    }

    private void earthQuakeEvent() {
        Npc invisible = getPosition().getWorldMapInstance().getNpc(283082);
        SkillEngine.getInstance().getSkill(getOwner(), 20717, 55, getOwner()).useNoAnimationSkill();
        if (invisible == null) {
            spawn(283082, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
        }
    }

    private void sinkEvent() {
        SkillEngine.getInstance().getSkill(getOwner(), 20720, 55, getOwner()).useNoAnimationSkill();
        for (Player player : getKnownList().getKnownPlayers().values()) {
            if (isInRange(player, 30)) {
                spawn(283083, player.getX(), player.getY(), player.getZ(), (byte) 0);
                spawn(283084, player.getX(), player.getY(), player.getZ(), (byte) 0);
            }
        }
    }

    private void chooseRandomEvent() {
        int rand = Rnd.get(0, 1);
        if (rand == 0) {
            earthQuakeEvent();
        } else {
            sinkEvent();
        }
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        cancelTask();
    }

    @Override
    protected void handleDespawned() {
        super.handleDespawned();
        cancelTask();
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        cancelTask();
        getOwner().getEffectController().removeEffect(20941);
        isHome.set(true);
    }
}
