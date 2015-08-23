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
package admincommands;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.PunishmentService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

import java.util.NoSuchElementException;

/**
 * @author lord_rex Command: //rprison <player> This command is removing player
 *         from prison.
 */
public class RPrison extends AdminCommand {

    public RPrison() {
        super("rprison");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length == 0 || params.length > 2) {
            PacketSendUtility.sendMessage(admin, "syntax //rprison <player>");
            return;
        }

        try {
            Player playerFromPrison = World.getInstance().findPlayer(Util.convertName(params[0]));

            if (playerFromPrison != null) {
                PunishmentService.setIsInPrison(playerFromPrison, false, 0, "");
                PacketSendUtility.sendMessage(admin, "Player " + playerFromPrison.getName() + " removed from prison.");
            }
        } catch (NoSuchElementException nsee) {
            PacketSendUtility.sendMessage(admin, "Usage: //rprison <player>");
        } catch (Exception e) {
            PacketSendUtility.sendMessage(admin, "Usage: //rprison <player>");
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //rprison <player>");
    }
}
