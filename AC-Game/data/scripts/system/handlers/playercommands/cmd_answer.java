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

import com.aionemu.gameserver.model.Wedding;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.WeddingService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * @author synchro2
 */
public class cmd_answer extends PlayerCommand {

    public cmd_answer() {
        super("answer");
    }

    @Override
    public void execute(Player player, String... params) {
        Wedding wedding = WeddingService.getInstance().getWedding(player);

        if (params == null || params.length != 1) {
            PacketSendUtility.sendMessage(player, "syntax .answer yes/no.");
            return;
        }

        if (player.getWorldId() == 510010000 || player.getWorldId() == 520010000) {
            PacketSendUtility.sendMessage(player, "You can't use this command on prison.");
            return;
        }

        if (wedding == null) {
            PacketSendUtility.sendMessage(player, "Wedding not started.");
        }

        if (params[0].toLowerCase().equals("yes")) {
            PacketSendUtility.sendMessage(player, "You accept.");
            WeddingService.getInstance().acceptWedding(player);
        }

        if (params[0].toLowerCase().equals("no")) {
            PacketSendUtility.sendMessage(player, "You decide.");
            WeddingService.getInstance().cancelWedding(player);
        }

    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "syntax .answer yes/no.");
    }
}
