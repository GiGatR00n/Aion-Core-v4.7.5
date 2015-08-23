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
package ai.walkers;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.handler.MoveEventHandler;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.utils.MathUtil;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author Rolandas
 */
@AIName("polorserin")
public class PolorSerinAI2 extends WalkGeneralRunnerAI2 {

    static final int[] stopAdults = {203129, 203132};

    @Override
    protected void handleMoveArrived() {
        boolean adultsNear = false;
        for (VisibleObject object : getOwner().getKnownList().getKnownObjects().values()) {
            if (object instanceof Npc) {
                Npc npc = (Npc) object;
                if (!ArrayUtils.contains(stopAdults, npc.getNpcId())) {
                    continue;
                }
                if (MathUtil.isIn3dRange(npc, getOwner(), getOwner().getAggroRange())) {
                    adultsNear = true;
                    break;
                }
            }
        }
        if (adultsNear) {
            MoveEventHandler.onMoveArrived(this);
            getOwner().unsetState(CreatureState.WEAPON_EQUIPPED);
        } else {
            super.handleMoveArrived();
            getOwner().setState(CreatureState.WEAPON_EQUIPPED);
        }
    }
}
