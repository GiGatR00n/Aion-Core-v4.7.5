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
package ai.instance.kromedesTrial;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Tiger0319, Gigi, xTz
 */
@AIName("krbuff")
public class KromedesBuffAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleUseItemFinish(Player player) {
        switch (getNpcId()) {
            case 730336:
                SkillEngine.getInstance().applyEffectDirectly(19216, player, player, 0);
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400655));
                break;
            case 730337:
                SkillEngine.getInstance().applyEffectDirectly(19217, player, player, 0);
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400656));
                AI2Actions.deleteOwner(this);
                break;
            case 730338:
                SkillEngine.getInstance().applyEffectDirectly(19218, player, player, 0);
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400657));
                AI2Actions.deleteOwner(this);
                break;
            case 730339:
                SkillEngine.getInstance().applyEffectDirectly(19219, player, player, 0);
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1400658));
                break;
        }
    }
}
