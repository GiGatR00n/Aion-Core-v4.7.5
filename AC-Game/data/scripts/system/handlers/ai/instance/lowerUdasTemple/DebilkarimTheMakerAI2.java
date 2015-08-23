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
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.*;
import com.aionemu.gameserver.ai2.event.*;
import com.aionemu.gameserver.ai2.manager.*;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.skill.NpcSkillEntry;

@AIName("debilkarimthemaker")
public class DebilkarimTheMakerAI2 extends AggressiveNpcAI2 {

    private boolean isStart = false;

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        checkPercentage(getLifeStats().getHpPercentage());
    }

    private void checkPercentage(int hpPercentage) {
        if ((hpPercentage >= 50) && (hpPercentage <= 75)) {
            isStart = true;
            AI2Actions.useSkill(this, 18633); //Rockfall.
            AI2Actions.useSkill(this, 18635); //Wave Of Fear.
        }
        if ((hpPercentage >= 25) && (hpPercentage <= 50)) {
            isStart = true;
            AI2Actions.useSkill(this, 18633); //Rockfall.
            AI2Actions.useSkill(this, 18635); //Wave Of Fear.
            AI2Actions.useSkill(this, 18636); //Invoke Energy.
        }
        if ((hpPercentage >= 10) && (hpPercentage <= 25)) {
            isStart = true;
            AI2Actions.useSkill(this, 18633); //Rockfall.
            AI2Actions.useSkill(this, 18635); //Wave Of Fear.
            if (hpPercentage <= 25) {
                isStart = true;
                AI2Actions.useSkill(this, 19000); //Infernal Rift.
            }
        }
        if (hpPercentage <= 10) {
            isStart = true;
            PyreSpirit();
        }
    }

    private void PyreSpirit() {
        if (getPosition().isSpawned() && !isAlreadyDead() && isStart) {
            for (int i = 0; i < 2; i++) {
                int distance = Rnd.get(3, 10);
                int nrNpc = Rnd.get(1, 2);
                switch (nrNpc) {
                    case 1:
                        nrNpc = 217165; //Pyre Spirit.
                        break;
                    case 2:
                        nrNpc = 217165; //Pyre Spirit.
                        break;
                }
                rndSpawnInRange(nrNpc, distance);
            }
        }
    }

    private void rndSpawnInRange(int npcId, float distance) {
        float direction = Rnd.get(0, 199) / 100f;
        float x1 = (float) (Math.cos(Math.PI * direction) * distance);
        float y1 = (float) (Math.sin(Math.PI * direction) * distance);
        spawn(npcId, getPosition().getX() + x1, getPosition().getY() + y1, getPosition().getZ(), (byte) 0);
    }

    @Override
    protected void handleBackHome() {
        isStart = false;
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
