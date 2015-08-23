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
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * @author Source
 */
public class GroupToMe extends AdminCommand {

    public GroupToMe() {
        super("grouptome");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params == null || params.length < 1) {
            onFail(admin, null);
            return;
        }

        Player groupToMove = World.getInstance().findPlayer(Util.convertName(params[0]));
        if (groupToMove == null) {
            PacketSendUtility.sendMessage(admin, "The player is not online.");
            return;
        }

        if (!groupToMove.isInGroup2()) {
            PacketSendUtility.sendMessage(admin, groupToMove.getName() + " is not in group.");
            return;
        }

        for (Player target : groupToMove.getPlayerGroup2().getMembers()) {
            if (target != admin) {
                TeleportService2.teleportTo(target, admin.getWorldId(), admin.getInstanceId(), admin.getX(), admin.getY(),
                        admin.getZ(), admin.getHeading());
                PacketSendUtility.sendMessage(target, "You have been summoned by " + admin.getName() + ".");
                PacketSendUtility.sendMessage(admin, "You summon " + target.getName() + ".");
            }
        }
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax //grouptome <player>");
    }
}
