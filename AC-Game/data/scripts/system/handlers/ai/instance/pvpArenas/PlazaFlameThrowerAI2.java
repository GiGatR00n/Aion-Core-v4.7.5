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
import com.aionemu.gameserver.model.instance.instancereward.InstanceReward;
import com.aionemu.gameserver.model.instance.instancereward.PvPArenaReward;
import com.aionemu.gameserver.skillengine.SkillEngine;

import java.util.List;

/**
 * @author xTz
 */
@AIName("plaza_flame_thrower")
public class PlazaFlameThrowerAI2 extends ShifterAI2 {

    private boolean isRewarded;

    @Override
    protected void handleDialogStart(Player player) {
        InstanceReward<?> instance = getPosition().getWorldMapInstance().getInstanceHandler().getInstanceReward();
        if (instance != null && !instance.isStartProgress()) {
            return;
        }
        super.handleDialogStart(player);
    }

    @Override
    protected void handleUseItemFinish(Player player) {
        super.handleUseItemFinish(player);
        if (!isRewarded) {
            isRewarded = true;
            AI2Actions.handleUseItemFinish(this, player);
            switch (getNpcId()) {
                case 701169:
                    useSkill(getNpcs(701178));
                    useSkill(getNpcs(701192));
                    break;
                case 701170:
                    useSkill(getNpcs(701177));
                    useSkill(getNpcs(701191));
                    break;
                case 701171:
                    useSkill(getNpcs(701176));
                    useSkill(getNpcs(701190));
                    break;
                case 701172:
                    useSkill(getNpcs(701175));
                    useSkill(getNpcs(701189));
                    break;
            }
            AI2Actions.scheduleRespawn(this);
            AI2Actions.deleteOwner(this);
        }
    }

    private void useSkill(List<Npc> npcs) {
        PvPArenaReward instance = (PvPArenaReward) getPosition().getWorldMapInstance().getInstanceHandler().getInstanceReward();
        for (Npc npc : npcs) {
            int skill = instance.getNpcBonusSkill(npc.getNpcId());
            SkillEngine.getInstance().getSkill(npc, skill >> 8, skill & 0xFF, npc).useNoAnimationSkill();
        }
    }

    private List<Npc> getNpcs(int npcId) {
        return getPosition().getWorldMapInstance().getNpcs(npcId);
    }
}
