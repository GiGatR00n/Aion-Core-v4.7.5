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
package ai.worlds;

import ai.NoActionAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.MathUtil;

/**
 * @author Steve
 */
@AIName("sacred_image")
public class SacredImageAI2 extends NoActionAI2 {

    @Override
    protected void handleCreatureSee(Creature creature) {
        checkDistance(creature);
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        checkDistance(creature);
    }

    private void checkDistance(Creature creature) {
        int spellid = getOwner().getNpcId() == 258281 ? 20373 : 20374;
        if (creature instanceof Player) {
            Player player = (Player) creature;
            if ((player.getRace().equals(Race.ASMODIANS) && getOwner().getNpcId() == 258281) || (player.getRace().equals(Race.ELYOS) && getOwner().getNpcId() == 258280)) {
                return;
            }
            if (MathUtil.isIn3dRangeLimited(getOwner(), creature, 0, 25)) {
                SkillEngine.getInstance().getSkill(getOwner(), spellid, 65, player).useNoAnimationSkill();
            }
        }
    }

    @Override
    public int modifyDamage(int damage) {
        return 1;
    }
}
