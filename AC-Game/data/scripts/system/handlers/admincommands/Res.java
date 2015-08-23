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

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RESURRECT;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Sarynth
 */
public class Res extends AdminCommand {

    public Res() {
        super("res");
    }

    @Override
    public void execute(Player admin, String... params) {
        final VisibleObject target = admin.getTarget();
        if (target == null) {
            PacketSendUtility.sendMessage(admin, "No target selected.");
            return;
        }

        if (!(target instanceof Player)) {
            PacketSendUtility.sendMessage(admin, "You can only resurrect other players.");
            return;
        }

        final Player player = (Player) target;
        if (!player.getLifeStats().isAlreadyDead()) {
            PacketSendUtility.sendMessage(admin, "That player is already alive.");
            return;
        }

        // Default action is to prompt for resurrect.
        if (params == null || params.length == 0 || ("prompt").startsWith(params[0])) {
            player.setPlayerResActivate(true);
            PacketSendUtility.sendPacket(player, new SM_RESURRECT(admin));
            return;
        }

        if (("instant").startsWith(params[0])) {
            PlayerReviveService.skillRevive(player);
            return;
        }

        PacketSendUtility.sendMessage(admin, "[Resurrect] Usage: target player and use //res <instant|prompt>");
    }

    @Override
    public void onFail(Player player, String message) {
        // TODO Auto-generated method stub
    }
}
