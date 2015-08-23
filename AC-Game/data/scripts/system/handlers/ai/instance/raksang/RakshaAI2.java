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
package ai.instance.raksang;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.actions.PlayerActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("raksha")
public class RakshaAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isAggred = new AtomicBoolean(false);
    private AtomicBoolean isStartedEvent = new AtomicBoolean(false);
    private Future<?> phaseTask;

    @Override
    protected void handleDespawned() {
        cancelPhaseTask();
        super.handleDespawned();
    }

    @Override
    protected void handleDied() {
        cancelPhaseTask();
        spawn(730445, 1062.281f, 889.900f, 138.744f, (byte) 29);
        super.handleDied();
    }

    private void sendMsg(int msg) {
        NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), false, 0, 0);
    }

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isAggred.compareAndSet(false, true)) {
            sendMsg(1401152);
        }
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage <= 75) {
            if (isStartedEvent.compareAndSet(false, true)) {
                sendMsg(1401154);
                startPhaseTask();
            }
        }
    }

    private void startPhaseTask() {
        phaseTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (isAlreadyDead()) {
                    cancelPhaseTask();
                } else {
                    SkillEngine.getInstance().getSkill(getOwner(), 19938, 46, getOwner()).useNoAnimationSkill();
                    List<Player> players = getLifedPlayers();
                    if (!players.isEmpty()) {
                        int size = players.size();
                        if (players.size() < 4) {
                            for (Player p : players) {
                                spawnRubble(p);
                            }
                        } else {
                            int count = Rnd.get(3, size);
                            for (int i = 0; i < count; i++) {
                                if (players.isEmpty()) {
                                    break;
                                }
                                spawnRubble(players.get(Rnd.get(players.size())));
                            }
                        }
                    }
                }
            }
        }, 3000, 15000);
    }

    private void spawnRubble(Player player) {
        final float x = player.getX();
        final float y = player.getY();
        final float z = player.getZ();
        if (x > 0 && y > 0 && z > 0) {
            ThreadPoolManager.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    if (!isAlreadyDead()) {
                        spawn(282325, x, y, z, (byte) 0);
                    }
                }
            }, 3000);

        }
    }

    private List<Player> getLifedPlayers() {
        List<Player> players = new ArrayList<Player>();
        for (Player player : getKnownList().getKnownPlayers().values()) {
            if (!PlayerActions.isAlreadyDead(player)) {
                players.add(player);
            }
        }
        return players;
    }

    private void cancelPhaseTask() {
        if (phaseTask != null && !phaseTask.isDone()) {
            phaseTask.cancel(true);
        }
    }

    @Override
    protected void handleBackHome() {
        cancelPhaseTask();
        isStartedEvent.set(false);
        isAggred.set(false);
        super.handleBackHome();
    }
}
