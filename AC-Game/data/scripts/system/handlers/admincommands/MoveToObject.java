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
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

/**
 * @author Rolandas
 */
public class MoveToObject extends AdminCommand {

    public MoveToObject() {
        super("movetoobj");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params == null || params.length != 1) {
            PacketSendUtility.sendMessage(admin, "Syntax : //movetoobj <object id>");
            return;
        }

        int objectId = 0;

        try {
            objectId = Integer.valueOf(params[0]);
        } catch (NumberFormatException e) {
            PacketSendUtility.sendMessage(admin, "Only numbers please!!!");
        }

        VisibleObject object = World.getInstance().findVisibleObject(objectId);
        if (object == null) {
            PacketSendUtility.sendMessage(admin, "Cannot find object for spawn #" + objectId);
            return;
        }

        VisibleObject spawn = (VisibleObject) object;

        TeleportService2.teleportTo(admin, spawn.getWorldId(), spawn.getSpawn().getX(), spawn.getSpawn().getY(), spawn
                .getSpawn().getZ());
        admin.getController().stopProtectionActiveTask();
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Syntax : //movetoobj <object id>");
    }
}
