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
import com.aionemu.gameserver.services.VortexService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import org.apache.commons.lang.math.NumberUtils;

public class Invasion extends AdminCommand {

    private static final String COMMAND_START = "start";
    private static final String COMMAND_STOP = "stop";

    public Invasion() {
        super("invasion");
    }

    @Override
    public void execute(Player player, String... params) {

        if (params.length == 0) {
            showHelp(player);
            return;
        }

        if (COMMAND_STOP.equalsIgnoreCase(params[0]) || COMMAND_START.equalsIgnoreCase(params[0])) {
            handleStartStopInvasion(player, params);
        }
    }

    protected void handleStartStopInvasion(Player player, String... params) {
        if (params.length != 2 || !NumberUtils.isDigits(params[1])) {
            showHelp(player);
            return;
        }

        int vortexId = NumberUtils.toInt(params[1]);
        String locationName = vortexId == 0 ? "Theobomos" : "Brusthonin";
        if (!isValidVortexLocationId(player, vortexId)) {
            showHelp(player);
            return;
        }

        if (COMMAND_START.equalsIgnoreCase(params[0])) {
            if (VortexService.getInstance().isInvasionInProgress(vortexId)) {
                PacketSendUtility.sendMessage(player, locationName + " is already under siege");
            } else {
                PacketSendUtility.sendMessage(player, locationName + " invasion started!");
                VortexService.getInstance().startInvasion(vortexId);
            }
        } else if (COMMAND_STOP.equalsIgnoreCase(params[0])) {
            if (!VortexService.getInstance().isInvasionInProgress(vortexId)) {
                PacketSendUtility.sendMessage(player, locationName + " is not under siege");
            } else {
                PacketSendUtility.sendMessage(player, locationName + " invasion stopped!");
                VortexService.getInstance().stopInvasion(vortexId);
            }
        }
    }

    protected boolean isValidVortexLocationId(Player player, int vortexId) {

        if (!VortexService.getInstance().getVortexLocations().keySet().contains(vortexId)) {
            PacketSendUtility.sendMessage(player, "Id " + vortexId + " is invalid");
            return false;
        }

        return true;
    }

    protected void showHelp(Player player) {
        PacketSendUtility.sendMessage(player, "AdminCommand //invasion start|stop <Id>");
    }
}
