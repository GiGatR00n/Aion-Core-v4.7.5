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

import com.aionemu.gameserver.model.Support;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.SupportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author paranaix
 * @author GiGatR00n v4.7.5.x
 */
public class Ticket extends AdminCommand {

    public Ticket() {
        super("ticket");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length < 1) {
            PacketSendUtility.sendMessage(admin, "Syntax: //ticket <accept | peek>");
            return;
        }
        if (params[0].equals("accept")) {
            accept(admin);
        } else if (params[0].equals("peek")) {
            peek(admin);
        } else {
            PacketSendUtility.sendMessage(admin, "Syntax: //ticket <accept | peek>");
        }
    }

    public void accept(Player admin) {
        StringBuilder builder = new StringBuilder();
        Support support = SupportService.getInstance().getTicket();

        if (support == null) {
            PacketSendUtility.sendMessage(admin, "There are no tickets available at the moment");
            return;
        }

        builder.append("=============================\n");
        builder.append("From: " + support.getOwner().getName() + ":" + support.getOwner().getLevel() + ":" + support.getOwner().getRace().toString() + ":" + support.getOwner().getPlayerClass().toString().toLowerCase() + "\n");
        builder.append(support.getSummary() + "\n");
        builder.append("=============================");

        PacketSendUtility.sendMessage(admin, builder.toString());
    }

    public void peek(Player admin) {
        StringBuilder builder = new StringBuilder();
        Support support = SupportService.getInstance().peek();

        if (support == null) {
            PacketSendUtility.sendMessage(admin, "There are no tickets available at the moment");
            return;
        }

        builder.append("=============================\n");
        builder.append("From: " + support.getOwner().getName() + ":" + support.getOwner().getLevel() + ":" + support.getOwner().getRace().toString() + ":" + support.getOwner().getPlayerClass().toString().toLowerCase() + "\n");
        builder.append(support.getSummary() + "\n");
        builder.append("=============================");

        PacketSendUtility.sendMessage(admin, builder.toString());
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Syntax: //ticket <accept | peek>");
    }
}
