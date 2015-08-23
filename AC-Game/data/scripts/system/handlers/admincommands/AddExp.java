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
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Wakizashi
 */
public class AddExp extends AdminCommand {

    public AddExp() {
        super("addexp");
    }

    @Override
    public void execute(Player player, String... params) {
        if (params.length != 1) {
            onFail(player, null);
            return;
        }

        Player target = null;

        if (player.getTarget() == null) {
            onFail(player, null);
        } else if (!(player.getTarget() instanceof Player)) {
            onFail(player, null);
        } else {
            target = (Player) player.getTarget();
        }

        String paramValue = params[0];
        long exp;
        try {
            exp = Long.parseLong(paramValue);
        } catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(player, "<exp> must be an Integer");
            return;
        }

        exp += target.getCommonData().getExp();
        target.getCommonData().setExp(exp);
        PacketSendUtility.sendMessage(player, "You added " + params[0] + " exp points to the target.");
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Select a target and use command this way:");
        PacketSendUtility.sendMessage(player, "syntax //addexp <exp>");
    }
}
