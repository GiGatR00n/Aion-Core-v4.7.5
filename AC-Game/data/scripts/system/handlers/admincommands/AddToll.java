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
import com.aionemu.gameserver.model.ingameshop.InGameShopEn;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * @author Eloann
 */
public class AddToll extends AdminCommand {

    public AddToll() {
        super("addtoll");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params.length == 0) {
            onFail(admin, null);
            return;
        }
        int toll;
        Player player = null;
        if (params.length == 2) {
            try {
                toll = Integer.parseInt(params[2]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "<toll> value must be an integer.");
                return;
            }

            if (toll < 0) {
                PacketSendUtility.sendMessage(admin, "<toll> must > 0.");
                return;
            }

            String name = Util.convertName(params[1]);

            player = World.getInstance().findPlayer(name);
            if (player == null) {
                PacketSendUtility.sendMessage(admin, "The specified player is not online.");
                return;
            }

            PacketSendUtility.sendMessage(admin, "You added " + toll + " tolls to Player: " + name);
            addtoll(player, toll);
        }
        if (params.length == 1) {
            try {
                toll = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {
                PacketSendUtility.sendMessage(admin, "<toll> value must be an integer.");
                return;
            }

            VisibleObject target = admin.getTarget();
            if (target == null) {
                PacketSendUtility.sendMessage(admin, "You should select a target first!");
                return;
            }

            if (target instanceof Player) {
                player = (Player) target;
            }

            PacketSendUtility.sendMessage(admin, "You added " + toll + " tolls to Player: " + player.getName());
            addtoll(player, toll);
        }
    }

    private void addtoll(Player player, int toll) {
        InGameShopEn.getInstance().addToll(player, toll);
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "If you have target : //addtoll <toll>");
        PacketSendUtility.sendMessage(player, "Without target : //addtoll <playername> <toll>");
    }
}
