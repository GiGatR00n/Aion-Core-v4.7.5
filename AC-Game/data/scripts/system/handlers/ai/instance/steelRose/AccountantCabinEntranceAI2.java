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
package ai.instance.steelRose;

import ai.ActionItemNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @rework pralinka
 **/
@AIName("accountant_cabin")
public class AccountantCabinEntranceAI2 extends ActionItemNpcAI2
{
	@Override
	protected void handleUseItemFinish(Player player) {
		switch (getNpcId()) {
		    case 730764: //Accountant's Cabin Entrance.
				switch (player.getWorldId()) {
                    case 301020000: //Steel Rose Quarters Solo
					    PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, "You enter <Accountant's Cabin>", ChatType.BRIGHT_YELLOW_CENTER), true);
						TeleportService2.teleportTo(player, 301020000, 702.11993f, 500.80948f, 939.60675f, (byte) 60, TeleportAnimation.BEAM_ANIMATION);
			        break;
				} 
				switch (player.getWorldId()) {
                    case 301040000: //Steel Rose Quarters Group
					    PacketSendUtility.broadcastPacket(player, new SM_MESSAGE(player, "You enter <Accountant's Cabin>", ChatType.BRIGHT_YELLOW_CENTER), true);
						TeleportService2.teleportTo(player, 301040000, 702.11993f, 500.80948f, 939.60675f, (byte) 60, TeleportAnimation.BEAM_ANIMATION);
			        break;
				}
		    break;
		}
	}
}