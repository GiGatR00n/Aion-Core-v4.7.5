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
package ai.instance.lowerUdasTemple;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AttackIntention;
import com.aionemu.gameserver.ai2.event.AIEventType;
import com.aionemu.gameserver.ai2.manager.SkillAttackManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.skill.NpcSkillEntry;

@AIName("churatwinblade")
public class ChuraTwinbladeAI2 extends AggressiveNpcAI2 {

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if (hpPercentage <= 33) {
            AI2Actions.useSkill(this, 18624); //Stigma Burst.
        }
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
    }

    @Override
    public AttackIntention chooseAttackIntention() {
        VisibleObject currentTarget = getTarget();
        Creature mostHated = getAggroList().getMostHated();
        if (mostHated == null || mostHated.getLifeStats().isAlreadyDead()) {
            return AttackIntention.FINISH_ATTACK;
        }
        if (currentTarget == null || !currentTarget.getObjectId().equals(mostHated.getObjectId())) {
            onCreatureEvent(AIEventType.TARGET_CHANGED, mostHated);
            return AttackIntention.SWITCH_TARGET;
        }
        NpcSkillEntry skill = SkillAttackManager.chooseNextSkill(this);
        if (skill != null) {
            skillId = skill.getSkillId();
            skillLevel = skill.getSkillLevel();
            return AttackIntention.SKILL_ATTACK;
        }
        return AttackIntention.SIMPLE_ATTACK;
    }
}
