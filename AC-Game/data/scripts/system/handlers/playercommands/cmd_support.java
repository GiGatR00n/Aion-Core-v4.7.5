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
package playercommands;

import com.aionemu.gameserver.model.Support;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.SupportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

import java.util.Arrays;

/**
 * @author paranaix
 * @author GiGatR00n v4.7.5.x
 */
public class cmd_support extends PlayerCommand {

    public cmd_support() {
        super("support");
    }

    @Override
    public void execute(Player player, String... params) 
    {
        if (params.length == 0) {
            PacketSendUtility.sendMessage(player, "Syntax: .ticket <Issue Short Description> -- will notify GM's of your issue");
            return;
        }
        
        if (SupportService.getInstance().hasTicket(player)) {
            PacketSendUtility.sendMessage(player, "You already have an open support!");
            peek(player);
            return;
        }

		String params_string = Arrays.toString(params).replace(",", "");
		params_string = params_string.substring(1, params_string.length() - 1);

        SupportService.getInstance().addTicket(new Support(player, params_string, ""));
        PacketSendUtility.sendMessage(player, "Your support request has been sent successfully. a GM will shortly investigate your problems.");
    }
    
    public void peek(Player player) {
        StringBuilder IssueMsg = new StringBuilder();
        Support support = SupportService.getInstance().peek();

        if (support == null) {
            PacketSendUtility.sendMessage(player, "There are no tickets available at the moment");
            return;
        }

        IssueMsg.append("=============================\n");
        IssueMsg.append("From: " + support.getOwner().getName() + ":" + support.getOwner().getLevel() + ":" + support.getOwner().getRace().toString() + ":" + support.getOwner().getPlayerClass().toString().toLowerCase() + "\n");
        IssueMsg.append(support.getSummary() + "\n");
        IssueMsg.append("=============================");

        PacketSendUtility.sendMessage(player, IssueMsg.toString());
    }
    
    @Override
    public void onFail(Player player, String message) {
    	PacketSendUtility.sendMessage(player, "Syntax: .ticket <Issue Short Description> -- will notify GM's of your issue");
    }    
}
