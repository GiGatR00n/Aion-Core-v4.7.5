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
package ai.instance.ophidanBridge;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.poll.AIAnswer;
import com.aionemu.gameserver.ai2.poll.AIAnswers;
import com.aionemu.gameserver.ai2.poll.AIQuestion;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Eloann
 */
@AIName("ophidan_bridge_cannon")
public class OphidanCannonAI2 extends ActionItemNpcAI2 {

    private AtomicBoolean canUse = new AtomicBoolean(true);

    @Override
    protected void handleUseItemFinish(Player player) {

        if (canUse.compareAndSet(true, false)) {
            int morphSkill = getMorphSkill();
            SkillEngine.getInstance().getSkill(getOwner(), morphSkill, 60, player).useNoAnimationSkill();
            AI2Actions.scheduleRespawn(this);
            AI2Actions.deleteOwner(this);
        }
    }

    private int getMorphSkill() {
        switch (getNpcId()) {
            case 701646: //elyos
                return 21434;
            case 701647: //asmodian
                return 21435;
        }
        return 0;
    }

    @Override
    protected AIAnswer pollInstance(AIQuestion question) {
        switch (question) {
            case SHOULD_REWARD:
                return AIAnswers.NEGATIVE;
            default:
                return super.pollInstance(question);
        }
    }
}
