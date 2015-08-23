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
import com.aionemu.gameserver.services.StaticDoorService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

/**
 * @author Rolandas
 */
public class DoorState extends AdminCommand {

    public DoorState() {
        super("doorstate");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length != 3) {
            onFail(admin, null);
            return;
        }

        int doorId = 0;
        try {
            doorId = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(admin, "<id> must be a number!");
            return;
        }

        Boolean open = null;
        if (params[1].equalsIgnoreCase("open")) {
            open = true;
        } else if (params[1].equalsIgnoreCase("close")) {
            open = false;
        }
        if (open == null) {
            onFail(admin, null);
            return;
        }

        int state = 0;
        try {
            state = Integer.parseInt(params[2]);
        } catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(admin, "<state> must be a number!");
            return;
        }

        StaticDoorService.getInstance().changeStaticDoorState(admin, doorId, open, state);
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "<usage //doorstate <id> <open|close> <state>");
    }
}
