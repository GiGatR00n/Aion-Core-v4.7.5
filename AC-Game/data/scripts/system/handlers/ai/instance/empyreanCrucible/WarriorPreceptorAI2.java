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
package ai.instance.empyreanCrucible;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.actions.PlayerActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.MathUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Luzien
 */
@AIName("warrior_preceptor")
public class WarriorPreceptorAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);
    private Future<?> task;

    @Override
    public void handleDespawned() {
        cancelTask();
        super.handleDespawned();
    }

    @Override
    public void handleDied() {
        cancelTask();
        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500208, getObjectId(), 0, 0);
        super.handleDied();
    }

    @Override
    public void handleBackHome() {
        cancelTask();
        isHome.set(true);
        super.handleBackHome();
    }

    @Override
    public void handleAttack(Creature creature) {

        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            startSkillTask();
        }
    }

    private void startSkillTask() {
        task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelTask();
                } else {
                    startSkillEvent();
                }
            }
        }, 30000, 30000);
    }

    private void cancelTask() {
        if (task != null && !task.isCancelled()) {
            task.cancel(true);
        }
    }

    private void startSkillEvent() {
        NpcShoutsService.getInstance().sendMsg(getOwner(), 1500207, getObjectId(), 0, 0);
        SkillEngine.getInstance().getSkill(getOwner(), 19595, 10, getTargetPlayer()).useNoAnimationSkill();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    SkillEngine.getInstance().getSkill(getOwner(), 19596, 15, getOwner()).useNoAnimationSkill();
                }
            }
        }, 6000);
    }

    private Player getTargetPlayer() {
        List<Player> players = new ArrayList<Player>();
        for (Player player : getKnownList().getKnownPlayers().values()) {
            if (!PlayerActions.isAlreadyDead(player) && MathUtil.isIn3dRange(player, getOwner(), 15)) {
                players.add(player);
            }
        }
        return !players.isEmpty() ? players.get(Rnd.get(players.size())) : null;
    }
}
