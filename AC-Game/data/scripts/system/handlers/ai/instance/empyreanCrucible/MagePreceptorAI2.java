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
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.world.WorldPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Luzien
 */
@AIName("mage_preceptor")
public class MagePreceptorAI2 extends AggressiveNpcAI2 {

    private List<Integer> percents = new ArrayList<Integer>();

    @Override
    public void handleSpawned() {
        super.handleSpawned();
        addPercents();
    }

    @Override
    public void handleDespawned() {
        percents.clear();
        despawnNpcs();
        super.handleDespawned();
    }

    @Override
    public void handleDied() {
        despawnNpcs();
        super.handleDied();
    }

    @Override
    public void handleBackHome() {
        addPercents();
        despawnNpcs();
        super.handleBackHome();
    }

    @Override
    public void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void startEvent(int percent) {
        if (percent == 50 || percent == 25) {
            SkillEngine.getInstance().getSkill(getOwner(), 19606, 10, getTarget()).useNoAnimationSkill();
        }

        switch (percent) {
            case 75:
                SkillEngine.getInstance().getSkill(getOwner(), 19605, 10, getTargetPlayer()).useNoAnimationSkill();
                break;
            case 50:
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        if (!isAlreadyDead()) {
                            SkillEngine.getInstance().getSkill(getOwner(), 19609, 10, getOwner()).useNoAnimationSkill();
                            ThreadPoolManager.getInstance().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    WorldPosition p = getPosition();
                                    spawn(282364, p.getX(), p.getY(), p.getZ(), p.getHeading());
                                    spawn(282363, p.getX(), p.getY(), p.getZ(), p.getHeading());
                                    scheduleSkill(2000);
                                }
                            }, 4500);

                        }
                    }
                }, 3000);
                break;
            case 25:
                scheduleSkill(3000);
                scheduleSkill(9000);
                scheduleSkill(15000);
                break;
        }

    }

    private void scheduleSkill(int delay) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (!isAlreadyDead()) {
                    SkillEngine.getInstance().getSkill(getOwner(), 19605, 10, getTargetPlayer()).useNoAnimationSkill();

                }
            }
        }, delay);
    }

    private Player getTargetPlayer() {
        List<Player> players = new ArrayList<Player>();
        for (Player player : getKnownList().getKnownPlayers().values()) {
            if (!PlayerActions.isAlreadyDead(player) && MathUtil.isIn3dRange(player, getOwner(), 37)) {
                players.add(player);
            }
        }
        return players.get(Rnd.get(players.size()));
    }

    private void checkPercentage(int percentage) {
        for (Integer percent : percents) {
            if (percentage <= percent) {
                percents.remove(percent);
                startEvent(percent);
                break;
            }
        }
    }

    private void addPercents() {
        percents.clear();
        Collections.addAll(percents, new Integer[]{75, 50, 25});
    }

    private void despawnNpcs() {
        despawnNpc(getPosition().getWorldMapInstance().getNpc(282364));
        despawnNpc(getPosition().getWorldMapInstance().getNpc(282363));
    }

    private void despawnNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }
}
