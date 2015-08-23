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
package ai.instance.dredgion;

import ai.OneDmgPerHitAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.event.AIEventType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;

/**
 * recieve only 1 dmg with each attack(handled by super)
 * <p/>
 * Aggro the whole room on attack
 *
 * @author Luzien
 */
@AIName("surkana")
public class SurkanaAI2 extends OneDmgPerHitAI2 {

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        //roomaggro
        checkForSupport(creature);
    }

    private void checkForSupport(Creature creature) {
        for (VisibleObject object : getKnownList().getKnownObjects().values()) {
            if (object instanceof Npc && isInRange(object, 25) && !((Npc) object).getLifeStats().isAlreadyDead()) {
                ((Npc) object).getAi2().onCreatureEvent(AIEventType.CREATURE_AGGRO, creature);
            }
        }
    }
}
