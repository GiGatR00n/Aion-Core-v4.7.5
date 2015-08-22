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
package weddingcommands;

import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.chathandlers.WeddingCommand;

/**
 * @author synchro2
 * @rework Eloann
 */
public class missyou extends WeddingCommand {

    public missyou() {
        super("missyou");
    }

    @Override
    public void execute(final Player player, String... params) {

        Player partner = player.findPartner();

        if (partner == null) {
            PacketSendUtility.sendMessage(player, "Not online.");
            return;
        }

        if (player.isInPrison() || player.isInPvPArena() || partner.isInPrison() || partner.isInPvPArena()) {
            PacketSendUtility.sendMessage(player, "You cannot use this command in your location.");
            return;
        }

        if (player.isInInstance() || partner.isInInstance()) {
            PacketSendUtility.sendMessage(player, "You can't teleported to " + partner.getName() + ", your partner is in Instance.");
            return;
        }

        if (player.isAttackMode() || partner.isAttackMode()) {
            PacketSendUtility.sendMessage(player, "You can't use this command in combat mode!");
            return;
        }

        if (!player.isCommandInUse()) {
            TeleportService2.teleportTo(player, partner.getWorldId(), partner.getInstanceId(), partner.getX(), partner.getY(), partner.getZ(), partner.getHeading(), TeleportAnimation.BEAM_ANIMATION);
            PacketSendUtility.sendMessage(player, "Teleported to player " + partner.getName() + ".");
            player.setCommandUsed(true);

			ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					player.setCommandUsed(false);
				}
			}, 60 * 60 * 1000);
		}
		else
			PacketSendUtility.sendMessage(player, "Only 1 TP per hour.");
	}

	@Override
	public void onFail(Player player, String message) {
		PacketSendUtility.sendMessage(player, "Failed");
	}
}
