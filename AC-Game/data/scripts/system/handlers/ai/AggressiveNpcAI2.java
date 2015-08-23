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
package ai;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.event.AIEventType;
import com.aionemu.gameserver.ai2.handler.AggroEventHandler;
import com.aionemu.gameserver.ai2.handler.CreatureEventHandler;
import com.aionemu.gameserver.model.actions.PlayerActions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ATracer
 * @author Antraxx
 */
@AIName("aggressive")
public class AggressiveNpcAI2 extends GeneralNpcAI2 {

    @Override
    protected void handleCreatureSee(Creature creature) {
        CreatureEventHandler.onCreatureSee(this, creature);
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        CreatureEventHandler.onCreatureMoved(this, creature);
    }

    @Override
    protected void handleCreatureAggro(Creature creature) {
        if (canThink()) {
            AggroEventHandler.onAggro(this, creature);
        }
    }

    @Override
    protected boolean handleGuardAgainstAttacker(Creature attacker) {
        return AggroEventHandler.onGuardAgainstAttacker(this, attacker);
    }

    /**
     * NPC calls for help. NPCs in range of distance are going aggressive to
     * first target of caller
     *
     * @param distance (meters)
     */
    protected void callForHelp(int distance) {
        Creature firstTarget = getAggroList().getMostHated();
        for (VisibleObject object : getKnownList().getKnownObjects().values()) {
            if (object instanceof Npc && isInRange(object, distance)) {
                Npc npc = (Npc) object;
                if ((npc != null) && !npc.getLifeStats().isAlreadyDead()) {
                    npc.getAi2().onCreatureEvent(AIEventType.CREATURE_AGGRO, firstTarget);
                }
            }
        }
    }

    /**
     * returns a random target from npc's known-list
     *
     * @return (Player)
     */
    protected Player getRandomTarget() {
        List<Player> players = new ArrayList<Player>();
        for (Player player : getKnownList().getKnownPlayers().values()) {
            if (!PlayerActions.isAlreadyDead(player) && MathUtil.isIn3dRange(player, getOwner(), 50)) {
                players.add(player);
            }
        }
        if (players.isEmpty()) {
            return null;
        }
        return players.get(Rnd.get(players.size()));
    }
}
