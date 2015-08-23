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

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.model.templates.spawns.siegespawns.SiegeSpawnTemplate;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

import java.io.IOException;

/**
 * @author Luno
 */
public class Delete extends AdminCommand {

    public Delete() {
        super("delete");
    }

    @Override
    public void execute(Player player, String... params) {

        VisibleObject cre = player.getTarget();
        if (!(cre instanceof Npc)) {
            PacketSendUtility.sendMessage(player, "Wrong target");
            return;
        }
        Npc npc = (Npc) cre;
        SpawnTemplate template = npc.getSpawn();
        if (template.hasPool()) {
            PacketSendUtility.sendMessage(player, "Can't delete pooled spawn template");
            return;
        }
        if (template instanceof SiegeSpawnTemplate) {
            PacketSendUtility.sendMessage(player, "Can't delete siege spawn template");
            return;
        }
        npc.getController().onDelete();
        try {
            DataManager.SPAWNS_DATA2.saveSpawn(player, npc, true);
        } catch (IOException e) {
            e.printStackTrace();
            PacketSendUtility.sendMessage(player, "Could not remove spawn");
            return;
        }
        PacketSendUtility.sendMessage(player, "Spawn removed");
    }

    @Override
    public void onFail(Player player, String message) {
        // TODO Auto-generated method stub
    }
}
