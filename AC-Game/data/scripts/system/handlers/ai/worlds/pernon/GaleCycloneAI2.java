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
package ai.worlds.pernon;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.controllers.observer.GaleCycloneObserver;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import javolution.util.FastMap;

/**
 * @author xTz
 */
@AIName("gale_cyclone")
public class GaleCycloneAI2 extends NpcAI2 {

    private FastMap<Integer, GaleCycloneObserver> observed = new FastMap<Integer, GaleCycloneObserver>().shared();
    private boolean blocked;

    @Override
    protected void handleCreatureSee(Creature creature) {
        if (blocked) {
            return;
        }
        if (creature instanceof Player) {
            final Player player = (Player) creature;
            final GaleCycloneObserver observer = new GaleCycloneObserver(player, getOwner()) {
                @Override
                public void onMove() {
                    if (!blocked) {
                        SkillEngine.getInstance().getSkill(getOwner(), 20528, 50, player).useNoAnimationSkill();
                    }
                }
            };
            player.getObserveController().addObserver(observer);
            observed.put(player.getObjectId(), observer);
        }
    }

    @Override
    protected void handleCreatureNotSee(Creature creature) {
        if (blocked) {
            return;
        }
        if (creature instanceof Player) {
            Player player = (Player) creature;
            Integer obj = player.getObjectId();
            GaleCycloneObserver observer = observed.remove(obj);
            if (observer != null) {
                player.getObserveController().removeObserver(observer);
            }
        }
    }

    @Override
    protected void handleDied() {
        clear();
        super.handleDied();
    }

    @Override
    protected void handleDespawned() {
        clear();
        super.handleDespawned();
    }

    private void clear() {
        blocked = true;
        for (Integer obj : observed.keySet()) {
            Player player = getKnownList().getKnownPlayers().get(obj);
            GaleCycloneObserver observer = observed.remove(obj);
            if (player != null) {
                player.getObserveController().removeObserver(observer);
            }
        }
    }
}
