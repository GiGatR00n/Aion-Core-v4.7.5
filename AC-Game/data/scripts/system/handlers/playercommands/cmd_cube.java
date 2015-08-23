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

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.CubeExpandService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;

/**
 * @author Kamui, Maestros
 */
public class cmd_cube extends PlayerCommand {

    public cmd_cube() {
        super("cube");
    }

    @Override
    public void execute(Player player, String... params) {
        if (player.getNpcExpands() >= 9) {
            PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.CUBE_ALLREADY_EXPANDED));
            return;
        }
        while (player.getNpcExpands() < 9) {
            CubeExpandService.expand(player, true);
        }
        PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.CUBE_SUCCESS_EXPAND));
    }

    @Override
    public void onFail(Player admin, String message) {
        PacketSendUtility.sendMessage(admin, "Syntax : .cube");
    }
}
