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
package ai.worlds.danaria;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.handler.AggroEventHandler;
import com.aionemu.gameserver.ai2.handler.CreatureEventHandler;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author DeathMagnestic
 */
@AIName("bodyguard")
public class BodyGuardAI2 extends AggressiveNpcAI2 {

    private boolean canThink = false;

    @Override
    public boolean canThink() {
        return canThink;
    }

    @Override
    protected void handleCreatureSee(Creature creature) {
        CreatureEventHandler.onCreatureSee(this, creature);
        AggroEventHandler.onAggro(this, creature);
        VisibleObject target = creature.getTarget();
        if (creature.getSkillNumber() >= 1 || creature.getCastingSkillId() >= 1 || creature.isCasting()) {
            if (target instanceof Player && !creature.getRace().equals(((Player) target).getRace())) {
                canThink = true;
                AggroEventHandler.onAggro(this, creature);
                StartChain(creature);
            }
            if (target instanceof Creature) {
                canThink = false;
                AggroEventHandler.onAggro(this, creature);
            }
        }
    }

    public void StartChain(final Creature player) {
        SkillEngine.getInstance().getSkill(player, 20672, 65, player).useNoAnimationSkill();
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                SkillEngine.getInstance().getSkill(player, 20542, 65, player).useNoAnimationSkill();
                ThreadPoolManager.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        SkillEngine.getInstance().getSkill(getOwner(), 20548, 65, player).useNoAnimationSkill();
                        ThreadPoolManager.getInstance().schedule(new Runnable() {
                            @Override
                            public void run() {
                                SkillEngine.getInstance().getSkill(getOwner(), 21263, 65, player).useNoAnimationSkill();
                            }
                        }, 2000);
                    }
                }, 500);
            }
        }, 500);
        canThink = false;
    }
}