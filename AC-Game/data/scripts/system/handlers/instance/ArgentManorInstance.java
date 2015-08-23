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
package instance;

import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.ai2.manager.WalkManager;
import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author xTz
 */
@InstanceID(300270000)
public class ArgentManorInstance extends GeneralInstanceHandler {

    @Override
    public void onDie(Npc npc) {
        switch (npc.getNpcId()) {
            case 217243:
                Npc prison = instance.getNpc(205498);
                if (prison != null) {
                    NpcShoutsService.getInstance().sendMsg(prison, 1500263, prison.getObjectId(), 0, 0);
                    prison.getSpawn().setWalkerId("69B73541CCBF9F7BAB484BA68FF4BE0D2A9B6AD6");
                    WalkManager.startWalking((NpcAI2) prison.getAi2());
                }
                spawn(701011, 955.91956f, 1240.153f, 54.090305f, (byte) 90);
                break;
        }
    }

    @Override
    public void handleUseItemFinish(Player player, Npc npc) {
        switch (npc.getNpcId()) {
            case 701001:
                SkillEngine.getInstance().getSkill(npc, 19316, 60, player).useNoAnimationSkill();
                break;
            case 701002:
                SkillEngine.getInstance().getSkill(npc, 19317, 60, player).useNoAnimationSkill();
                break;
            case 701003:
                SkillEngine.getInstance().getSkill(npc, 19318, 60, player).useNoAnimationSkill();
                break;
            case 701004:
                SkillEngine.getInstance().getSkill(npc, 19319, 60, player).useNoAnimationSkill();
                break;
        }
    }

    @Override
    public void onPlayerLogOut(Player player) {
        TeleportService2.moveToInstanceExit(player, mapId, player.getRace());
        removeEffects(player);
    }

    @Override
    public void onLeaveInstance(Player player) {
        removeEffects(player);
    }

    private void removeEffects(Player player) {
        player.getEffectController().removeEffect(19316);
        player.getEffectController().removeEffect(19317);
        player.getEffectController().removeEffect(19318);
        player.getEffectController().removeEffect(19319);
    }
}
