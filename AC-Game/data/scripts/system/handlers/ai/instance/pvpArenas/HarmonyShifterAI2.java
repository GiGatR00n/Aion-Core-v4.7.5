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
package ai.instance.pvpArenas;

import ai.ShifterAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.instance.instancereward.HarmonyArenaReward;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author xTz
 */
@AIName("harmony_shifter")
public class HarmonyShifterAI2 extends ShifterAI2 {

    private AtomicBoolean isRewarded = new AtomicBoolean(false);

    @Override
    protected void handleUseItemFinish(Player player) {
        super.handleUseItemFinish(player);
        if (isRewarded.compareAndSet(false, true)) {
            AI2Actions.handleUseItemFinish(this, player);
            switch (getNpcId()) {
                case 207116:
                    useSkill(getNpcs(207118));
                    useSkill(getNpcs(207119));
                    break;
                case 207099:
                    useSkill(getNpcs(207100));
                    break;
            }
            AI2Actions.scheduleRespawn(this);
            AI2Actions.deleteOwner(this);
        }
    }

    private void useSkill(List<Npc> npcs) {
        HarmonyArenaReward instance = (HarmonyArenaReward) getPosition().getWorldMapInstance().getInstanceHandler().getInstanceReward();
        for (Npc npc : npcs) {
            int skill = instance.getNpcBonusSkill(npc.getNpcId());
            SkillEngine.getInstance().getSkill(npc, skill >> 8, skill & 0xFF, npc).useNoAnimationSkill();
        }
    }

    private List<Npc> getNpcs(int npcId) {
        return getPosition().getWorldMapInstance().getNpcs(npcId);
    }
}
