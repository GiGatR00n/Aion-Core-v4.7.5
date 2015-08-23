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
package ai.instance.voidCube;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.world.geo.GeoService;


@AIName("rootmine")
public class cubeMineAI2 extends AggressiveNpcAI2 {

    @Override
    protected void handleCreatureSee(Creature creature) {
        checkDistance(this, creature);
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        checkDistance(this, creature);
    }

    protected void checkDistance(NpcAI2 ai, Creature creature) {
        Npc owner = ai.getOwner();
        if (creature == null || creature.getLifeStats() == null) {
            return;
        }
        if (creature.getLifeStats().isAlreadyDead()) {
            return;
        }
        if (!owner.canSee(creature)) {
            return;
        }
        if (!owner.getActiveRegion().isMapRegionActive()) {
            return;
        }
        if (!(creature instanceof Player)) {
            return;
        }
        if (MathUtil.isIn3dRange(owner, creature, owner.getAggroRange())) {
            if (GeoService.getInstance().canSee(owner, creature)) {
                    AI2Actions.targetCreature(this, creature);
                    if (getNpcId() == 230410){ //stasis mine
                        SkillEngine.getInstance().applyEffectDirectly(20052, getOwner(), creature, 0); //TODO right skill
                        getOwner().getController().die();
                    }
                    if (getNpcId() == 230409){ //destructive mine
                        AI2Actions.useSkill(this, 19659); //destructive mine TODO right skill
                    }
                    AI2Actions.targetCreature(this, null);
            }
        }
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
    @Override
    protected void handleDied() {

        super.handleDied();
    }
}
